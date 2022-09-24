package com.example.chattingservice.elastic;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface ChatElasticSearch extends ReactiveElasticsearchRepository<Chat,String> {

}
