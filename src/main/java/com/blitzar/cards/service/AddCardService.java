package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.request.AddCardRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Singleton
public class AddCardService {

    private final CardRepository repository;
    private final Clock currentInstant;
    private final Validator validator;

    @Inject
    public AddCardService(CardRepository repository, Clock currentInstant, Validator validator) {
        this.repository = repository;
        this.currentInstant = currentInstant;
        this.validator = validator;
    }

    public Card addCard(AddCardRequest request){
        var constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        var card = new Card();
        card.setCardholderName(request.getCardholderName());
        card.setAccountHolderIban(request.getIban());
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardRequest.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardRequest.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardRequest.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.now(currentInstant)
                .plus(AddCardRequest.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS));

        return repository.save(card);
    }
}