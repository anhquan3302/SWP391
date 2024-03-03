package com.example.securityl.controller;

import com.example.securityl.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ChatMessage sendMessage(@Payload ChatMessage message){
        message.setTimestamp(new Date());
        message.setNickname("EFurniture Shop");
        return message;
    }
}
