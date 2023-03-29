package com.blitzar.cards.web.controller;

import com.blitzar.cards.TestContainer;
import com.blitzar.cards.argumentprovider.InvalidStringArgumentProvider;
import com.blitzar.cards.events.CardApplicationEvent;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.flyway.enabled=false"
})
public class CardApplicationControllerTest extends TestContainer {

    @Autowired
    private MessageSource messageSource;

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
        var cardApplicationEvent = new CardApplicationEvent("Jefferson Qikern");

        given()
            .spec(requestSpecification)
            .body(cardApplicationEvent)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.ACCEPTED.value())
                .body(equalTo(messageSource.getMessage("card.application.accepted", new Object[]{}, Locale.getDefault())));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidStringArgumentProvider.class)
    public void givenInvalidCardholderName_whenCardApplication_thenReturnBadRequest(String invalidCardholderName){
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
    public void givenCardholderNameLongerThan21Characters_whenCardApplication_thenReturnBadRequest(){
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