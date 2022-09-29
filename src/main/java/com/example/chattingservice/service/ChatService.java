package com.example.chattingservice.service;

import com.example.chattingservice.dto.ChatDto;
import com.example.chattingservice.vo.ChatRdo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


public interface ChatService {
    Mono<ChatRdo> createChat(String requestUserId, ChatDto chatCdo);
    Flux<ChatRdo> findAll();

    Sinks.Many<ChatRdo> getSink(String userId);
    Mono<ChatRdo> createOrGetChat(String requestUserId, ChatDto chatDto);

    Flux<ChatRdo> findMyChat(String requestUserId);
}
