package com.blitzar.cards.web.controller;

import com.blitzar.cards.MySQLTestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.events.CardApplicationEvent;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CancelCardControllerTest implements MySQLTestContainer {

    @Autowired
    @Qualifier("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    @Autowired
    private AddCardService addCardService;

    @Autowired
    private CardRepository cardRepository;

    private RequestSpecification requestSpecification;

    @BeforeAll
    public static void beforeAll(@LocalServerPort int serverHttpPort){
        port = serverHttpPort;
        basePath = "/api/v1/cards";
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach() {
        this.requestSpecification = new RequestSpecBuilder()
                .build()
                .contentType(ContentType.JSON);
    }

    @Test
    public void givenExistentCardId_whenCancelCard_thenReturnOk(){
        var cardApplicationEvent = new CardApplicationEvent("Mateo Condotta");
        Card card = addCardService.addCard(cardApplicationEvent);

        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/cancellation", card.getCardId())
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        cardRepository.findById(card.getCardId())
                .ifPresentOrElse(patchedCard -> assertThat(patchedCard.getCardStatus()).isEqualTo(CardStatus.CANCELLED),
                        () -> fail("No card has been found with id: %s".formatted(card.getCardId())));
    }

    @Test
    public void givenNonExistentCardId_whenCancelCard_thenReturnNotFound(){
        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/cancellation", NumberUtils.INTEGER_MINUS_ONE)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                .body("instance", equalTo(RestAssured.basePath + "/%s/cancellation".formatted(NumberUtils.INTEGER_MINUS_ONE)))
                .body("detail", equalTo(exceptionMessageSource.getMessage("card.notFound", new Object[] { NumberUtils.INTEGER_MINUS_ONE }, Locale.getDefault())));;
    }
}