package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.GetCardService;
import com.blitzar.cards.service.dto.CardDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.CARD_V1_MAPPING)
public class GetCardController {

    private final GetCardService getCardService;

    @Inject
    public GetCardController(GetCardService getCardService) {
        this.getCardService = getCardService;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> findById(@PathVariable("card-id") Long cardId){
        CardDTO cardDTO = getCardService.findById(cardId);
        return HttpResponse.ok().body(cardDTO);
    }
}