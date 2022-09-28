package com.example.chattingservice.elastic;

import com.example.chattingservice.dto.ChatDto;
import com.example.chattingservice.vo.Channel;
import com.example.chattingservice.vo.ChannelVo;
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
    private ChannelVo target;

    private Channel channel;
    public Chat(String userId, ChatDto dto) {
        this.chatId = UUID.randomUUID().toString();
        this.channel = dto.getChannel();
        List<String> targetList = dto.getTargetMemberList();
        targetList.add(userId);
        this.members = targetList;
        this.target = dto.getTargetItem();
        this.title = dto.getTitle();

    }
    public  ChatRdo toRdo (){
        return new ChatRdo(chatId,members,title);
    }
}
