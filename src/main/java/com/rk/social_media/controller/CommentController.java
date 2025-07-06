package com.rk.social_media.controller;

import com.rk.social_media.Exception.CommentException;
import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.config.JwtProvider;
import com.rk.social_media.dto.CommentDto;
import com.rk.social_media.entity.Comment;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.CommentService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.CommentMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cmt")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Operation(summary = "Create a comment on a post")
    @PostMapping("/create/{postId}/comment")
    public CommentDto createComment(@RequestHeader("Authorization") String jwt , @PathVariable Integer postId , @RequestBody Comment comment) throws PostException, UserException, CommentException {
        User user = userService.findUserByJwt(jwt);
        Comment savedComment = commentService.createComment(comment, postId, user.getId());
        return CommentMapper.toDto(savedComment);
    }

    @Operation(summary = "Like a comment")
    @PutMapping("/like/{commentId}")
    public CommentDto likeComment(@RequestHeader ("Authorization") String jwt , @PathVariable Integer commentId) throws UserException, CommentException {
        User user = userService.findUserByJwt(jwt);
        Comment likedComment = commentService.likeComment(commentId , user.getId());
        return CommentMapper.toDto(likedComment);
    }

    @Operation(summary = "Get all comments on a post")
    @GetMapping("/getAllComment/{postId}")
    public List<CommentDto> getAllCommentOfOnePost(@PathVariable Integer postId) throws PostException, CommentException {
        List<Comment> comments = commentService.findCommentByPostId(postId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentDtos.add(CommentMapper.toDto(comment));
        }
        return commentDtos;
    }

    @Operation(summary = "Delete a comment by ID")
    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Integer commentId,
                                @RequestHeader("Authorization") String jwt) throws CommentException {
        User user = userService.findUserByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        return "Comment deleted successfully.";
    }

}
