package com.example.kafkatesting.kafka;

import com.example.kafkatesting.model.Entity;
import com.example.kafkatesting.model.GameMemory;
import com.example.kafkatesting.model.GameRender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumer() {
    }



   /*@KafkaListener(topics = "${kafka.topic.test}")
    public void receive(String payload) {
        try {
            System.out.println(payload);
            GameRender gameRender = objectMapper.readValue(payload, GameRender.class);
            GameMemory gameMemory = GameMemory.getInstance();
            gameMemory.setGameRender(gameRender);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //LOGGER.info("received payload='{}'", payload);

    }*/

    @KafkaListener(topics = "${kafka.topic.test}", containerFactory = "kafkaListenerContainerFactory")
    public void processMessage(GameRender gameRender) {
        GameMemory.getInstance().setGameRender(gameRender);
        //LOGGER.info("received payload='{}'", payload);

    }

    @KafkaListener(topics = "heroes", containerFactory = "entityKafkaListenerContainerFactory")
    public void processHero(List<Entity> entities) {
        GameMemory.getInstance().setEntities(entities);
        //LOGGER.info("received payload='{}'", payload);

    }
}
