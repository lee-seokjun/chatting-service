package com.example.chattingservice.vo;


import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class ChannelVo {


    public abstract List<String> getTargetMembers();
    public abstract String getTargetId();
}
