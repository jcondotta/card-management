package com.blitzar.cards.events;

import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.delegate.AddCardRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CardApplicationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CardApplicationEventListener.class);

    private final AddCardService addCardService;

    @Autowired
    public CardApplicationEventListener(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @KafkaListener(topics = "${app.kafka.card-application-topic}", groupId = "${app.kafka.card-application-group-id}")
    public void cardApplicationListener(@NotNull @Valid CardApplicationEvent cardApplicationEvent){
        logger.info("Received a Card Application event with cardholderName: {}", cardApplicationEvent.cardholderName());

        addCardService.addCard(new AddCardRequest(cardApplicationEvent.cardholderName()));
    }
}