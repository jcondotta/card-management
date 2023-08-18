package com.blitzar.cards.web.dto;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;

import java.time.LocalDate;

public class CardDTO {

    private long cardId;
    private String cardholderName;
    private String cardNumber;
    private String accountHolderIban;
    private CardStatus cardStatus;
    private int dailyWithdrawalLimit;
    private int dailyPaymentLimit;
    private LocalDate expirationDate;

    public CardDTO(long cardId,
                   String cardholderName,
                   String cardNumber,
                   String accountHolderIban,
                   CardStatus cardStatus,
                   int dailyWithdrawalLimit,
                   int dailyPaymentLimit,
                   LocalDate expirationDate) {
        this.cardId = cardId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.accountHolderIban = accountHolderIban;
        this.cardStatus = cardStatus;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.dailyPaymentLimit = dailyPaymentLimit;
        this.expirationDate = expirationDate;
    }

    public CardDTO(Card card) {
        this(card.getCardId(),
                card.getCardholderName(),
                card.getCardNumber(),
                card.getAccountHolderIban(),
                card.getCardStatus(),
                card.getDailyWithdrawalLimit(),
                card.getDailyPaymentLimit(),
                card.getExpirationDate());
    }

    public long getCardId() {
        return cardId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountHolderIban() {
        return accountHolderIban;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public int getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public int getDailyPaymentLimit() {
        return dailyPaymentLimit;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}