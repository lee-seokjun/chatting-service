package com.example.chattingservice.service;

import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


public interface ChatService {
    Mono<ChatRdo> createChat(String requestUserId, ChatCdo chatCdo);
    Flux<ChatRdo> findAll();

}
