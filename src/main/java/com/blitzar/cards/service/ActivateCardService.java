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
public class ActivateCardService {

    private static final Logger logger = LoggerFactory.getLogger(ActivateCardService.class);

    private final CardRepository repository;

    @Inject
    public ActivateCardService(CardRepository repository) {
        this.repository = repository;
    }

    public void activateCard(Long cardId){
        logger.info("[CardId={}] Activating card", cardId);

        repository.findById(cardId)
                .map(card -> {
                    card.setCardStatus(CardStatus.ACTIVE);
                    repository.save(card);

                    logger.info("[CardId={}] Card activated", cardId);
                    return card;
                })
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }
}