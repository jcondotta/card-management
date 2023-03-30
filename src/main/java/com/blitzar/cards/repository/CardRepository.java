package com.blitzar.cards.repository;

import com.blitzar.cards.domain.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    Collection<Card> findByCardholderName(String cardholderName);
}
