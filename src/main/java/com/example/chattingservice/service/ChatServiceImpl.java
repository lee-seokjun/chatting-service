package com.example.chattingservice.service;

import com.example.chattingservice.elastic.Chat;
import com.example.chattingservice.elastic.ChatElasticSearch;
import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ChatServiceImpl  implements ChatService{

    private final ChatElasticSearch respository;
    private final ModelMapper mapper;
    public ChatServiceImpl(ChatElasticSearch respository) {

        this.respository = respository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ChatRdo createChat(ChatCdo chatCdo) {
        Chat chat = mapper.map(chatCdo,Chat.class);
        chat.setChatId(UUID.randomUUID().toString());

        Chat savedChat = respository.save(chat);

        return mapper.map(savedChat,ChatRdo.class);
    }



    @Override
    public List<ChatRdo> findAll() {
        List<ChatRdo> rdo= new ArrayList<>();
        respository.findAll().forEach(v->rdo.add(mapper.map(v,ChatRdo.class)));
        return  rdo;
    }
}
