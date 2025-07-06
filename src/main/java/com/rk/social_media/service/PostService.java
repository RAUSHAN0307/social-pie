package com.rk.social_media.service;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    // yaha par public dene ki jarurat nahi hai kyon ki interface me method by default public hi hota hai
    Post createNewPost(String caption , MultipartFile image , MultipartFile video , Integer userId) throws PostException, IOException;

    String deletePost(Integer postId , Integer userId) throws PostException, UserException;
    List<Post> findPostByUserId(Integer userId);
    Post findPostById(Integer postId) throws PostException;
    List<Post> findAllPost();
    Post savedPost(Integer postId , Integer userId) throws PostException, UserException;
    Post likePost(Integer postId , Integer userId) throws PostException, UserException;
    List<Post>findPostByUser(User user);
}
