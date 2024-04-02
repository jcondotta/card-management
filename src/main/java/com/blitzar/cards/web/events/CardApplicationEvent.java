package com.blitzar.cards.web.events;

import com.blitzar.cards.service.request.AddCardRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CardApplicationEvent extends AddCardRequest {

    public CardApplicationEvent(Long bankAccountId, String cardholderName) {
        super(bankAccountId, cardholderName);
    }
}

