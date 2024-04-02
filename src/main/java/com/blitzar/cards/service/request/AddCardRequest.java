package com.blitzar.cards.service.request;

import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.validation.annotation.BankAccountId;
import com.blitzar.cards.validation.annotation.CardholderName;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class AddCardRequest {

    public static final CardStatus DEFAULT_CARD_STATUS = CardStatus.LOCKED;
    public static final int DEFAULT_DAILY_WITHDRAWAL_LIMIT = 1000;
    public static final int DEFAULT_DAILY_PAYMENT_LIMIT = 2500;
    public static final int DEFAULT_YEAR_PERIOD_EXPIRATION_DATE = 5;

    @BankAccountId
    private Long bankAccountId;

    @CardholderName
    private String cardholderName;

    public AddCardRequest(Long bankAccountId, String cardholderName) {
        this.bankAccountId = bankAccountId;
        this.cardholderName = cardholderName;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}