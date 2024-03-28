package com.blitzar.cards.service;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

@Singleton
@Transactional
public class LockCardService {

    private static final Logger logger = LoggerFactory.getLogger(LockCardService.class);

    private final CardRepository repository;

    @Inject
    public LockCardService(CardRepository repository) {
        this.repository = repository;
    }

    public void lockCard(Long cardId){
        logger.info("[CardId={}] Locking card", cardId);

        repository.findById(cardId)
                .map(card -> {
                    card.setCardStatus(CardStatus.LOCKED);
                    repository.save(card);

                    logger.info("[CardId={}] Card locked", cardId);
                    return card;
                })
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }
}