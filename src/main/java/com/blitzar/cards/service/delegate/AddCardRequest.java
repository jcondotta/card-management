package com.blitzar.cards.service.delegate;

import com.blitzar.cards.validation.annotation.CardholderName;

public record AddCardRequest(
        @CardholderName String cardholderName) implements AddCardDelegate {
}