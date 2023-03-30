package com.blitzar.cards.web.controller;

import com.blitzar.cards.KafkaTestContainer;
import com.blitzar.cards.MySQLTestContainer;
import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.config.TestTimeConfiguration;
import com.blitzar.cards.events.CardApplicationEvent;
import com.blitzar.cards.events.CardApplicationEventListener;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.delegate.AddCardDelegate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = TestTimeConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CardApplicationControllerTest implements KafkaTestContainer, MySQLTestContainer {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("exceptionMessageSource")
    private MessageSource exceptionMessageSource;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    @Qualifier("testFixedInstantUTC")
    private Clock testFixedInstantUTC;

    @SpyBean
    private CardApplicationEventListener cardApplicationEventListener;

    @Captor
    private ArgumentCaptor<CardApplicationEvent> myMessageCaptor;

    private RequestSpecification requestSpecification;

    @BeforeAll
    public static void beforeAll(@LocalServerPort int serverHttpPort){
        RestAssured.port = serverHttpPort;
        RestAssured.basePath = "/api/v1/cards/application";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void beforeEach() {
        this.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void givenValidRequest_whenCardApplication_thenReturnCreated(){
        var cardApplicationEvent = new CardApplicationEvent("Jefferson William");

        given()
            .spec(requestSpecification)
            .body(cardApplicationEvent)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.ACCEPTED.value())
                .body("cardApplicationReference", Matchers.notNullValue())
                .body("message", equalTo(messageSource.getMessage("card.application.accepted", null, Locale.getDefault())));

        await().pollInterval(Duration.ofMillis(100)).atMost(1, SECONDS)
                .untilAsserted(() -> verify(cardApplicationEventListener).cardApplicationListener(myMessageCaptor.capture()));

        assertThat(myMessageCaptor.getValue().cardholderName()).isEqualTo(cardApplicationEvent.cardholderName());

        var cards = cardRepository.findByCardholderName(cardApplicationEvent.cardholderName());
        assertThat(cards).hasSize(1);

        cards.stream()
                .findFirst()
                        .ifPresent(card -> assertAll(
                                () -> assertThat(card.getCardId()).isNotNull(),
                                () -> assertThat(card.getCardholderName()).isEqualTo(cardApplicationEvent.cardholderName()),
                                () -> assertThat(card.getCardNumber()).isNotNull(),
                                () -> assertThat(card.getCardStatus()).isEqualTo(AddCardDelegate.DEFAULT_CARD_STATUS),
                                () -> assertThat(card.getDailyWithdrawalLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_WITHDRAWAL_LIMIT),
                                () -> assertThat(card.getDailyPaymentLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_PAYMENT_LIMIT),
                                () -> assertThat(card.getExpirationDate())
                                        .isEqualTo(LocalDate.now(testFixedInstantUTC).plus(AddCardDelegate.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS))
                        ));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenCardApplication_thenReturnBadRequest(String invalidCardholderName){
        var cardApplicationEvent = new CardApplicationEvent(invalidCardholderName);

        given()
            .spec(requestSpecification)
            .body(cardApplicationEvent)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("instance", equalTo(RestAssured.basePath))
                .body("errors", hasSize(1))
                .body("errors[0].field", equalTo("cardholderName"))
                .body("errors[0].message", equalTo(exceptionMessageSource.getMessage("card.cardholderName.notBlank", null, Locale.getDefault())));
    }

    @Test
    public void givenCardholderNameLongerThan21Characters_whenCardApplication_thenReturnBadRequest(){
        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
        var cardApplicationEvent = new CardApplicationEvent(invalidCardholderName);

        given()
            .spec(requestSpecification)
            .body(cardApplicationEvent)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("instance", equalTo(RestAssured.basePath))
                .body("errors", hasSize(1))
                .body("errors[0].field", equalTo("cardholderName"))
                .body("errors[0].message", equalTo(exceptionMessageSource.getMessage("card.cardholderName.length.limit", null, Locale.getDefault())));
    }
}