package com.blitzar.cards.service.request;

import com.blitzar.cards.validation.annotation.CardholderName;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class AddCardRequest implements AddCardDelegate{

    @CardholderName
    private String cardholderName;

    @NotBlank
    private String iban;

    public AddCardRequest(String cardholderName, String iban) {
        this.cardholderName = cardholderName;
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}