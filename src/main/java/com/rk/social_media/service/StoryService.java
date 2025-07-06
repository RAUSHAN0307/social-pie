package com.rk.social_media.service;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Story;
import com.rk.social_media.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface StoryService {

    public Story createStory(MultipartFile image,MultipartFile video, String captions, User user) throws IOException;

    List<Story> findStoryByUserId(Integer id) throws UserException;

    Story likeStory(Integer storyId, Integer userId) throws PostException, UserException;

    void deleteStory(Integer storyId, Integer userId) throws PostException, UserException;

}
