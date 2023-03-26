package com.blitzar.cards.service;

import com.blitzar.cards.service.delegate.CardSolicitationRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CardSolicitationService {

    public static final String CARD_SOLICITATION_TOPIC_NAME = "card-solicitation";

    private final KafkaTemplate<String, CardSolicitationRequest> kafkaTemplate;

    public CardSolicitationService(KafkaTemplate<String, CardSolicitationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(CardSolicitationRequest cardSolicitationRequest){
        kafkaTemplate.send(CARD_SOLICITATION_TOPIC_NAME, cardSolicitationRequest);
    }
}
