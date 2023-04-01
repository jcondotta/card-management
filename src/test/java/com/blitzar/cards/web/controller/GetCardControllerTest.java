package com.blitzar.cards.web.controller;

import com.blitzar.cards.KafkaTestContainer;
import com.blitzar.cards.MySQLTestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.delegate.AddCardRequest;
import com.blitzar.cards.web.dto.CardDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.math.NumberUtils;
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

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetCardControllerTest implements MySQLTestContainer, KafkaTestContainer {

    @Autowired
    @Qualifier("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    @Autowired
    private AddCardService addCardService;

    private RequestSpecification requestSpecification;

    @BeforeAll
    public static void beforeAll(@LocalServerPort int serverHttpPort){
        RestAssured.port = serverHttpPort;
        RestAssured.basePath = "/api/v1/cards";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach() {
        this.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void givenExistentCardId_whenGetCard_thenReturnOk(){
        var addCardRequest = new AddCardRequest("Jefferson Condotta");
        Card card = addCardService.addCard(addCardRequest);

        CardDTO cardDTO = given()
            .spec(requestSpecification)
        .when()
            .get("/{id}", card.getCardId())
        .then()
            .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(CardDTO.class);

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
    public void givenNonExistentCardId_whenGetCard_thenReturnNotFound(){
        given()
            .spec(requestSpecification)
        .when()
            .get("/{id}", NumberUtils.INTEGER_MINUS_ONE)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                .body("instance", equalTo(RestAssured.basePath + "/%s".formatted(NumberUtils.INTEGER_MINUS_ONE)))
                .body("detail", equalTo(exceptionMessageSource.getMessage("card.notFound", new Object[] { org.testcontainers.shaded.org.apache.commons.lang3.math.NumberUtils.INTEGER_MINUS_ONE }, Locale.getDefault())));;
    }
}
