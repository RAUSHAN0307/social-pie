package com.rk.social_media.controller;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.PostDto;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.User;
import com.rk.social_media.response.ApiResponse;
import com.rk.social_media.service.PostService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.PostMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Operation(summary = "Create a new post (with optional image/video)")
    @PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestParam("caption") String caption,
                                              @RequestParam(value = "image" , required = false) MultipartFile image,
                                              @RequestParam(value = "video" , required = false) MultipartFile video,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Post createdPost = postService.createNewPost(caption,image,video , user.getId());
        return new ResponseEntity<>(PostMapper.toDto(createdPost), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete a post by post ID")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId , @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId,user.getId());
        ApiResponse response = new ApiResponse(message , true);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Operation(summary = "Find a post by its ID")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostById(@PathVariable Integer postId) throws PostException {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(PostMapper.toDto(post), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get all posts of the logged-in user")
    @GetMapping("/findUsersPost")
    public ResponseEntity<List<PostDto>> findUsersPost(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJwt(jwt);
        List<Post> posts = postService.findPostByUserId(user.getId());
        List<PostDto> dtos = new ArrayList<>();
        for(Post post : posts){
            dtos.add(PostMapper.toDto(post));
        }
        return new ResponseEntity<>(dtos , HttpStatus.OK);
    }

    @Operation(summary = "Get all public posts")
    @GetMapping("/allPost")
    public ResponseEntity<List<PostDto>> findAllPost(){
        List<Post> posts = postService.findAllPost();
        List<PostDto> dtos = new ArrayList<>();
        for(Post post : posts){
            dtos.add(PostMapper.toDto(post));
        }
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @Operation(summary = "Save or unsave a post")
    @PutMapping("/saved/{postId}")
    public ResponseEntity<PostDto> savedPost(@PathVariable Integer postId , @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        User user = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId , user.getId());
        return new ResponseEntity<>(PostMapper.toDto(post),HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Like or unlike a post")
    @PutMapping("/like/{postId}")
    public ResponseEntity<PostDto> likePost(@PathVariable Integer postId , @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        User user = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId , user.getId());
        return new ResponseEntity<>(PostMapper.toDto(post) , HttpStatus.ACCEPTED);
    }
}

// in case of frontend send the json + file then
// use RequestPost for only caption and
//@RequestPart("post") PostRequestDto postDto,
//@RequestPart(value = "image", required = false) MultipartFile image,
//@RequestPart(value = "video", required = false) MultipartFile video,