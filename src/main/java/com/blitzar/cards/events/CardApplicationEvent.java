package com.blitzar.cards.events;

import com.blitzar.cards.service.delegate.AddCardDelegate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardApplicationEvent(
        @NotBlank @Size(max = 21) String cardholderName) implements AddCardDelegate {

    @Override
    public String getCardholderName() {
        return cardholderName;
    }
}
