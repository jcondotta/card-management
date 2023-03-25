package com.blitzar.cards.config.kafka;

import com.blitzar.cards.service.AddCardDelegate;
import com.blitzar.cards.service.AddCardService;
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

//    @KafkaListener(topics = "card-solicitation")
    public void listener(String cardholderName){
        System.out.println("Card Solicitation for: " + cardholderName);
        AddCardDelegate delegate = new AddCardDelegate() {
            @Override
            public String getCardholderName() {
                return cardholderName;
            }
        };

        addCardService.addCard(delegate);
    }
}
