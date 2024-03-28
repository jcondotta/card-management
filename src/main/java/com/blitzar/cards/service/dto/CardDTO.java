package com.blitzar.cards.service.dto;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {

    private Long cardId;
    private Long bankAccountId;
    private String cardholderName;
    private String cardNumber;
    private CardStatus cardStatus;
    private int dailyWithdrawalLimit;
    private int dailyPaymentLimit;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate expirationDate;

    public CardDTO() {}

    public CardDTO(Long cardId,
                   Long bankAccountId,
                   String cardholderName,
                   String cardNumber,
                   CardStatus cardStatus,
                   int dailyWithdrawalLimit,
                   int dailyPaymentLimit,
                   LocalDate expirationDate) {
        this.cardId = cardId;
        this.bankAccountId = bankAccountId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.cardStatus = cardStatus;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.dailyPaymentLimit = dailyPaymentLimit;
        this.expirationDate = expirationDate;
    }

    public CardDTO(Card card) {
        this(card.getCardId(),
                card.getBankAccountId(),
                card.getCardholderName(),
                card.getCardNumber(),
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

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public String getCardNumber() {
        return cardNumber;
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