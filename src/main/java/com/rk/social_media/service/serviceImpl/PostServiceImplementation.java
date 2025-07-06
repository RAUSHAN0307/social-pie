package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.PostRepo;
import com.rk.social_media.repo.UserRepo;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.PostService;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UploadToCloudService uploadToCloudService;

    @Override
    public Post createNewPost(String caption, MultipartFile image,MultipartFile video, Integer userId) throws PostException, IOException {

        // uploading image
        String imageUrl = null;
        if(image != null && !image.isEmpty()){
            imageUrl = uploadToCloudService.uploadImage(image);
        }

        // uploading video
        String videoUrl = null;
        if(video != null && !video.isEmpty()){
            videoUrl = uploadToCloudService.uploadVideo(video);
        }

        Optional <User> user = userRepo.findById(userId);
        if(!user.isPresent()) throw new PostException("user not exist with this id " + userId);

        Post post = new Post();

        post.setCaption(caption);
        post.setImage(imageUrl);
        post.setVideo(videoUrl);
        post.setUser(user.get());
        post.setCreatedAt(LocalDateTime.now());
        // if there is any content in the post or not
        if(post.getCaption() == null || post.getCaption().isBlank() && (post.getImage() == null && post.getVideo() == null)) throw new PostException("Post must have at least a caption, image, or video");
        return postRepo.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(post.getUser().getId() != user.getId()) throw new PostException("you can't delete this post");
        postRepo.deleteById(postId);
        return "post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {

        return postRepo.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty()) throw new PostException("post not found with id " + postId);
        return post.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepo.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        }
        else{
            user.getSavedPost().add(post);
        }
        userRepo.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        boolean isLiked = false;
        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }
        else {
            post.getLiked().add(user);
            isLiked = true;
        }

        if(isLiked && !post.getUser().getId().equals(userId)){
            notificationService.sendNotification(
                    post.getUser() ,
                    userService.findUserById(userId),
                    post.getUser().getFirstName() + " liked your post",
                    "LIKE_Post",
                    postId
            );
        }
        return postRepo.save(post);
    }

    public List<Post> findPostByUser(User user){
        return findPostByUserId(user.getId());
    }

}
