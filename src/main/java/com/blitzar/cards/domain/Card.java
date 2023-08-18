package com.blitzar.cards.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "cardholder_name", nullable = false, length = 21)
    private String cardholderName;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "account_holder_iban", nullable = false)
    private String accountHolderIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "daily_withdrawal_limit", nullable = false)
    private int dailyWithdrawalLimit;

    @Column(name = "daily_payment_limit", nullable = false)
    private int dailyPaymentLimit;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getAccountHolderIban() {
        return accountHolderIban;
    }

    public void setAccountHolderIban(String accountHolderIban) {
        this.accountHolderIban = accountHolderIban;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(int dailyWithdrawalLimit) {
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    public int getDailyPaymentLimit() {
        return dailyPaymentLimit;
    }

    public void setDailyPaymentLimit(int dailyPaymentLimit) {
        this.dailyPaymentLimit = dailyPaymentLimit;
    }
}
