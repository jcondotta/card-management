package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.request.AddCardRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCardsServiceTest {

    private GetCardService getCardService;

    @Mock
    private CardRepository cardRepositoryMock;

    private Long cardId = 22732L;
    private Long bankAccountId = 998372L;
    private String cardholderName = "Jefferson Condotta";

    @BeforeEach
    public void beforeEach(){
        getCardService = new GetCardService(cardRepositoryMock);
    }

    @Test
    public void givenExistentCardId_whenGetCard_thenReturnCard(){
        var card = new Card();
        card.setCardId(cardId);
        card.setBankAccountId(bankAccountId);
        card.setCardholderName(cardholderName);
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardRequest.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardRequest.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardRequest.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.of(2001, Month.APRIL, 20));

        when(cardRepositoryMock.findByBankAccountId(anyLong()))
                .thenReturn(Collections.singletonList(card));

        var cardsDTO = getCardService.findByBankAccountId(bankAccountId);
        assertThat(cardsDTO.cards().size()).isEqualTo(1);

        cardsDTO.cards().stream()
                .findFirst()
                .ifPresent(cardDTO -> assertAll(
                        () -> assertThat(cardDTO.getCardId()).isEqualTo(card.getCardId()),
                        () -> assertThat(cardDTO.getBankAccountId()).isEqualTo(card.getBankAccountId()),
                        () -> assertThat(cardDTO.getCardholderName()).isEqualTo(card.getCardholderName()),
                        () -> assertThat(cardDTO.getCardNumber()).isEqualTo(card.getCardNumber()),
                        () -> assertThat(cardDTO.getCardStatus()).isEqualTo(card.getCardStatus()),
                        () -> assertThat(cardDTO.getDailyWithdrawalLimit()).isEqualTo(card.getDailyWithdrawalLimit()),
                        () -> assertThat(cardDTO.getDailyPaymentLimit()).isEqualTo(card.getDailyPaymentLimit()),
                        () -> assertThat(cardDTO.getExpirationDate()).isEqualTo(card.getExpirationDate())
                ));
    }

    @Test
    public void givenNonExistentCardId_whenGetCard_thenThrowException(){
        long nonExistentBankAccountId = NumberUtils.INTEGER_MINUS_ONE.longValue();
        when(cardRepositoryMock.findByBankAccountId(anyLong()))
                .thenReturn(Collections.EMPTY_LIST);

        var cardsDTO = getCardService.findByBankAccountId(nonExistentBankAccountId);
        assertThat(cardsDTO.cards().size()).isEqualTo(0);
    }
}