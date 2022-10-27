package com.example.chattingservice.controller;

import com.example.chattingservice.dto.ChatMessageDto;
import com.example.chattingservice.service.ChattingService;
import com.example.chattingservice.service.ChattingServiceImpl;
import com.example.chattingservice.vo.ChatMessageCdo;
import com.example.chattingservice.vo.ChatMessageRdo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/messages")
public class ChattingController {
    private ChattingService service;
    private ModelMapper mapper;
    public ChattingController(ChattingServiceImpl service) {
        this.service = service;
        mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping
    public Mono<ChatMessageRdo> sendMessage( @RequestBody ChatMessageCdo chatMessageCdo){
        return service.createMessage(mapper.map(chatMessageCdo,ChatMessageDto.class));

    }

    @GetMapping("/{chatId}")
    public Flux<ChatMessageRdo> getMessage(@PathVariable String chatId){

        return service.getMessages(chatId);
    }
    @CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/sse/{chatId}")
    public Mono<SseEmitter> sse(@PathVariable String chatId){

        return service.createEmitter(chatId);
    }
}
