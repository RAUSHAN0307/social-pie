package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.CommentException;
import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Comment;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.CommentRepo;
import com.rk.social_media.repo.PostRepo;
import com.rk.social_media.service.CommentService;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.PostService;
import com.rk.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private NotificationService notificationService;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws CommentException, UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setLocalDateTime(LocalDateTime.now());
        comment.setPost(post);

        //notification
        if (!post.getUser().getId().equals(user.getId())) {
            notificationService.sendNotification(post.getUser(), user ," commented on your post." , "Comment" , post.getId());
        }


        post.getComments().add(comment);
        Comment savedComment = commentRepo.save(comment);
        postRepo.save(post);
        return savedComment;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);
        if(comment.getLiked().contains(user)){
            comment.getLiked().remove(user);
        }
        else{
            comment.getLiked().add(user);
        }

        return commentRepo.save(comment);
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentException("comment not exist"));
        return comment;
    }

    @Override
    public List<Comment> findCommentByPostId(Integer postId) throws CommentException, PostException {
        Post post = postService.findPostById(postId);
        List<Comment> comments = post.getComments();
        return comments;
    }

    @Override
    public void deleteComment(Integer commentId, Integer userId) throws CommentException {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found with id: " + commentId));

        // Get the post's owner
        Integer postOwnerId = comment.getPost().getUser().getId();
        Integer commentOwnerId = comment.getUser().getId();

        // Allow delete if the current user is either the comment's creator or the post's creator
        if (!userId.equals(commentOwnerId) && !userId.equals(postOwnerId)) {
            throw new CommentException("You are not authorized to delete this comment.");
        }

        commentRepo.delete(comment);
    }

}
