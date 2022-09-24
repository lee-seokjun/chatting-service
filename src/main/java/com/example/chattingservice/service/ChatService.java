package com.example.chattingservice.service;

import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;

import java.util.List;

public interface ChatService {
    ChatRdo createChat(ChatCdo chatCdo);
    List<ChatRdo> findAll();
}
