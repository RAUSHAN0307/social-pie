package com.rk.social_media.controller;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.StoryDto;
import com.rk.social_media.entity.Story;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.StoryService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.StoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new story with image/video and caption")
    @PostMapping("/create")
    public StoryDto createStory(@RequestParam(value = "image" , required = false) MultipartFile image,
                                @RequestParam(value = "video" , required = false) MultipartFile video,
                                @RequestParam("captions") String captions,
                                @RequestHeader ("Authorization") String jwt) throws IOException {
        User user = userService.findUserByJwt(jwt);
        Story createdStory = storyService.createStory(image,video,captions,user);
        return StoryMapper.toDto(createdStory);
    }

    @Operation(summary = "Get all stories of the logged-in user")
    @GetMapping("/get")
    public List<StoryDto> findByUserId(@RequestHeader ("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        List<Story> stories = storyService.findStoryByUserId(user.getId());
        List<StoryDto> storyDtos = new ArrayList<>();
        for(Story story : stories){
            storyDtos.add(StoryMapper.toDto(story));
        }
        return storyDtos;
    }

    @Operation(summary = "Like or unlike a story")
    @PutMapping("/like/{storyId}")
    public StoryDto likeStory(@PathVariable Integer storyId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        User user = userService.findUserByJwt(jwt);
        Story likedStory = storyService.likeStory(storyId, user.getId());
        return StoryMapper.toDto(likedStory);
    }

    @Operation(summary = "Delete a story by ID")
    @DeleteMapping("/delete/{storyId}")
    public String deleteStory(@PathVariable Integer storyId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        User user = userService.findUserByJwt(jwt);
        storyService.deleteStory(storyId, user.getId());
        return "Story deleted successfully.";
    }

}
// same story like
// story comment
// story delete