package com.blitzar.cards.service;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivateCardService {

    private final CardRepository repository;

    @Autowired
    public ActivateCardService(CardRepository repository) {
        this.repository = repository;
    }

    public void activateCard(Long cardId){
        repository.findById(cardId)
                .map(card -> {
                    card.setCardStatus(CardStatus.ACTIVE);
                    repository.save(card);
                    return card;
                })
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }
}