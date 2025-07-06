package com.rk.social_media.controller;

import com.rk.social_media.Exception.ChatException;
import com.rk.social_media.Exception.MessageException;
import com.rk.social_media.dto.MessageDto;
import com.rk.social_media.entity.Message;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.MessageService;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.MessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/msg")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Operation(summary = "Send a message in a chat")
    @PostMapping("/send/{chatId}")
    public MessageDto createMessage(@PathVariable Integer chatId ,
                                    @RequestParam("content") String content ,
                                    @RequestParam(value = "file" , required = false) MultipartFile file,
                                    @RequestHeader ("Authorization") String jwt) throws ChatException, MessageException, IOException {
        User user = userService.findUserByJwt(jwt);

        Message createdMessage = messageService.createMessage(user,chatId,content , file);
        return MessageMapper.toDto(createdMessage);
    }

    @Operation(summary = "Get all messages from a chat")
    @GetMapping("/chat-messages/{chatId}")
    public List<MessageDto> getAllMessages(@PathVariable Integer chatId , @RequestHeader ("Authorization") String jwt) throws ChatException, MessageException {
        User user = userService.findUserByJwt(jwt); //Ensure the JWT token is valid
        List<MessageDto> messageDtos = new ArrayList<>();
        List<Message> messages = messageService.findByChatId(chatId);
        for(Message message : messages){
            messageDtos.add(MessageMapper.toDto(message));
        }
        return messageDtos;
    }
}
