package com.blitzar.cards.service;

import com.blitzar.cards.config.kafka.KafkaApplicationProperties;
import com.blitzar.cards.events.CardApplicationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CardApplicationEventHandler {

    private final KafkaTemplate<String, CardApplicationEvent> kafkaTemplate;
    private final KafkaApplicationProperties kafkaApplicationProperties;

    public CardApplicationEventHandler(KafkaTemplate<String, CardApplicationEvent> kafkaTemplate, KafkaApplicationProperties kafkaApplicationProperties) {
        this.kafkaApplicationProperties = kafkaApplicationProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handle(CardApplicationEvent cardApplicationEvent){
        kafkaTemplate.send(kafkaApplicationProperties.cardApplicationTopic(), cardApplicationEvent);
    }
}
