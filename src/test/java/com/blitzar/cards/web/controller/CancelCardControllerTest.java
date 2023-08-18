package com.blitzar.cards.web.controller;

import com.blitzar.cards.LocalStackMySQLTestContainer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
class CancelCardControllerTest implements LocalStackMySQLTestContainer {

//    @Autowired
//    @Qualifier("exceptionMessageSource")
//    private MessageSource exceptionMessageSource;
//
//    @Autowired
//    private AddCardService addCardService;
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    private RequestSpecification requestSpecification;
//
//    @BeforeAll
//    public static void beforeAll(@LocalServerPort int serverHttpPort){
//        port = serverHttpPort;
//        basePath = "/api/v1/cards";
//        enableLoggingOfRequestAndResponseIfValidationFails();
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        this.requestSpecification = new RequestSpecBuilder()
//                .build()
//                .contentType(ContentType.JSON);
//    }
//
//    @Test
//    public void givenExistentCardId_whenCancelCard_thenReturnOk(){
//        var addCardRequest = new AddCardRequest("Mateo Condotta");
//        Card card = addCardService.addCard(addCardRequest);
//
//        given()
//            .spec(requestSpecification)
//        .when()
//            .patch("/{id}/cancellation", card.getCardId())
//        .then()
//            .statusCode(HttpStatus.NO_CONTENT.value());
//
//        cardRepository.findById(card.getCardId())
//                .ifPresentOrElse(patchedCard -> assertThat(patchedCard.getCardStatus()).isEqualTo(CardStatus.CANCELLED),
//                        () -> fail(exceptionMessageSource.getMessage("card.notFound", new Object[]{ card.getCardId() }, Locale.getDefault())));
//    }
//
//    @Test
//    public void givenNonExistentCardId_whenCancelCard_thenReturnNotFound(){
//        given()
//            .spec(requestSpecification)
//        .when()
//            .patch("/{id}/cancellation", NumberUtils.INTEGER_MINUS_ONE)
//        .then()
//            .statusCode(HttpStatus.NOT_FOUND.value())
//                .body("title", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
//                .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
//                .body("instance", equalTo(RestAssured.basePath + "/%s/cancellation".formatted(NumberUtils.INTEGER_MINUS_ONE)))
//                .body("detail", equalTo(exceptionMessageSource.getMessage("card.notFound", new Object[] { NumberUtils.INTEGER_MINUS_ONE }, Locale.getDefault())));;
//    }
}