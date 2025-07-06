package com.rk.social_media.service;


import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.UserDto;
import com.rk.social_media.entity.User;
import com.rk.social_media.request.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
//    public User registerUser(User user);
    public  User findUserById(Integer userId) throws UserException;

    public User findUserByEmail(String email) throws UserException;

    public User followUser(Integer userId1 , Integer userId2) throws UserException;

    public User unfollowUser(Integer reqUserId, Integer userId2) throws UserException;

    public User updateUser(UserUpdateRequest request, MultipartFile file, Integer userId) throws UserException, IOException;

    //here we are using query because user can search using name,firstname
    // lastname or email thats why we are using query
    public List<User> searchUser(String query);

    public User findUserByJwt(String jwt);

    List<UserDto> getFollowers(Integer userId) throws UserException;

    List<UserDto> getFollowing(Integer userId) throws UserException;


}
