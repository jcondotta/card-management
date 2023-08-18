package com.blitzar.cards.service;

import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.web.dto.CardDTO;
import com.blitzar.cards.web.dto.CardsDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.stream.StreamSupport;

@Singleton
public class GetCardService {

    private CardRepository cardRepository;

    @Inject
    public GetCardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardDTO byId(Long cardId){
        return cardRepository.findById(cardId)
                .map(card -> new CardDTO(card))
                .orElseThrow(() -> new ResourceNotFoundException("card.notFound", cardId));
    }

    public CardsDTO findAll(){
        CardsDTO cardsDTO = new CardsDTO();
        StreamSupport.stream(cardRepository.findAll().spliterator(), false)
                .map(card -> new CardDTO(card))
                .forEachOrdered(cardsDTO.getCards()::add);

        return cardsDTO;
    }
}
