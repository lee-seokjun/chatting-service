package com.example.chattingservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRdo {
    private String chatId;
    private List<String> members;
    private String title;
}
