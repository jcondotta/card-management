package com.blitzar.cards.service.delegate;

import com.blitzar.cards.domain.CardStatus;

public interface AddCardDelegate {

    CardStatus DEFAULT_CARD_STATUS = CardStatus.BLOCKED;
    int DEFAULT_DAILY_WITHDRAWAL_LIMIT = 1000;
    int DEFAULT_DAILY_PAYMENT_LIMIT = 2500;
    int DEFAULT_YEAR_PERIOD_EXPIRATION_DATE = 5;

    String getCardholderName();
}
