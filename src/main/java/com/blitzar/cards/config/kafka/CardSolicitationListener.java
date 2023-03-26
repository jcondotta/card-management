package com.blitzar.cards.config.kafka;

import com.blitzar.cards.service.delegate.AddCardDelegate;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.delegate.CardSolicitationRequest;
import com.blitzar.cards.service.CardSolicitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CardSolicitationListener {

    private final AddCardService addCardService;

    @Autowired
    public CardSolicitationListener(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @KafkaListener(topics = CardSolicitationService.CARD_SOLICITATION_TOPIC_NAME, groupId = "123")
    public void listener(CardSolicitationRequest cardSolicitationRequest){
        System.out.println("Card Solicitation for: " + cardSolicitationRequest);
        AddCardDelegate delegate = () -> cardSolicitationRequest.cardholderName();

        addCardService.addCard(delegate);
    }
}
