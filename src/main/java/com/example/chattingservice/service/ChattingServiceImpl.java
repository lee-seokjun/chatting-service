package com.example.chattingservice.service;


import com.example.chattingservice.dto.ChatMessageDto;
import com.example.chattingservice.elastic.ChatElastic;
import com.example.chattingservice.elastic.ChatMessage;
import com.example.chattingservice.elastic.ChatMessageElastic;
import com.example.chattingservice.kafka.KafkaProducer;
import com.example.chattingservice.vo.ChatMessageRdo;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class ChattingServiceImpl implements ChattingService{

    private final ChatMessageElastic repository;
    private final ChatElastic chatRepository;
    private final KafkaProducer kafkaProducer;
    private final Cache<String, SseEmitter> sseEmitterCache;
    public ChattingServiceImpl( ChatMessageElastic repository
            ,ChatElastic chatRepository
            ,KafkaProducer kafkaProducer
            ,Cache<String, SseEmitter> sseEmitterCache

    ) {
        this.repository = repository;
        this.chatRepository =chatRepository;
        this.kafkaProducer =kafkaProducer;
        this.sseEmitterCache =sseEmitterCache;
    }

    @Override
    @Transactional
    public Mono<ChatMessageRdo> createMessage(ChatMessageDto dto) {
        ChatMessage chatMessage = ChatMessageDto.toEntity(dto);
        chatMessage.setMessageId(UUID.randomUUID().toString());
        chatMessage.setCreatedAt(new Date());


        /**
         *        createMessage return Success or fail 로만 사용.
         *        화면에 출력 되는 message 는 Websockets 로만 append 한다.
         */
        return chatRepository.findById(dto.getChatId())
                .switchIfEmpty(Mono.empty())
                .doOnNext(
                        c->{
                            log.info(String.valueOf(c.getMembers().contains(dto.getSenderId())));
                            if(c.getMembers().contains(dto.getSenderId()))
                            {
                                repository.save(chatMessage).log()
                                .map(entity->entity.toKafka(c.getMembers()))
                                .doOnNext(m->kafkaProducer.send("message-send",m))
                                .subscribe();
                            }
                        }
                             )
                .map(c-> new ChatMessageRdo(c.getChatId(),dto.getSenderId(),dto.getMessage()));
    }


    @Override
    public Flux<ChatMessageRdo> getMessages(String chatId) {

        return repository.findByChatId(chatId)
                .map(ChatMessage::toRdo);

    }

    @Override
    public Mono<SseEmitter> createEmitter(String chatId) {
        SseEmitter  sseEmitter = new SseEmitter( 10l * 60l * 1000l);
        String eventName = chatId+"_"+System.currentTimeMillis();
        sseEmitterCache.put(eventName, sseEmitter);
        return Mono.just(sseEmitter);
    }
}
