package com.blitzar.cards.web.controller;

import com.blitzar.cards.LocalStackMySQLTestContainer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
public class GetCardControllerTest implements LocalStackMySQLTestContainer {

//    @Autowired
//    @Qualifier("exceptionMessageSource")
//    private MessageSource exceptionMessageSource;
//
//    @Autowired
//    private AddCardService addCardService;
//
//    private RequestSpecification requestSpecification;
//
//    @BeforeAll
//    public static void beforeAll(@LocalServerPort int serverHttpPort){
//        RestAssured.port = serverHttpPort;
//        RestAssured.basePath = "/api/v1/cards";
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        this.requestSpecification = new RequestSpecBuilder()
//                .setContentType(ContentType.JSON)
//                .build();
//    }
//
//    @Test
//    public void givenExistentCardId_whenGetCard_thenReturnOk(){
//        var addCardRequest = new AddCardRequest("Jefferson Condotta");
//        Card card = addCardService.addCard(addCardRequest);
//
//        CardDTO cardDTO = given()
//            .spec(requestSpecification)
//        .when()
//            .get("/{id}", card.getCardId())
//        .then()
//            .statusCode(HttpStatus.OK.value())
//                .extract()
//                    .as(CardDTO.class);
//
//        assertAll(
//                () -> assertThat(cardDTO.cardId()).isEqualTo(card.getCardId()),
//                () -> assertThat(cardDTO.cardholderName()).isEqualTo(card.getCardholderName()),
//                () -> assertThat(cardDTO.cardNumber()).isEqualTo(card.getCardNumber()),
//                () -> assertThat(cardDTO.cardStatus()).isEqualTo(card.getCardStatus()),
//                () -> assertThat(cardDTO.dailyWithdrawalLimit()).isEqualTo(card.getDailyWithdrawalLimit()),
//                () -> assertThat(cardDTO.dailyPaymentLimit()).isEqualTo(card.getDailyPaymentLimit()),
//                () -> assertThat(cardDTO.expirationDate()).isEqualTo(card.getExpirationDate())
//        );
//    }
//
//    @Test
//    public void givenNonExistentCardId_whenGetCard_thenReturnNotFound(){
//        given()
//            .spec(requestSpecification)
//        .when()
//            .get("/{id}", NumberUtils.INTEGER_MINUS_ONE)
//        .then()
//            .statusCode(HttpStatus.NOT_FOUND.value())
//                .body("title", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
//                .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
//                .body("instance", equalTo(RestAssured.basePath + "/%s".formatted(NumberUtils.INTEGER_MINUS_ONE)))
//                .body("detail", equalTo(exceptionMessageSource.getMessage("card.notFound", new Object[] { NumberUtils.INTEGER_MINUS_ONE }, Locale.getDefault())));
//    }
}
