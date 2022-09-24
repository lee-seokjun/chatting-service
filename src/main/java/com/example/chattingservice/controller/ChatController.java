package com.example.chattingservice.controller;

import com.example.chattingservice.service.ChatService;
import com.example.chattingservice.service.ChatServiceImpl;
import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    protected final ChatService chatService;
    private ModelMapper mapper;
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
        mapper = new ModelMapper();
    }

    @PostMapping
    public ChatRdo createChat(@RequestBody  ChatCdo chatCdo){
        return chatService.createChat(chatCdo);
    }
    @GetMapping
    public List<ChatRdo> findAll(){
        return chatService.findAll();
    }
}
