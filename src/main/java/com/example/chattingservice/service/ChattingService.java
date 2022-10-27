package com.example.chattingservice.service;


import com.example.chattingservice.dto.ChatMessageDto;
import com.example.chattingservice.vo.ChatMessageRdo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ChattingService {
    Flux<ChatMessageRdo> getMessages(String chatId);

    Mono<ChatMessageRdo> createMessage(ChatMessageDto chatMessage) ;
    Mono<SseEmitter> createEmitter(String chatId);
}
