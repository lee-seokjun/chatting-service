package com.example.chattingservice.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
public class KafkaSendMessage {
    private final String chatId;
    private final String senderId;
    private final String message;
    private final Date createdAt;
    private final List<String> toUsers;

    public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<>();
        map.put("senderId",senderId);
        map.put("message",message);
        map.put("chatId",chatId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        map.put("createdAt",dateFormat.format(createdAt));
        String users = toUsers.stream().reduce((a,b) -> a+","+b).orElse("");
        map.put("toUsers",users);
        return map;
    }

}
