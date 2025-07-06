package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.Exception.MessageException;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.Message;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.ChatRepo;
import com.rk.social_media.repo.MessageRepo;
import com.rk.social_media.service.ChatService;
import com.rk.social_media.service.MessageService;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.UploadToCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    UploadToCloudService uploadToCloudService;

    @Override
    public List<Message> findByChatId(Integer chatId) throws MessageException, ChatException {
        Chat chat = chatService.finChatById(chatId); // for checking this chat is exist or not
        return messageRepo.findByChatId(chatId);
    }

    @Override
    public Message createMessage(User user, Integer chatId, String content , MultipartFile file) throws MessageException, ChatException, IOException {


        String imageUrl = null;
        if(file != null && !file.isEmpty()){
            imageUrl = uploadToCloudService.uploadImage(file);
        }
        Message message = new Message();
        Chat chat = chatService.finChatById(chatId);
        message.setChat(chat);
        message.setLocalDateTime(LocalDateTime.now());
        message.setUser(user);
        message.setContent(content);
        message.setImage(imageUrl);
        Message savedMessage = messageRepo.save(message);
        chat.getMessages().add(savedMessage);
        chatRepo.save(chat);

        for (User participant : chat.getUsers()) {
            if (!participant.getId().equals(user.getId())) {
                String notifMessage = user.getFirstName() + " sent you a message";

                notificationService.sendNotification(
                        user,
                        participant,
                        notifMessage,
                        "NEW_MESSAGE",
                        chat.getId()
                );
                break; // since only one receiver
            }
        }
        return savedMessage;
    }
    // for creating and updating chat
}
