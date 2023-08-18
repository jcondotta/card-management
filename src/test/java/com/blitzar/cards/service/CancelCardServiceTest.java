package com.blitzar.cards.service;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.exception.ResourceNotFoundException;
import com.blitzar.cards.repository.CardRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelCardServiceTest {

    private CancelCardService cancelCardService;

    @Mock
    private CardRepository cardRepositoryMock;

    @BeforeEach
    public void beforeEach(){
        cancelCardService = new CancelCardService(cardRepositoryMock);
    }

    @Test
    public void givenExistentCardId_whenCancelCard_thenUpdateCardStatus(){
        Card cardMock = mock(Card.class);

        when(cardRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cardMock));

        cancelCardService.cancelCard(anyLong());
        verify(cardRepositoryMock).save(any());
    }

    @Test
    public void givenNonExistentCardId_whenCancelCard_thenReturnNotFound(){
        long nonExistentCardId = NumberUtils.INTEGER_MINUS_ONE.longValue();
        when(cardRepositoryMock.findById(nonExistentCardId)).thenReturn(Optional.empty());

        var exception = assertThrowsExactly(ResourceNotFoundException.class, () -> cancelCardService.cancelCard(nonExistentCardId));

        assertAll(
                () -> assertThat(exception.getMessage()).isEqualTo("card.notFound"),
                () -> assertThat(exception.getRejectedIdentifier()).isEqualTo(nonExistentCardId)
        );
    }
}