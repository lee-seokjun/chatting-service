package com.example.chattingservice.service;

import com.example.chattingservice.elastic.Chat;
import com.example.chattingservice.elastic.ChatElasticSearch;
import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.*;


@Service
@Slf4j
public class ChatServiceImpl  implements ChatService{

    private final ChatElasticSearch respository;
    private Map<String,Sinks.Many<ChatRdo>> sinks;
    public ChatServiceImpl(ChatElasticSearch respository) {

        sinks = new HashMap<>();
        this.respository = respository;
    }

    @Override
    public Mono<ChatRdo> createChat(String requestUserId, ChatCdo chatCdo) {
        Chat chat = new Chat(requestUserId,chatCdo);

        //createChat
        Sinks.Many sink =sinks.get(requestUserId);
        if(sink!=null)
        {
            return respository.save(chat)
                    .map(Chat::toRdo)
                    .doOnNext(c ->  sinks.get(requestUserId).tryEmitNext(c));
        }

        return respository.save(chat)
                .map(Chat::toRdo);
    }
    @Override
    public Flux<ChatRdo> findAll() {

        return  respository.findAll().map(Chat::toRdo);
    }
    @Override
    public Sinks.Many<ChatRdo> getSink(String userId) {
        Sinks.Many<ChatRdo> sink;
        if(   sinks.get(userId) ==null) {
            sink = Sinks.many().multicast().onBackpressureBuffer();
            sinks.put(userId,sink);
        }else {
            sink = sinks.get(userId);
        }
        return sink;
    }
}
