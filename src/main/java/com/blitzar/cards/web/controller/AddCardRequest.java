package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.AddCardDelegate;

public class AddCardRequest implements AddCardDelegate {

    private String cardholderName;
    private Integer dailyWithdrawalLimit;

    public AddCardRequest() {}

    public AddCardRequest(AddCardDelegate addCardDelegate) {
        this.cardholderName = addCardDelegate.getCardholderName();
        this.dailyWithdrawalLimit = addCardDelegate.getDailyWithdrawalLimit();
    }

    @Override
    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    @Override
    public Integer getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(Integer dailyWithdrawalLimit) {
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }
}