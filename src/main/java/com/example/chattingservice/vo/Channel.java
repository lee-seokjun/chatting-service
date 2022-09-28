package com.example.chattingservice.vo;


import com.fasterxml.jackson.annotation.JsonCreator;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;


public enum Channel {
    WRITE_MESSAGE(ResponseWrite.class, "write","target.writeId");
    private Class targetClass;
    private String type;
    private String targetName;
    Channel(Class targetClass,  String type, String targetName) {
        this.targetClass=targetClass;
        this.type=type;
        this.targetName=targetName;
    }

    public ChannelVo getTarget(Object input){
        return (ChannelVo) new ModelMapper().map(input,this.targetClass);
    }
    public String getTargetName(){return this.targetName;}
    public List<String> getTargetList(Object input){
        return getTarget(input).getTargetMembers();
    }
    @JsonCreator
    public static Channel codeOf(String type){
        return Arrays.stream(Channel.values())
                .filter( t -> t.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(type + "is illegal argument."));
    }



}
