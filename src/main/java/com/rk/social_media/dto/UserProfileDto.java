package com.rk.social_media.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "User profile including basic info, posts, and reels")
public class UserProfileDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String profilePic;

    private List<PostDto> posts;
    private List<ReelsDto> reels;

    public UserProfileDto(){}
    public UserProfileDto(String firstName, String lastName, String gender, String profilePic, List<PostDto> posts, List<ReelsDto> reels) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.profilePic = profilePic;
        this.posts = posts;
        this.reels = reels;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<ReelsDto> getReels() {
        return reels;
    }

    public void setReels(List<ReelsDto> reels) {
        this.reels = reels;
    }
}
