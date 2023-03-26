package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.CardSolicitationService;
import com.blitzar.cards.service.delegate.CardSolicitationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CardSolicitationController {

    private CardSolicitationService cardSolicitationService;

    @Autowired
    public CardSolicitationController(CardSolicitationService cardSolicitationService) {
        this.cardSolicitationService = cardSolicitationService;
    }

    @PostMapping("/solicitation")
    public void publish(@RequestBody CardSolicitationRequest cardSolicitationRequest){
        cardSolicitationService.publish(cardSolicitationRequest);
    }
}
