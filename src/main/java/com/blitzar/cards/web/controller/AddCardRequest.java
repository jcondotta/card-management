package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.delegate.AddCardDelegate;

public class AddCardRequest implements AddCardDelegate {

    private String cardholderName;

    public AddCardRequest() {}

    public AddCardRequest(AddCardDelegate addCardDelegate) {
        this.cardholderName = addCardDelegate.getCardholderName();
    }

    @Override
    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}