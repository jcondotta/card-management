package com.blitzar.cards.web.controller;

import com.blitzar.cards.TestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import com.blitzar.cards.web.dto.CardDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetCardControllerTest extends TestContainer {

    private String currentTestName;
    private RequestSpecification requestSpecification;

    @Autowired
    private AddCardService addCardService;

    @BeforeAll
    public static void beforeAll(@LocalServerPort int serverHttpPort){
        RestAssured.port = serverHttpPort;
        RestAssured.basePath = "/api/v1/cards";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        this.currentTestName = testInfo.getTestMethod().orElseThrow().getName();
        this.requestSpecification = new RequestSpecBuilder()
                .build()
                .contentType(ContentType.JSON);
    }

    @Test
    public void givenExistentCardId_whenGetCard_thenReturnOk(){
        var addCardDelegate = new TestAddCardDelegate().buildCardRequest();
        Card card = addCardService.addCard(addCardDelegate);

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
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
