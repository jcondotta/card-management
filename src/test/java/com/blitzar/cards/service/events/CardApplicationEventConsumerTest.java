package com.blitzar.cards.service.events;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import com.blitzar.cards.LocalStackMySQLTestContainer;
import com.blitzar.cards.domain.Card;
import com.blitzar.cards.repository.CardRepository;
import com.blitzar.cards.service.request.AddCardRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
public class CardApplicationEventConsumerTest implements LocalStackMySQLTestContainer {

//    @Inject
//    private AmazonSQS sqsClient;
//
//    @Value("${app.aws.sqs.card-application-queue-name}")
//    private String cardApplicationQueueName;
//
//    private String cardApplicationQueueURL;
//
//    @Inject
//    private ObjectMapper objectMapper;
//
//    @Inject
//    private CardRepository cardRepository;
//
//    @Inject
//    private Clock testFixedInstantUTC;
//
//    private String cardholderName = "Jefferson Condotta";
//    private String accountHolderIban = UUID.randomUUID().toString();
//
//    @BeforeEach
//    public void beforeEach() {
//        this.cardApplicationQueueURL = sqsClient.createQueue(cardApplicationQueueName).getQueueUrl();
//        this.cardRepository.deleteAll();
//    }
//
//    @AfterEach
//    public void afterEach() {
//        sqsClient.purgeQueue(new PurgeQueueRequest(cardApplicationQueueURL));
//    }
//
//    @Test
//    public void givenValidCardApplicationSQSMessage_whenConsumeMessage_thenCreateCard() throws JsonProcessingException {
//        var cardApplicationEvent = new CardApplicationEvent(cardholderName, accountHolderIban);
//
//        sqsClient.sendMessage(cardApplicationQueueURL, objectMapper.writeValueAsString(cardApplicationEvent));
//
//        await().pollDelay(5, TimeUnit.SECONDS).untilAsserted(() -> {
//
//            List<Card> cards = cardRepository.findAll();
//            assertThat(cards).hasSize(1);
//
//            var card = cards.get(0);
//            assertAll(
//                    () -> assertThat(card.getCardId()).isNotNull(),
//                    () -> assertThat(card.getCardholderName()).isEqualTo(cardApplicationEvent.getCardholderName()),
//                    () -> assertThat(card.getAccountHolderIban()).isEqualTo(cardApplicationEvent.getIban()),
//                    () -> assertThat(card.getCardNumber()).isNotNull(),
//                    () -> assertThat(card.getCardStatus()).isEqualTo(AddCardRequest.DEFAULT_CARD_STATUS),
//                    () -> assertThat(card.getDailyWithdrawalLimit()).isEqualTo(AddCardRequest.DEFAULT_DAILY_WITHDRAWAL_LIMIT),
//                    () -> assertThat(card.getDailyPaymentLimit()).isEqualTo(AddCardRequest.DEFAULT_DAILY_PAYMENT_LIMIT),
//                    () -> assertThat(card.getExpirationDate()).isEqualTo(LocalDate.now(testFixedInstantUTC)
//                            .plus(AddCardRequest.DEFAULT_YEAR_PERIOD_EXPIRATION_DATE, ChronoUnit.YEARS))
//            );}
//        );
//    }
}