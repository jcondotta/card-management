package com.blitzar.cards.repository;

import com.blitzar.cards.domain.Card;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Collection<Card> findByBankAccountId(Long bankAccountId);

}
