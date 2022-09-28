package com.example.chattingservice.dto;

import com.example.chattingservice.vo.Channel;
import com.example.chattingservice.vo.ChannelVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatDto implements Serializable {
    private String title;
    private Channel channel;
    private Object target;

    public ChannelVo getTargetItem(){
        return   channel.getTarget(target);
    }
    public List<String> getTargetMemberList(){
        return channel.getTargetList(target);
    }

    public String getTargetId() { return getTargetItem().getTargetId();}
}
