package com.example.chattingservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRdo {

    public ChatMessageRdo(String chatId, String senderId, String message) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.message = message;
    }

    private String chatId;
    private String senderId;
    private String message;
    private Date createdAt;
}
