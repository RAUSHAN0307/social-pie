package com.rk.social_media.utility;


import com.rk.social_media.dto.ChatDto;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ChatMapper {

    public static ChatDto toDto(Chat chat) {
        ChatDto dto = new ChatDto();
        dto.setId(chat.getId());
        dto.setChatName(chat.getChatName());
        dto.setChatImage(chat.getChatImage());
        dto.setLocalDateTime(chat.getLocalDateTime());

        List<User> users = chat.getUsers();
        List<Integer> userIds = new ArrayList<>();

        for (User user : users) {
            userIds.add(user.getId());
        }

        dto.setUserIds(userIds);


        return dto;
    }
}

