package com.blitzar.cards.service;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;

@Singleton
@Transactional
public class CancelCardService {

    private final CardRepository repository;

    @Inject
    public CancelCardService(CardRepository repository) {
        this.repository = repository;
    }

    public void cancelCard(Long cardId){
        repository.findById(cardId)
                .map(card -> {
                    card.setCardStatus(CardStatus.CANCELLED);
                    repository.save(card);
                    return card;
                })
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }
}