package com.blitzar.cards.events;

import com.blitzar.cards.service.delegate.AddCardDelegate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardApplicationEvent(
        @NotBlank(message = "card.cardholderName.notBlank")
        @Size(max = 21, message = "card.cardholderName.length.limit")
        String cardholderName) implements AddCardDelegate {
}
