package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.NotificationException;
import com.rk.social_media.entity.Notification;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.NotificationRepo;
import com.rk.social_media.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Transactional
    @Override
    public Notification sendNotification(User sender, User recipient, String message, String type, Integer referenceId) {
        // First delete the existing notification of same type and reference (if any)
        notificationRepo.removeExistingNotification(sender, recipient, type, referenceId);

        // Now create and save the new notification
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setType(type);
        notification.setReferenceId(referenceId);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);

        return notificationRepo.save(notification);
    }


    @Override
    public List<Notification> getUserNotifications(Integer userId) {
        return notificationRepo.getNotificationsForUser(userId);
    }

    @Override
    public void markAsRead(Integer notificationId, Integer userId) throws NotificationException {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new NotificationException("Notification not found"));
        if (!notification.getRecipient().getId().equals(userId)) {
            throw new NotificationException("Unauthorized");
        }
        notification.setRead(true);
        notificationRepo.save(notification);
    }

}
