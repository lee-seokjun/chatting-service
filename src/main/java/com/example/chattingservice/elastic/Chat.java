package com.example.chattingservice.elastic;

import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(indexName="chat")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String chatId;
    private List<String> members;
    private String title;

    public Chat(String userId, ChatCdo cdo) {
        this.chatId = UUID.randomUUID().toString();
        this.members = cdo.getMembers();
        members.add(userId);
        this.title = cdo.getTitle();

    }
    public  ChatRdo toRdo (){
        return new ChatRdo(chatId,members,title);
    }
}
