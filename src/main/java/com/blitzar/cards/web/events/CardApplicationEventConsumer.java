package com.blitzar.cards.web.events;

import com.blitzar.cards.service.AddCardService;
//import io.micronaut.jms.annotations.JMSListener;
//import io.micronaut.jms.annotations.Queue;
//import io.micronaut.jms.sqs.configuration.SqsConfiguration;
//import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@JMSListener(SqsConfiguration.CONNECTION_FACTORY_BEAN_NAME)
public class CardApplicationEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CardApplicationEventConsumer.class);

    private final AddCardService addCardService;

    @Inject
    public CardApplicationEventConsumer(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

//    @Queue(value = "${app.aws.sqs.card-application-queue-name}")
//    public void consumeMessage(@MessageBody CardApplicationEvent cardApplicationEvent) {
//        logger.info("[BankAccountId={}, CardholderName={}] Received a card application event", cardApplicationEvent.getBankAccountId(), cardApplicationEvent.getCardholderName());
//
//        addCardService.addCard(cardApplicationEvent);
//    }
}