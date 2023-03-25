package com.blitzar.cards.web.dto;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;

public record CardDTO(long cardId, String cardholderName, String cardNumber, CardStatus cardStatus, int dailyWithdrawalLimit, int dailyPaymentLimit) {

    public CardDTO(Card card) {
        this(
                card.getCardId(),
                card.getCardholderName(),
                card.getCardNumber(),
                card.getCardStatus(),
                card.getDailyWithdrawalLimit(),
                card.getDailyPaymentLimit()
        );
    }
}