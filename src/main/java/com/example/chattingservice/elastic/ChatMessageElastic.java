package com.example.chattingservice.elastic;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface ChatMessageElastic extends ReactiveElasticsearchRepository<ChatMessage,String> {
    @Query("{\"match\": {\"chatId\": {\"query\": \"?0\"}}}")
    Flux<ChatMessage> findByChatId(String chatId);
}
