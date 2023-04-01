package com.blitzar.cards.service;

import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.events.CardApplicationEvent;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.delegate.AddCardRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddCardServiceTest {

    private AddCardService addCardService;

    @Mock
    private CardRepository cardRepositoryMock;

    @BeforeEach
    public void beforeEach(){
        addCardService = new AddCardService(cardRepositoryMock, Clock.system(ZoneOffset.UTC), Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Test
    public void givenValidRequest_whenAddCard_thenSaveCard(){
        var addCardRequest = new AddCardRequest("Jefferson Condotta");

        addCardService.addCard(addCardRequest);
        verify(cardRepositoryMock).save(any());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenAddCard_thenThrowException(String invalidCardholderName){
        var addCardRequest = new AddCardRequest(invalidCardholderName);

        var exception = assertThrowsExactly(ConstraintViolationException.class, () -> addCardService.addCard(addCardRequest));
        assertThat(exception.getConstraintViolations()).hasSize(1);

        exception.getConstraintViolations().stream()
                .findFirst()
                .ifPresent(violation -> assertAll(
                        () -> assertThat(violation.getMessage()).isEqualTo("card.cardholderName.notBlank"),
                        () -> assertThat(violation.getPropertyPath().toString()).isEqualTo("cardholderName")
                ));

        verify(cardRepositoryMock, never()).save(any());
    }

    @Test
    public void givenCardholderNameLongerThan21Characters_whenAddCard_thenThrowException(){
        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
        var addCardRequest = new AddCardRequest(invalidCardholderName);

        var exception = assertThrowsExactly(ConstraintViolationException.class, () -> addCardService.addCard(addCardRequest));
        assertThat(exception.getConstraintViolations()).hasSize(1);

        exception.getConstraintViolations().stream()
                .findFirst()
                .ifPresent(violation -> assertAll(
                        () -> assertThat(violation.getMessage()).isEqualTo("card.cardholderName.length.limit"),
                        () -> assertThat(violation.getPropertyPath().toString()).isEqualTo("cardholderName")
                ));

        verify(cardRepositoryMock, never()).save(any());
    }
}
