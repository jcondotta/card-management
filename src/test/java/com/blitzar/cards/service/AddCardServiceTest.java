package com.blitzar.cards.service;

import am.ik.yavi.core.ConstraintViolationsException;
import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddCardServiceTest {

    private String currentTestName;
    private AddCardService addCardService;

    @Mock
    private CardRepository cardRepositoryMock;

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        currentTestName = testInfo.getTestMethod().orElseThrow().getName();
        addCardService = new AddCardService(cardRepositoryMock, Clock.system(ZoneOffset.UTC));
    }

    @Test
    public void givenValidRequest_whenAddCard_thenSaveCard(){
        var delegate = new TestAddCardDelegate();

        addCardService.addCard(delegate);
        verify(cardRepositoryMock).save(any());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenAddCard_thenThrowException(String invalidCardholderName){
        var delegate = new TestAddCardDelegate()
                .setCardholderName(invalidCardholderName);

        var exception = assertThrowsExactly(ConstraintViolationsException.class, () -> addCardService.addCard(delegate));
        assertThat(exception.violations()).hasSize(1);

        exception.violations().stream()
                .findFirst()
                .ifPresent(violation -> assertAll(
                        () -> assertThat(violation.defaultMessageFormat()).isEqualTo("card.cardholderName.notBlank"),
                        () -> assertThat(violation.name()).isEqualTo("cardholderName")
                ));

        verify(cardRepositoryMock, never()).save(any());
    }

    @Test
    public void givenCardholderNameLongerThan21Characters_whenAddCard_thenThrowException(){
        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
        var delegate = new TestAddCardDelegate()
                .setCardholderName(invalidCardholderName);

        var exception = assertThrowsExactly(ConstraintViolationsException.class, () -> addCardService.addCard(delegate));
        assertThat(exception.violations()).hasSize(1);

        exception.violations().stream()
                .findFirst()
                .ifPresent(violation -> assertAll(
                        () -> assertThat(violation.defaultMessageFormat()).isEqualTo("card.cardholderName.length.limit"),
                        () -> assertThat(violation.name()).isEqualTo("cardholderName")
                ));

        verify(cardRepositoryMock, never()).save(any());
    }
}
