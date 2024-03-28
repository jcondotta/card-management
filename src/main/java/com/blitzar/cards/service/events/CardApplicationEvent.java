package com.blitzar.cards.service.events;

import com.blitzar.cards.service.request.AddCardRequest;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class CardApplicationEvent extends AddCardRequest {

    public CardApplicationEvent(Long bankAccountId, String cardholderName) {
        super(bankAccountId, cardholderName);
    }
}

