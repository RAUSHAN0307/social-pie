package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.ChatRepo;
import com.rk.social_media.service.ChatService;
import com.rk.social_media.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Chat> findByUserId(Integer userId) {
        return chatRepo.findByUsersId(userId);
    }

    @Override
    public Chat createChat(User reqUser, User user2 , Chat chat) {
        Chat isExist = chatRepo.findChatByUserId(user2 , reqUser);
        if(isExist != null){
            return isExist;
        }

        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setLocalDateTime(LocalDateTime.now());
        notificationService.sendNotification(user2, reqUser ,"You have a new chat with " , "Chat" , user2.getId());

        return chatRepo.save(chat);
    }

    @Override
    public Chat finChatById(Integer chatId) throws ChatException {
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new ChatException("chat not found with id " + chatId));

        return chat;
    }
}
