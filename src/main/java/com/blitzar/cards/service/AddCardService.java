package com.blitzar.cards.service;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolationsException;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.repository.CardRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.stream.StreamSupport;

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

                .constraint(AddCardDelegate::getDailyWithdrawalLimit, "dailyWithdrawalLimit",
                        c -> c.notNull().message("card.dailyWithdrawalLimit.notNull")
                                .greaterThanOrEqual(0).message("card.dailyWithdrawalLimit.negative"))
                .build()
                .applicative()
                .validate(delegate)
                .orElseThrow(violations -> new ConstraintViolationsException(violations));

        var card = new Card();
        card.setCardholderName(delegate.getCardholderName());
        card.setCardStatus(CardStatus.BLOCKED);
        card.setDailyWithdrawalLimit(delegate.getDailyWithdrawalLimit());
//        card.setCardNumber(UUID.randomUUID().toString());
//        card.setExpirationDate(LocalDate.now().plus(4, ChronoUnit.YEARS));
//        card.setSecurityCode(RandomStringUtils.randomNumeric(3).toCharArray());

        return repository.save(card);
    }
}