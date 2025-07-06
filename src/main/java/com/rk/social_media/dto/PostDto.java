package com.rk.social_media.dto;

import com.rk.social_media.entity.User;
import com.rk.social_media.response.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Data for a post including media, caption, user info, likes, and time")
public class PostDto {
    private Integer id;
    private String caption;
    private String image;
    private String video;
    private UserResponseDto user;
    private List<UserResponseDto> liked;
    private LocalDateTime createdAt;

    // Optionally, exclude comments here if not needed in all views

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public List<UserResponseDto> getLiked() {
        return liked;
    }

    public void setLiked(List<UserResponseDto> liked) {
        this.liked = liked;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}
