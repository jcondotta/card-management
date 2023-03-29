package com.blitzar.cards.web.controller.request;

import com.blitzar.cards.service.delegate.AddCardDelegate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddCardRequest implements AddCardDelegate {

    @NotBlank
    @Size(max = 21)
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