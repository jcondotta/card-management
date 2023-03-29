package com.blitzar.cards.events;

import com.blitzar.cards.service.AddCardService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
public class CardApplicationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CardApplicationEventListener.class);

    private final AddCardService addCardService;

    @Autowired
    public CardApplicationEventListener(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @KafkaListener(topics = "${app.kafka.card-application-topic}", groupId = "${app.kafka.card-application-group-id}")
    public void cardApplicationListener(@NotNull CardApplicationEvent cardApplicationEvent){
        logger.info("Received a Card Application event with cardholderName: {}", cardApplicationEvent.cardholderName());

        addCardService.addCard(cardApplicationEvent);
    }
}