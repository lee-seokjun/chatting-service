package com.example.chattingservice.controller;

import com.example.chattingservice.dto.ChatDto;
import com.example.chattingservice.service.ChatService;
import com.example.chattingservice.service.ChatServiceImpl;
import com.example.chattingservice.vo.ChatCdo;
import com.example.chattingservice.vo.ChatRdo;
import org.modelmapper.ModelMapper;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@RestController
@RequestMapping("/chat")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
public class ChatController {
    protected final ChatService chatService;
    private ModelMapper mapper;
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
        mapper = new ModelMapper();
    }

    @PostMapping
    public Mono<ChatRdo> createChat(@RequestHeader ("userId") String requestUserId, @RequestBody  ChatCdo chatCdo){
        return chatService.createOrGetChat(requestUserId,new ModelMapper().map(chatCdo, ChatDto.class));
    }
    @GetMapping("/mychat")
    public Flux<ChatRdo> findMyChat(@RequestHeader ("userId") String requestUserId)
    {
        return chatService.findMyChat(requestUserId);
    }
    @GetMapping
    public Flux<ChatRdo> findAll(){
        return chatService.findAll();
    }
    @GetMapping("/sse/{userId}")
    public Flux<ServerSentEvent<ChatRdo>> sse(
//            @RequestHeader ("userId") String userId
            @PathVariable String userId
    ){
        Sinks.Many<ChatRdo> sink  = chatService.getSink(userId);

        return sink.asFlux().map(c-> ServerSentEvent.builder(c).build());
    }
}
