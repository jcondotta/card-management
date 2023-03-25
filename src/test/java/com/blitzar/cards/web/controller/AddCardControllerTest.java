package com.blitzar.cards.web.controller;

import com.blitzar.cards.TestMySQLContainer;
import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.config.TestTimeConfiguration;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.AddCardDelegate;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestTimeConfiguration.class)
public class AddCardControllerTest extends TestMySQLContainer {

    private String currentTestName;
    private RequestSpecification requestSpecification;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    @Qualifier("testFixedInstantUTC")
    private Clock testFixedInstantUTC;

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
    public void givenValidRequest_whenAddCard_thenReturnCreated(){
        var addCardRequest = new TestAddCardDelegate().buildCardRequest();

        Response response = given()
            .spec(requestSpecification)
            .body(addCardRequest)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
                .extract().response();

        Long cardId = response.body().as(Long.class);
        Card card = cardRepository.findById(cardId)
                .orElseThrow();

        assertAll(
                () -> assertThat(card.getCardId()).isNotNull(),
                () -> assertThat(card.getCardholderName()).isEqualTo(addCardRequest.getCardholderName()),
                () -> assertThat(card.getCardNumber()).isNotNull(),
                () -> assertThat(card.getCardStatus()).isEqualTo(AddCardDelegate.DEFAULT_CARD_STATUS),
                () -> assertThat(card.getDailyWithdrawalLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_WITHDRAWAL_LIMIT),
                () -> assertThat(card.getDailyPaymentLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_PAYMENT_LIMIT),
                () -> assertThat(card.getExpirationDate()).isEqualTo(LocalDate.now(testFixedInstantUTC)
                        .plus(AddCardDelegate.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS))
        );
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenAddCard_thenReturnBadRequest(String invalidCardholderName){
        var addCardRequest = new TestAddCardDelegate()
                .setCardholderName(invalidCardholderName)
                .buildCardRequest();

        given()
            .spec(requestSpecification)
            .body(addCardRequest)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void givenCardholderNameLongerThan21Characters_whenAddCard_thenReturnBadRequest(){
        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
        var addCardRequest = new TestAddCardDelegate()
                .setCardholderName(invalidCardholderName)
                .buildCardRequest();

        given()
            .spec(requestSpecification)
            .body(addCardRequest)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
