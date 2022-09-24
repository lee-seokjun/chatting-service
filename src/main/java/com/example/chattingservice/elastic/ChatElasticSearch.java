package com.example.chattingservice.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChatElasticSearch extends ElasticsearchRepository<Chat,String> {

}
