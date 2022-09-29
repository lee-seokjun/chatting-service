package com.example.chattingservice.service;


import com.example.chattingservice.dto.ChatMessageDto;
import com.example.chattingservice.vo.ChatMessageRdo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ChattingService {
    Flux<ChatMessageRdo> getMessages(String chatId);

    Mono<ChatMessageRdo> createMessage(ChatMessageDto chatMessage) ;
}
