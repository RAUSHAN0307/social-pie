package com.rk.social_media.controller;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.ChatDto;
import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.ChatService;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.ChatMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadToCloudService uploadToCloudService;


    @Operation(summary = "Create a new chat with another user")
    @PostMapping("/create/{userId}")
    public ChatDto createChat(@RequestHeader("Authorization") String jwt , @PathVariable Integer userId ,
                              @RequestParam("chatName") String chatName ,
                              @RequestParam (value = "file" , required = false)MultipartFile file) throws UserException, IOException {

        String chatImage = null;
        if(file != null && !file.isEmpty()){
            chatImage = uploadToCloudService.uploadImage(file);
        }

        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setChatImage(chatImage);

        User user = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(userId);
        Chat createdChat = chatService.createChat(user,user2 , chat);
        return ChatMapper.toDto(createdChat);
    }

    @Operation(summary = "Get all chats of the logged-in user")
    @GetMapping("/usersChat")
    public List<ChatDto> findUsersChat(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJwt(jwt);
        List<ChatDto> usersChat = new ArrayList<>();
        List<Chat> chats = chatService.findByUserId(user.getId());
        for(Chat chat : chats){
            usersChat.add(ChatMapper.toDto(chat));
        }
        return usersChat;
    }

}