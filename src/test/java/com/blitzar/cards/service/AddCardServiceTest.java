package com.blitzar.cards.service;

import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.events.CardApplicationEvent;
import com.blitzar.cards.service.request.AddCardRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.UUID;

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

    private String cardholderName = "Jefferson Condotta";
    private String accountHolderIban = UUID.randomUUID().toString();

    @BeforeEach
    public void beforeEach(){
        addCardService = new AddCardService(cardRepositoryMock, Clock.system(ZoneOffset.UTC), Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Test
    public void givenValidRequest_whenAddCard_thenSaveCard(){
        var addCardRequest = new AddCardRequest(cardholderName, accountHolderIban);

        addCardService.addCard(addCardRequest);
        verify(cardRepositoryMock).save(any());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenAddCard_thenThrowException(String invalidCardholderName){
        var addCardRequest = new AddCardRequest(invalidCardholderName, accountHolderIban);

        var exception = assertThrowsExactly(ConstraintViolationException.class, () -> addCardService.addCard(addCardRequest));
        assertThat(exception.getConstraintViolations()).hasSize(1);

        var violation = exception.getConstraintViolations().stream()
                .findFirst()
                .orElseThrow();

        assertAll(
                () -> assertThat(violation.getMessage()).isEqualTo("card.cardholderName.notBlank"),
                () -> assertThat(violation.getPropertyPath().toString()).isEqualTo("cardholderName")
        );

        verify(cardRepositoryMock, never()).save(any());
    }

    @Test
    public void givenCardholderNameLongerThan21Characters_whenAddCard_thenThrowException(){
        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
        var addCardRequest = new AddCardRequest(invalidCardholderName, accountHolderIban);

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

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidIBAN_whenRegisterCardApplication_thenThrowException(String invalidIBAN){
        var cardApplicationEvent = new CardApplicationEvent(cardholderName, invalidIBAN);

        var exception = assertThrowsExactly(ConstraintViolationException.class, () -> addCardService.addCard(cardApplicationEvent));
        assertThat(exception.getConstraintViolations()).hasSize(1);

        exception.getConstraintViolations().stream()
                .findFirst()
                .ifPresent(violation -> assertAll(
                        () -> assertThat(violation.getMessage()).isEqualTo("card.iban.notBlank"),
                        () -> assertThat(violation.getPropertyPath().toString()).isEqualTo("iban")
                ));

        verify(cardRepositoryMock, never()).save(any());
    }
}
