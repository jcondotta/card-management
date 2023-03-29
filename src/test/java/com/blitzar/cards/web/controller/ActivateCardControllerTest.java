package com.blitzar.cards.web.controller;

import com.blitzar.cards.TestContainer;
import com.blitzar.cards.config.TestTimeConfiguration;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.domain.CardStatus;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.web.controller.stubs.TestAddCardDelegate;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.org.apache.commons.lang3.math.NumberUtils;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestTimeConfiguration.class)
class ActivateCardControllerTest extends TestContainer {

    private RequestSpecification requestSpecification;

    @Autowired
    private AddCardService addCardService;

    @Autowired
    private CardRepository cardRepository;

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
    public void givenExistentCardId_whenActivateCard_thenReturnOk(){
        var addCardDelegate = new TestAddCardDelegate().buildCardRequest();
        Card card = addCardService.addCard(addCardDelegate);

        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/activation", card.getCardId())
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        cardRepository.findById(card.getCardId())
                .ifPresentOrElse(patchedCard -> assertThat(patchedCard.getCardStatus()).isEqualTo(CardStatus.ACTIVE),
                        () -> fail("No card has been found with id: %s",  card.getCardId()));
    }

    @Test
    public void givenNonExistentCardId_whenActivateCard_thenReturnNotFound(){
        given()
            .spec(requestSpecification)
        .when()
            .patch("/{id}/activation", NumberUtils.INTEGER_MINUS_ONE)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}