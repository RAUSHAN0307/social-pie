package com.rk.social_media.service;

import com.rk.social_media.Exception.CommentException;
import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment , Integer postId , Integer userId) throws CommentException, UserException, PostException;

    Comment likeComment(Integer commentId , Integer userId) throws CommentException, UserException;

    Comment findCommentById(Integer commentId) throws CommentException;

    List<Comment> findCommentByPostId(Integer postId) throws CommentException, PostException;

    void deleteComment(Integer commentId, Integer userId) throws CommentException;

}
