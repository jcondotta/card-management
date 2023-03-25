package com.blitzar.cards.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CardsDTO implements Serializable {

    private final Collection<CardDTO> cards;

    public CardsDTO() {
        this.cards = new ArrayList<>();
    }

    public void addCard(CardDTO cardDTO){
        getCards().add(cardDTO);
    }

    public Collection<CardDTO> getCards() {
        return cards;
    }
}