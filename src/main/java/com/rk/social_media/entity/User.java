package com.rk.social_media.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "first name should not be empty!")
    private String firstName;

    private String lastName;

    @NotBlank (message = "gender should not be empty")
    private String gender;

    private String profilePic;
    @Email
    @NotEmpty
    private String email;

    @Size(min = 6 , message = "week password")
    @NotBlank
    private String password;

    // followers
    @ElementCollection
    @CollectionTable(name = "user_followers" , joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_id")
    private List<Integer> followers;

    // following
    @ElementCollection
    @CollectionTable(name = "user_following" , joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_id")
    private List<Integer> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reels> reels = new ArrayList<>();

    // saved Post
    @ManyToMany
    private List<Post> savedPost;

    public List<Post> getSavedPost() {
        return savedPost;
    }

    public void setSavedPost(List<Post> savedPost) {
        this.savedPost = savedPost;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public Integer getId() {
        return id;
    }

    public List<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Integer> following) {
        this.following = following;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Reels> getReels() {
        return reels;
    }

    public void setReels(List<Reels> reels) {
        this.reels = reels;
    }

    public User(){

    }

    public User(String firstName, String lastName, String gender, String profilePic, String email, String password, List<Integer> followers, List<Integer> following, List<Post> posts, List<Reels> reels, List<Post> savedPost) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.profilePic = profilePic;
        this.email = email;
        this.password = password;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.reels = reels;
        this.savedPost = savedPost;
    }
}
