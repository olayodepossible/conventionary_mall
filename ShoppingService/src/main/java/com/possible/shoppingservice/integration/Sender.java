package com.possible.shoppingservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Sender {

    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

    public void send(Message message)  {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String stringMessage= objectMapper.writeValueAsString(message);
            log.info("******************************Sending message from Shopping-Service ************************\n");
            kafkaTemplate.send("shoppingCommand",stringMessage);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
