package com.rk.social_media.controller;

import com.rk.social_media.Exception.NotificationException;
import com.rk.social_media.dto.NotificationDto;
import com.rk.social_media.entity.Notification;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.NotificationMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    // Get all notifications for logged-in user
    @Operation(summary = "Get all notifications for the logged-in user")
    @GetMapping
    public List<NotificationDto> getNotifications(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        List<Notification> notifications = notificationService.getUserNotifications(user.getId());
        return notifications.stream().map(NotificationMapper::toDto).collect(Collectors.toList());
    }

    // Mark a notification as read
    @Operation(summary = "Mark a notification as read")
    @PutMapping("/{notificationId}/read")
    public String markAsRead(@RequestHeader("Authorization") String jwt,
                             @PathVariable Integer notificationId) throws NotificationException {
        User user = userService.findUserByJwt(jwt);
        notificationService.markAsRead(notificationId, user.getId());
        return "Notification marked as read";
    }
}
