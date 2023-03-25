package com.blitzar.cards.web.dto;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;

public record CardDTO(long cardId, String cardholderName, CardStatus cardStatus, int dailyWithdrawalLimit) {

    public CardDTO(Card card) {
        this(
                card.getCardId(),
                card.getCardholderName(),
                card.getCardStatus(),
                card.getDailyWithdrawalLimit()
        );
    }
}