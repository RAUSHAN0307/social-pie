package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.UserException;
import com.rk.social_media.config.JwtProvider;
import com.rk.social_media.dto.UserDto;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.UserRepo;
import com.rk.social_media.request.UserUpdateRequest;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class userServiceImplementation implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UploadToCloudService uploadToCloudService;

    @Override
    public User findUserById(Integer userId) throws UserException {
       Optional <User> user = userRepo.findById(userId);
        if(user.isPresent()) return user.get();
        else{
            throw new UserException("user not exist with userId " + userId);
        }
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        if(email == null) throw new UserException("no any registerd using this email " + email);
        return userRepo.findByEmail(email);
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserException {
        Optional<User> reqUser = userRepo.findById(reqUserId);
        Optional<User> user2 = userRepo.findById(userId2);
        if (reqUser.isEmpty() || user2.isEmpty()) {
            throw new UserException("One or both users not found.");
        }
        User user3 = reqUser.get();
        User user4 = user2.get();
        if (!user4.getFollowers().contains(user3.getId())) {
            user4.getFollowers().add(user3.getId());
        }
        if (!user3.getFollowing().contains(user4.getId())) {
            user3.getFollowing().add(user4.getId());
        }

        userRepo.save(user3);
        userRepo.save(user4);
        return user3;
    }

    @Override
    public User unfollowUser(Integer reqUserId, Integer userId2) throws UserException {
        Optional<User> reqUser = userRepo.findById(reqUserId);
        Optional<User> user2 = userRepo.findById(userId2);

        if (reqUser.isEmpty() || user2.isEmpty()) {
            throw new UserException("One or both users not found.");
        }

        User user3 = reqUser.get();
        User user4 = user2.get();

        user4.getFollowers().remove(user3.getId());
        user3.getFollowing().remove(user4.getId());

        userRepo.save(user3);
        userRepo.save(user4);
        return user3;
    }


    @Override
    public User updateUser(UserUpdateRequest request, MultipartFile file, Integer id) throws UserException, IOException {
        Optional<User> oldUser = userRepo.findById(id);
        if(oldUser.isEmpty()) throw new UserException("user is not exist with id " + id);

        // update profile image
        String imageUrl = null;
        if(file != null && !file.isEmpty()){
            imageUrl = uploadToCloudService.uploadImage(file);
        }

        if(request.getFirstName() != null) oldUser.get().setFirstName(request.getFirstName());
        if(request.getLastName() != null) oldUser.get().setLastName(request.getLastName());
        if(imageUrl != null) oldUser.get().setProfilePic(imageUrl);
        if(request.getGender() != null) oldUser.get().setGender(request.getGender());
        if(request.getEmail() != null) oldUser.get().setEmail(request.getEmail());
        userRepo.save(oldUser.get());
        return oldUser.get();
    }

    @Override
    public List<User> searchUser(String query) {
        return  userRepo.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
         String email = JwtProvider.getEmailFromJwtToken(jwt);
         return userRepo.findByEmail(email);
    }

    @Override
    public List<UserDto> getFollowers(Integer userId) throws UserException {
        User user = findUserById(userId);
        List<UserDto> followers = new ArrayList<>();
        for (Integer followerId : user.getFollowers()) {
            User follower = findUserById(followerId);
            followers.add(UserMapper.convertToDTO(follower));
        }
        return followers;
    }

    @Override
    public List<UserDto> getFollowing(Integer userId) throws UserException {
        User user = findUserById(userId);
        List<UserDto> following = new ArrayList<>();
        for (Integer followingId : user.getFollowing()) {
            User followingUser = findUserById(followingId);
            following.add(UserMapper.convertToDTO(followingUser));
        }
        return following;
    }


}
