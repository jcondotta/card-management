package com.blitzar.cards.web.controller;

import com.blitzar.cards.LocalStackMySQLTestContainer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
public class AddCardControllerTest implements LocalStackMySQLTestContainer {

//    private RequestSpecification requestSpecification;
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Autowired
//    @Qualifier("testFixedInstantUTC")
//    private Clock testFixedInstantUTC;
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
//    public void givenValidRequest_whenAddCard_thenReturnCreated(){
//        var addCardRequest = new AddCardRequest("Jefferson Feitosa");
//
//        Response response = given()
//            .spec(requestSpecification)
//            .body(addCardRequest)
//        .when()
//            .post()
//        .then()
//            .statusCode(HttpStatus.CREATED.value())
//                .extract().response();
//
//        Long cardId = response.body().as(Long.class);
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow();
//
//        assertAll(
//                () -> assertThat(card.getCardId()).isNotNull(),
//                () -> assertThat(card.getCardholderName()).isEqualTo(addCardRequest.cardholderName()),
//                () -> assertThat(card.getCardNumber()).isNotNull(),
//                () -> assertThat(card.getCardStatus()).isEqualTo(AddCardDelegate.DEFAULT_CARD_STATUS),
//                () -> assertThat(card.getDailyWithdrawalLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_WITHDRAWAL_LIMIT),
//                () -> assertThat(card.getDailyPaymentLimit()).isEqualTo(AddCardDelegate.DEFAULT_DAILY_PAYMENT_LIMIT),
//                () -> assertThat(card.getExpirationDate()).isEqualTo(LocalDate.now(testFixedInstantUTC)
//                        .plus(AddCardDelegate.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS))
//        );
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(InvalidStringArgumentProvider.class)
//    public void givenInvalidCardholderName_whenAddCard_thenReturnBadRequest(String invalidCardholderName){
//        var addCardRequest = new AddCardRequest(invalidCardholderName);
//
//        given()
//            .spec(requestSpecification)
//            .body(addCardRequest)
//        .when()
//            .post()
//        .then()
//            .statusCode(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    public void givenCardholderNameLongerThan21Characters_whenAddCard_thenReturnBadRequest(){
//        var invalidCardholderName = RandomStringUtils.randomAlphabetic(22);
//        var addCardRequest = new AddCardRequest(invalidCardholderName);
//
//        given()
//            .spec(requestSpecification)
//            .body(addCardRequest)
//        .when()
//            .post()
//        .then()
//            .statusCode(HttpStatus.BAD_REQUEST.value());
//    }
}
