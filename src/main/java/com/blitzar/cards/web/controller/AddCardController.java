package com.blitzar.cards.web.controller;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.service.request.AddCardRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.BASE_PATH_API_V1_MAPPING)
public class AddCardController {

    private final AddCardService addCardService;

    @Inject
    public AddCardController(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<?> addCard(@Body AddCardRequest addCardRequest){
        Card card = addCardService.addCard(addCardRequest);

        return HttpResponse.created(card.getCardId());
    }
}