package com.rk.social_media.service;

import com.rk.social_media.Exception.NotificationException;
import com.rk.social_media.entity.Notification;
import com.rk.social_media.entity.User;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(User sender, User recipient, String message, String type, Integer referenceId);
    List<Notification> getUserNotifications(Integer userId);
    void markAsRead(Integer notificationId, Integer userId) throws NotificationException;
}
