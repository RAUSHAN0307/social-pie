package com.rk.social_media.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Data Transfer Object for Story")
public class StoryDto {
    private Integer id;
    private String image;
    private String video;
    private String captions;
    private LocalDateTime timeStamps;
    private Integer userId;
    private List<Integer> likedUserIds;
//    private boolean isDeleted;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public String getCaptions() { return captions; }
    public void setCaptions(String captions) { this.captions = captions; }

    public LocalDateTime getTimeStamps() { return timeStamps; }
    public void setTimeStamps(LocalDateTime timeStamps) { this.timeStamps = timeStamps; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getLikedUserIds() { return likedUserIds; }
    public void setLikedUserIds(List<Integer> likedUserIds) { this.likedUserIds = likedUserIds; }

//    public boolean isDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        isDeleted = deleted;
//    }
}
