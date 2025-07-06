package com.rk.social_media.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Reels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String video;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "reels_likes",
            joinColumns = @JoinColumn(name = "reels_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> liked;

    private boolean isDeleted = false;


    public Reels(){}

    public Reels(String title, String video, User user, List<User> liked, boolean isDeleted) {
        this.title = title;
        this.video = video;
        this.user = user;
        this.liked = liked;
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
