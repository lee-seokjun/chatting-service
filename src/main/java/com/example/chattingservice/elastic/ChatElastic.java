package com.example.chattingservice.elastic;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

    public interface ChatElastic extends ReactiveElasticsearchRepository<Chat,String> {
        @Query("{ \"bool\" : { \"must\" : [ { \"query_string\" : { \"query\" : \"?0\", \"fields\" : [ \"members\" ] } }, { \"query_string\" : { \"query\" : \"?2\", \"fields\" : [ \"?1\" ] } } ] } }")
        Mono<Chat> findByTargetIdAndUserId(String userId,String targetName , String targetId );
        @Query("{\"match\": {\"members\": {\"query\": \"?0\"}}}")
        Flux<Chat> findByUserId(String requestUserId);
    }
