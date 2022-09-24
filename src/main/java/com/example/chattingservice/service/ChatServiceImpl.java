package com.example.chattingservice.service;

import com.example.chattingservice.elastic.Chat;
import com.example.chattingservice.elastic.ChatElasticSearch;
import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;


@Service
@Slf4j
public class ChatServiceImpl  implements ChatService{

    private final ChatElasticSearch respository;
    public ChatServiceImpl(ChatElasticSearch respository) {

        this.respository = respository;
    }

    @Override
    public Mono<ChatRdo> createChat(String requestUserId, ChatCdo chatCdo) {
        Chat chat = new Chat(requestUserId,chatCdo);
        return respository.save(chat)
                .map(Chat::toRdo);
    }
    @Override
    public Flux<ChatRdo> findAll() {

        return  respository.findAll().map(Chat::toRdo);
    }
}
