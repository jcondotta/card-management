package com.blitzar.cards.web.controller;

import com.blitzar.cards.LocalStackMySQLTestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.request.AddCardRequest;
import com.blitzar.cards.service.dto.CardDTO;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
public class GetCardControllerIT implements LocalStackMySQLTestContainer {

    @Inject
    @Named("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    @Inject
    private AddCardService addCardService;

    private RequestSpecification requestSpecification;

    private Long bankAccountId = 998372L;
    private String cardholderName = "Jefferson Condotta";

    @BeforeAll
    public static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification
                .contentType(ContentType.JSON)
                .basePath(CardAPIConstants.CARD_V1_MAPPING);
    }

    @Test
    public void givenExistentCardId_whenGetCard_thenReturnOk(){
        var addCardRequest = new AddCardRequest(bankAccountId, cardholderName);

        Card card = addCardService.addCard(addCardRequest);
        assertThat(card.getCardStatus()).isEqualTo(CardStatus.LOCKED);

        CardDTO cardDTO = given()
            .spec(requestSpecification)
        .when()
            .get("/", card.getCardId())
        .then()
            .statusCode(HttpStatus.OK.getCode())
                .extract()
                    .as(CardDTO.class);

        assertAll(
                () -> assertThat(cardDTO.getCardId()).isEqualTo(card.getCardId()),
                () -> assertThat(cardDTO.getBankAccountId()).isEqualTo(card.getBankAccountId()),
                () -> assertThat(cardDTO.getCardholderName()).isEqualTo(card.getCardholderName()),
                () -> assertThat(cardDTO.getCardNumber()).isEqualTo(card.getCardNumber()),
                () -> assertThat(cardDTO.getCardStatus()).isEqualTo(card.getCardStatus()),
                () -> assertThat(cardDTO.getDailyWithdrawalLimit()).isEqualTo(card.getDailyWithdrawalLimit()),
                () -> assertThat(cardDTO.getDailyPaymentLimit()).isEqualTo(card.getDailyPaymentLimit()),
                () -> assertThat(cardDTO.getExpirationDate()).isEqualTo(card.getExpirationDate())
        );
    }

    @Test
    public void givenNonExistentCardId_whenGetCard_thenReturnNotFound(){
        var nonExistentCardId = NumberUtils.INTEGER_MINUS_ONE;

        given()
            .spec(requestSpecification)
        .when()
            .get("/", nonExistentCardId)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.getCode())
            .rootPath("_embedded")
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo(exceptionMessageSource.getMessage("card.notFound", Locale.getDefault(), nonExistentCardId).orElseThrow()));
    }
}
