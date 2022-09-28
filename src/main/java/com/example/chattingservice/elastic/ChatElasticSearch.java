package com.example.chattingservice.elastic;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

    public interface ChatElasticSearch extends ReactiveElasticsearchRepository<Chat,String> {
        @Query("{\"dis_max\":{\"queries\":[{\"match\":{\"members\":{\"query\":\"?0\"}}},{\"match\":{\"?1\":{\"query\":\"?2\"}}}]}}")
        Mono<Chat> findByTargetIdAndUserId(String userId,String targetName , String targetId );
}
