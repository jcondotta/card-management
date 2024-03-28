package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.ActivateCardService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.CARD_V1_MAPPING)
public class ActivateCardController {

    private final ActivateCardService activateCardService;

    @Inject
    public ActivateCardController(ActivateCardService activateCardService) {
        this.activateCardService = activateCardService;
    }

    @Patch(value = "/activation")
    public HttpResponse<?> activateCard(@PathVariable("card-id") Long cardId){
        activateCardService.activateCard(cardId);
        return HttpResponse.noContent();
    }
}