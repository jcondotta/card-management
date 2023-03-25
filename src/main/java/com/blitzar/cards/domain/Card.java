package com.blitzar.cards.domain;

import jakarta.persistence.*;

@Entity
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "cardholder_name", nullable = false, length = 21)
    private String cardholderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

//    @Column(name = "card_number")
//    private String cardNumber;
//
//    @Column(name = "expiration_date")
//    private LocalDate expirationDate;
//
//    @Column(name = "security_code")
//    private char[] securityCode;

//    @Column(name = "daily_payment_limit")
//    private int dailyPaymentLimit;
//
    @Column(name = "daily_withdrawal_limit", nullable = false)
    private int dailyWithdrawalLimit;

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

    public int getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(int dailyWithdrawalLimit) {
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    //    public String getCardNumber() {
//        return cardNumber;
//    }
//
//    public void setCardNumber(String cardNumber) {
//        this.cardNumber = cardNumber;
//    }
//
//    public LocalDate getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(LocalDate expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//
//    public char[] getSecurityCode() {
//        return securityCode;
//    }
//
//    public void setSecurityCode(char[] securityCode) {
//        this.securityCode = securityCode;
//    }
}
