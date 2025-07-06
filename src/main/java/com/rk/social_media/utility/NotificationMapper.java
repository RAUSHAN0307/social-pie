package com.rk.social_media.utility;

import com.rk.social_media.dto.NotificationDto;
import com.rk.social_media.entity.Notification;

public class NotificationMapper {
    public static NotificationDto toDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setReferenceId(notification.getReferenceId());
        dto.setRead(notification.isRead());
        dto.setSenderId(notification.getSender().getId());
        dto.setRecipientId(notification.getRecipient().getId());
        dto.setTimestamp(notification.getTimestamp());
        return dto;
    }
}
