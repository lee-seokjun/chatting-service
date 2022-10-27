package com.example.chattingservice.kafka;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {

    Cache<String, SseEmitter> sseEmitterCache;
    Cache<String, String> sseEventCache;

    public KafkaConsumer( Cache<String, SseEmitter> sseEmitterCache, Cache<String, String> sseEventCache) {
        this.sseEmitterCache = sseEmitterCache;
        this.sseEventCache = sseEventCache;
    }

    @KafkaListener(topics = "message-send")
    public void createAlert(String kafkaMessage){
        log.info("kafka Message : ->" + kafkaMessage );
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<Object,Object> map ;
            map =mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
            String chatId = (String) map.get("chatId");
            sseEmitterCache.forEach(
                    (key,emitter) ->
                    {
                        if( key.startsWith(chatId+"_"))
                        {
                            SseEmitter.SseEventBuilder event = emitter.event()
                                    .data(map);
                            try {
                                emitter.send(event);
                            }catch(IOException | IllegalStateException e){
                                log.error(e.getMessage());
                                sseEmitterCache.remove(key);
                            }
                        }

                    }


            );

            log.debug(map.toString());
        }catch(JsonProcessingException x){
            x.printStackTrace();
        }



    }



}
