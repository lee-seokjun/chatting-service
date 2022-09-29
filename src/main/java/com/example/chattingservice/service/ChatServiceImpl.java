package com.example.chattingservice.service;

import com.example.chattingservice.dto.ChatDto;
import com.example.chattingservice.elastic.Chat;
import com.example.chattingservice.elastic.ChatElastic;
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

    private final ChatElastic respository;
    private Map<String,Sinks.Many<ChatRdo>> sinks;
    public ChatServiceImpl(ChatElastic respository) {

        sinks = new HashMap<>();
        this.respository = respository;
    }
    //add kafka consumer

    @Override
    public Mono<ChatRdo> createOrGetChat(String requestUserId, ChatDto chatDto) {
        //채팅방 목록 아닌 곳에서 채팅하기를 눌렀을 때
        // ex) 프로필보기에서 채팅하기 / 게시글에서 채팅하기 버튼 클릭
        //채팅이 존재 하면 get , 존재하지 않으면 create

        String targetName = chatDto.getChannel().getTargetName();
        String targetId = chatDto.getTargetId();

        return respository.findByTargetIdAndUserId(requestUserId,targetName,targetId)
                .map(Chat::toRdo)
                .switchIfEmpty( Mono.defer(() ->createChat(requestUserId,chatDto)));

    }

    @Override
    public Mono<ChatRdo> createChat(String requestUserId, ChatDto chatCdo) {
        Chat chat = new Chat(requestUserId,chatCdo);

        Sinks.Many sink =sinks.get(requestUserId);
        if(sink!=null)
        {
            return respository.save(chat)
                    .map(Chat::toRdo)
                    .doOnNext(rdo ->rdo.getMembers().forEach(m -> sinks.get(m).tryEmitNext(rdo)));
                    //채팅창 멤버 모두에게 sse 전송
        }

        return respository.save(chat)
                .map(Chat::toRdo);
    }
    @Override
    public Flux<ChatRdo> findMyChat(String requestUserId) {
        return respository.findByUserId(requestUserId)
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
