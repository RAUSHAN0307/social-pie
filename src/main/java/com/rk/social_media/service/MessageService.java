package com.rk.social_media.service;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.Exception.MessageException;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.Message;
import com.rk.social_media.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    List<Message> findByChatId(Integer chatId) throws MessageException, ChatException;

    Message createMessage(User user, Integer chatId , String content , MultipartFile file) throws MessageException, ChatException, IOException;


}
