package com.example.chattingservice.vo;

import lombok.Data;

@Data
public class ChatMessageCdo {
    private String chatId;
    private String message;
    private String senderId;
}
