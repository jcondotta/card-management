package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.delegate.AddCardRequest;
import com.blitzar.cards.web.dto.CardDTO;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCardServiceTest {

    private GetCardService getCardService;

    @Mock
    private CardRepository cardRepositoryMock;

    @BeforeEach
    public void beforeEach(){
        getCardService = new GetCardService(cardRepositoryMock);
    }

    @Test
    public void givenExistentCardId_whenGetCard_thenReturnCard(){
        var card = new Card();
        card.setCardId(10L);
        card.setCardholderName("Jefferson Test User");
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardStatus(AddCardRequest.DEFAULT_CARD_STATUS);
        card.setDailyWithdrawalLimit(AddCardRequest.DEFAULT_DAILY_WITHDRAWAL_LIMIT);
        card.setDailyPaymentLimit(AddCardRequest.DEFAULT_DAILY_PAYMENT_LIMIT);
        card.setExpirationDate(LocalDate.of(2001, Month.APRIL, 20));

        when(cardRepositoryMock.findById(anyLong())).thenReturn(Optional.of(card));

        CardDTO cardDTO = getCardService.byId(anyLong());

        assertAll(
                () -> assertThat(cardDTO.cardId()).isEqualTo(card.getCardId()),
                () -> assertThat(cardDTO.cardholderName()).isEqualTo(card.getCardholderName()),
                () -> assertThat(cardDTO.cardNumber()).isEqualTo(card.getCardNumber()),
                () -> assertThat(cardDTO.cardStatus()).isEqualTo(card.getCardStatus()),
                () -> assertThat(cardDTO.dailyWithdrawalLimit()).isEqualTo(card.getDailyWithdrawalLimit()),
                () -> assertThat(cardDTO.dailyPaymentLimit()).isEqualTo(card.getDailyPaymentLimit()),
                () -> assertThat(cardDTO.expirationDate()).isEqualTo(card.getExpirationDate())
        );
    }

    @Test
    public void givenNonExistentCardId_whenGetCard_thenThrowException(){
        long nonExistentCardId = NumberUtils.INTEGER_MINUS_ONE.longValue();
        when(cardRepositoryMock.findById(nonExistentCardId)).thenReturn(Optional.empty());

        var exception = assertThrowsExactly(ResourceNotFoundException.class, () -> getCardService.byId(nonExistentCardId));

        assertAll(
            () -> assertThat(exception.getMessage()).isEqualTo("card.notFound"),
            () -> assertThat(exception.getRejectedIdentifier()).isEqualTo(nonExistentCardId)
        );
    }
}