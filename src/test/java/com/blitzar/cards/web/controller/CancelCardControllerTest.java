package com.blitzar.cards.web.controller;

import com.blitzar.cards.LocalStackMySQLTestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.request.AddCardRequest;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Locale;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
class CancelCardControllerTest implements LocalStackMySQLTestContainer {

    @Inject
    @Named("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    @Inject
    private AddCardService addCardService;

    @Inject
    private CardRepository cardRepository;

    private RequestSpecification requestSpecification;

    private String cardholderName = "Jefferson Condotta";
    private String accountHolderIban = UUID.randomUUID().toString();

    @BeforeAll
    public static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification
                .contentType(ContentType.JSON)
                .basePath(CardAPIConstants.BASE_PATH_API_V1_MAPPING);
    }

    @Test
    public void givenExistentCardId_whenCancelCard_thenReturnOk(){
        var addCardRequest = new AddCardRequest(cardholderName, accountHolderIban);
        Card card = addCardService.addCard(addCardRequest);

        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/cancellation", card.getCardId())
        .then()
            .statusCode(HttpStatus.NO_CONTENT.getCode());

        cardRepository.findById(card.getCardId())
                .ifPresentOrElse(patchedCard -> assertThat(patchedCard.getCardStatus()).isEqualTo(CardStatus.CANCELLED),
                        () -> fail(exceptionMessageSource.getMessage("card.notFound", Locale.getDefault()).orElseThrow()));
    }

    @Test
    public void givenNonExistentCardId_whenCancelCard_thenReturnNotFound(){
        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/cancellation", NumberUtils.INTEGER_MINUS_ONE)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.getCode())
            .rootPath("_embedded")
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo(exceptionMessageSource.getMessage("card.notFound", Locale.getDefault()).orElseThrow()));
    }
}