package com.blitzar.cards.service;

import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.dto.CardDTO;
import com.blitzar.cards.service.dto.CardsDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.StreamSupport;

@Singleton
public class GetCardService {

    private static final Logger logger = LoggerFactory.getLogger(GetCardService.class);

    private CardRepository cardRepository;

    @Inject
    public GetCardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardDTO findById(Long cardId){
        logger.info("[CardId={}] Fetching card", cardId);

        return cardRepository.findById(cardId)
                .map(card -> new CardDTO(card))
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }

    public CardsDTO findByBankAccountId(Long bankAccountId){
        logger.info("[BankAccountId={}] Fetching cards", bankAccountId);

        CardsDTO cardsDTO = new CardsDTO();
        StreamSupport.stream(cardRepository.findByBankAccountId(bankAccountId).spliterator(), false)
                .map(card -> new CardDTO(card))
                .forEachOrdered(cardsDTO.getCards()::add);

        logger.info("[BankAccountId={}] {} card(s) found", bankAccountId, cardsDTO.getCards().size());
        return cardsDTO;
    }
}
