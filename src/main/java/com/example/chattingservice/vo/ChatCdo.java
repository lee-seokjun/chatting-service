package com.example.chattingservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatCdo {
    private List<String> members;
    private String title;
}
