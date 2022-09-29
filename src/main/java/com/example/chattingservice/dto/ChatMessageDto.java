package com.example.chattingservice.dto;

import com.example.chattingservice.elastic.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
public class ChatMessageDto implements Serializable {

    private String chatId;

    private String message;
    private Date createdAt;
    private String senderId;

    public static ChatMessage toEntity(ChatMessageDto dto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper.map(dto,ChatMessage.class);
    }

}
