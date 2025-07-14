package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.PostException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Story;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.StoryRepo;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.StoryService;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class StoryServiceImplementation implements StoryService {


    @Autowired
    private StoryRepo storyRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UploadToCloudService uploadToCloudService;

    @Override
    public Story createStory(MultipartFile image, MultipartFile video, String captions, User user) throws IOException {

        // uploading image
        String imageUrl = null;
        if(image != null && !image.isEmpty()){
            imageUrl = uploadToCloudService.uploadImage(image);
        }

        // uploading video
        String videoUrl = null;
        if(video != null && !video.isEmpty()){
            videoUrl = uploadToCloudService.uploadVideo(video);
        }
        Story createStory = new Story();
        createStory.setCaptions(captions);
        createStory.setImage(imageUrl);
        createStory.setVideo(videoUrl);
        createStory.setUser(user);
        createStory.setTimeStamps(LocalDateTime.now());
        return storyRepo.save(createStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer id) throws  UserException {
        User user = userService.findUserById(id);
        List<Story> allStories = storyRepo.findByUserId(id);
        List<Story> validStories = new ArrayList<>();

        // that story will be not return which is expired
        for (Story story : allStories) {
            if (!story.isDeleted() && story.getTimeStamps() != null &&
                    story.getTimeStamps().isAfter(LocalDateTime.now().minusHours(24))) {
                validStories.add(story);
            }
        }

        return validStories;
    }

    @Override
    public Story likeStory(Integer storyId, Integer userId) throws PostException, UserException {
        Story story = storyRepo.findById(storyId)
                .orElseThrow(() -> new PostException("Story not found with id: " + storyId));
        User user = userService.findUserById(userId);
        boolean isLiked = false;
        if (story.getLiked().contains(user)) {
            story.getLiked().remove(user);
        } else {
            story.getLiked().add(user);
            isLiked = true;
        }
        if(isLiked && !story.getUser().getId().equals(userId)){
            String message = user.getFirstName() + " liked your story";
            notificationService.sendNotification(
                    user,
                    story.getUser(),
                    message,
                    "LIKE_STORY",
                    story.getId()
            );
        }
        return storyRepo.save(story);
    }

    @Override
    public void deleteStory(Integer storyId, Integer userId) throws PostException, UserException {
        Story story = storyRepo.findById(storyId)
                .orElseThrow(() -> new PostException("Story not found with id: " + storyId));
        User user = userService.findUserById(userId);

        if (!story.getUser().getId().equals(user.getId())) {
            throw new PostException("You can only delete your own story.");
        }

        story.setDeleted(true);
        storyRepo.save(story);
    }

    // scheduled is automatically remove the story from database who has expired
    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void deleteExpiredStories() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        List<Story> expired = storyRepo.findByTimeStampsBeforeAndIsDeletedFalse(cutoff);
        for (Story story : expired) {
            story.setDeleted(true);
        }
        storyRepo.saveAll(expired);
    }

    @Scheduled(cron = "0 0 3 * * *") // Every day at 3 AM
public void permanentlyDeleteOldStories() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30); // Keep 30 days of deleted data
        storyRepo.deleteAllByIsDeletedTrueAndTimeStampsBefore(cutoff);
    }



}
