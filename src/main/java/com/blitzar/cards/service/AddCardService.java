package com.blitzar.cards.service;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolationsException;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.delegate.AddCardDelegate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AddCardService {

    private final CardRepository repository;
    private final Clock currentInstant;

    @Autowired
    public AddCardService(CardRepository repository, Clock currentInstant) {
        this.repository = repository;
        this.currentInstant = currentInstant;
    }

    public Card addCard(AddCardDelegate delegate){
        ValidatorBuilder.<AddCardDelegate>of()
                .constraint(AddCardDelegate::getCardholderName, "cardholderName",
                        c -> c.notNull().message("card.cardholderName.notBlank")
                                .predicate(s -> StringUtils.isNotBlank(s), "card.cardholderName.notBlank", "card.cardholderName.notBlank")
                                .lessThanOrEqual(21).message("card.cardholderName.length.limit"))
                .build()
                .applicative()
                .validate(delegate)
                .orElseThrow(violations -> new ConstraintViolationsException(violations));

        var card = new Card();
        card.setCardholderName(delegate.getCardholderName());
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardDelegate.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardDelegate.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardDelegate.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.now(currentInstant)
                .plus(AddCardDelegate.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS));

        return repository.save(card);
    }
}