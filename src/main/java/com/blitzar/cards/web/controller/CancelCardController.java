package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.CancelCardService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.CARD_V1_MAPPING)
public class CancelCardController {

    private final CancelCardService cancelCardService;

    @Inject
    public CancelCardController(CancelCardService cancelCardService) {
        this.cancelCardService = cancelCardService;
    }

    @Patch(value = "/cancellation")
    public HttpResponse<?> cancelCard(@PathVariable("card-id") Long cardId){
        cancelCardService.cancelCard(cardId);
        return HttpResponse.noContent();
    }
}