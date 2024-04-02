package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.request.AddCardRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Singleton
@Transactional
public class AddCardService {

    private static final Logger logger = LoggerFactory.getLogger(AddCardService.class);

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
        logger.info("[BankAccountId={}, CardholderName={}] Attempting to add a new card", request.getBankAccountId(), request.getCardholderName());

        var constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        var card = new Card();
        card.setBankAccountId(request.getBankAccountId());
        card.setCardholderName(request.getCardholderName());
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardRequest.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardRequest.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardRequest.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.now(currentInstant)
                .plus(AddCardRequest.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS));

        repository.save(card);
        logger.info("[BankAccountId={}, CardholderName={}] Card saved to DB", request.getBankAccountId(), request.getCardholderName());

        return card;
    }
}