package com.rk.social_media.utility;

import com.rk.social_media.dto.MessageDto;
import com.rk.social_media.entity.Message;

public class MessageMapper {

    public static MessageDto toDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setImage(message.getImage());
        dto.setUserId(message.getUser().getId());
        dto.setChatId(message.getChat().getId());
        dto.setLocalDateTime(message.getLocalDateTime());
        return dto;
    }

}

