package com.rk.social_media.controller;

import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.UserDto;
import com.rk.social_media.dto.UserProfileDto;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User operations like profile, follow, update, and search")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Get user by ID")
    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return UserMapper.convertToDTO(user);
    }
    @Operation(summary = "Get user by email")
    @GetMapping("/email")
    public UserDto findByEmail(@RequestParam String email) throws UserException {
        User user = userService.findUserByEmail(email);
        return UserMapper.convertToDTO(user);
    }

    @Operation(summary = "Follow a user")
    @PutMapping("/follow/{targetUserId}")
    public String followUser(@PathVariable Integer targetUserId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        userService.followUser(user.getId(), targetUserId);
        return "You are now following user with ID " + targetUserId;
    }

    @Operation(summary = "Unfollow a user")
    @PutMapping("/unfollow/{targetUserId}")
    public String unfollowUser(@PathVariable Integer targetUserId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        userService.unfollowUser(user.getId(), targetUserId);
        return "You have unfollowed user with ID " + targetUserId;
    }

    @Operation(summary = "Get your followers")
    @GetMapping("/followers")
    public List<UserDto> getFollowers(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return userService.getFollowers(user.getId());
    }

    @Operation(summary = "Get users you are following")
    @GetMapping("/following")
    public List<UserDto> getFollowing(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return userService.getFollowing(user.getId());
    }

    @Operation(summary = "Update your user profile")
    @PutMapping("/update")
    public UserDto updateUser(@RequestHeader("Authorization") String jwt , @RequestBody User users) throws UserException {
        User user = userService.findUserByJwt(jwt);
        User updated = userService.updateUser(users , user.getId());
        return UserMapper.convertToDTO(updated);
    }

    @Operation(summary = "Search user by name or email")
    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam("query") String query){

        List<User> users = userService.searchUser(query);
        List<UserDto> dtos = new ArrayList<>();

        for (User user : users) {
            UserDto dto = UserMapper.convertToDTO(user);
            dtos.add(dto);
        }
        return dtos;
    }

    // here request header is a type of mapping like request body path variable this is for
    // if something come from the header
    @Operation(summary = "Get your own profile using JWT")
    @GetMapping("/profile")
    public UserDto getUserFromToken(@RequestHeader ("Authorization") String token){
//        System.out.println("jwt:------- " + token);// to check that token is coming or not check on the console
        User user = userService.findUserByJwt(token);
        return UserMapper.convertToDTO(user);
    }

    // for anyones profile
    @Operation(summary = "View public profile of another user")
    @GetMapping("/profile/{userId}")
    public UserProfileDto viewUserProfile(@PathVariable Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return UserMapper.toUserProfile(user); // returns public-safe DTO
    }

}
