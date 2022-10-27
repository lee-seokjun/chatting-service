package com.example.chattingservice.kafka;

import com.example.chattingservice.vo.KafkaSendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String,String> kafkaTemplate ;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(String topic, KafkaSendMessage message){
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString= "";
        try {
            jsonInString = mapper.writeValueAsString(message.toMap());
            kafkaTemplate.send(topic,jsonInString);
            log.info("Kafka Producer sent data : " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



    }


}
