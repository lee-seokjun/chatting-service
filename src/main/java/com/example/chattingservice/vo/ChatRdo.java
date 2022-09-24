package com.example.chattingservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatRdo {
    private String chatId;
    private List<String> members;
    private String title;
}
