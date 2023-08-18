package com.blitzar.cards.service.request;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.validation.annotation.CardholderName;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class AddCardRequest {

    public static final CardStatus DEFAULT_CARD_STATUS = CardStatus.BLOCKED;
    public static final int DEFAULT_DAILY_WITHDRAWAL_LIMIT = 1000;
    public static final int DEFAULT_DAILY_PAYMENT_LIMIT = 2500;
    public static final int DEFAULT_YEAR_PERIOD_EXPIRATION_DATE = 5;

    @CardholderName
    private String cardholderName;

    @NotBlank(message = "card.accountHolderIBAN.notBlank")
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