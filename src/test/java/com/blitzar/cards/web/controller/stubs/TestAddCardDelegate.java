package com.blitzar.cards.web.controller.stubs;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.service.AddCardDelegate;
import com.blitzar.cards.web.controller.AddCardRequest;

public class TestAddCardDelegate implements AddCardDelegate {

    private String cardholderName;
    private Integer dailyWithdrawalLimit;

    public TestAddCardDelegate() {
        this.cardholderName = "Jefferson Condotta";
        this.dailyWithdrawalLimit = 1000;
    }

    @Override
    public String getCardholderName() {
        return cardholderName;
    }

    public TestAddCardDelegate setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
        return this;
    }

    @Override
    public Integer getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public TestAddCardDelegate setDailyWithdrawalLimit(Integer dailyWithdrawalLimit) {
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        return this;
    }

    public AddCardRequest buildCardRequest(){
        return new AddCardRequest(this);
    }

    public Card buildCard(){
        Card card = new Card();
        card.setCardId(10L);
        card.setCardholderName(cardholderName);
        card.setCardStatus(CardStatus.BLOCKED);
        card.setDailyWithdrawalLimit(dailyWithdrawalLimit);

        return card;
    }
}
