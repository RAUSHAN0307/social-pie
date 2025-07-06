package com.rk.social_media.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    private LocalDateTime localDateTime;

    public Message(){}

    public Message(String content, String image, User user, Chat chat, LocalDateTime localDateTime) {
        this.content = content;
        this.image = image;
        this.user = user;
        this.chat = chat;
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}


// json ignore because there is message inside chat and chat inside message then it will run infinitely