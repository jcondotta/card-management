package com.blitzar.cards.web.controller.stubs;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.delegate.AddCardDelegate;
import com.blitzar.cards.web.controller.AddCardRequest;

import java.util.UUID;

public class TestAddCardDelegate implements AddCardDelegate {

    private String cardholderName;

    public TestAddCardDelegate() {
        this.cardholderName = "Jefferson Condotta";
    }

    @Override
    public String getCardholderName() {
        return cardholderName;
    }

    public TestAddCardDelegate setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
        return this;
    }

    public AddCardRequest buildCardRequest(){
        return new AddCardRequest(this);
    }

    public Card buildCard(){
        Card card = new Card();
        card.setCardId(10L);
        card.setCardholderName(cardholderName);
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(DEFAULT_DAILY_PAYMENT_LIMIT);

        return card;
    }
}
