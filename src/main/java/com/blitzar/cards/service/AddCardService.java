package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.delegate.AddCardDelegate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class AddCardService {

    private final CardRepository repository;
    private final Clock currentInstant;
    private final Validator validator;

    @Autowired
    public AddCardService(CardRepository repository, Clock currentInstant, Validator validator) {
        this.repository = repository;
        this.currentInstant = currentInstant;
        this.validator = validator;
    }

    public Card addCard(AddCardDelegate delegate){
        var constraintViolations = validator.validate(delegate);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        var card = new Card();
        card.setCardholderName(delegate.cardholderName());
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardDelegate.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardDelegate.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardDelegate.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.now(currentInstant)
                .plus(AddCardDelegate.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS));

        return repository.save(card);
    }
}