package com.example.chattingservice.vo;

import lombok.Data;

@Data
public class ChatCdo {
    private String title;
    private Channel channel;
    private Object target;
}
