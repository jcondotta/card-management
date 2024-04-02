package com.blitzar.cards.service;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
public class CancelCardService {

    private static final Logger logger = LoggerFactory.getLogger(CancelCardService.class);

    private final CardRepository repository;

    @Inject
    public CancelCardService(CardRepository repository) {
        this.repository = repository;
    }

    public void cancelCard(Long cardId){
        logger.info("[CardId={}] Cancelling card", cardId);

        repository.findById(cardId)
                .map(card -> {
                    card.setCardStatus(CardStatus.CANCELLED);
                    repository.save(card);

                    logger.info("[CardId={}] Card cancelled", cardId);
                    return card;
                })
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }
}