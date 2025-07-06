package com.rk.social_media.service;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.User;

import java.util.List;

public interface ChatService {

    List<Chat> findByUserId(Integer userId);

    Chat createChat (User reqUser , User user2 , Chat chat);

    Chat finChatById(Integer chatId) throws ChatException;

}
