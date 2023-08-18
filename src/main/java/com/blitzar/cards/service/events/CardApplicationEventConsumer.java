package com.blitzar.cards.service.events;

import com.blitzar.cards.service.AddCardService;
import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.jms.sqs.configuration.SqsConfiguration;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@JMSListener(SqsConfiguration.CONNECTION_FACTORY_BEAN_NAME)
public class CardApplicationEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CardApplicationEventConsumer.class);

    private final AddCardService addCardService;

    @Inject
    public CardApplicationEventConsumer(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @Queue(value = "card-application", concurrency = "1-3")
    public void consumeMessage(@MessageBody CardApplicationEvent cardApplicationEvent) {
        logger.info("Received a card Application event with cardholderName: {}", cardApplicationEvent.getCardholderName());

        addCardService.addCard(cardApplicationEvent);
    }
}