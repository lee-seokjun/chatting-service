package com.example.chattingservice.elastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Document(indexName="chat")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String chatId;
    private List<String> members;
    private String title;

}
