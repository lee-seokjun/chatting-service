package com.example.chattingservice.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseWrite  extends ChannelVo {
    private String title;
    private String content;

    private Date createdAt;
    private String writeId;
    private String userId;

    @Override
    public List<String> getTargetMembers() {
        List<String> list = new ArrayList<>();
        list.add(this.userId);
        return list;
    }

    @Override
    public String getTargetId() {
        return writeId;
    }
}
