package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.LockCardService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.CARD_V1_MAPPING)
public class LockCardController {

    private final LockCardService lockCardService;

    @Inject
    public LockCardController(LockCardService lockCardService) {
        this.lockCardService = lockCardService;
    }

    @Patch(value = "/lock")
    public HttpResponse<?> lockCard(@PathVariable("card-id") Long cardId){
        lockCardService.lockCard(cardId);
        return HttpResponse.noContent();
    }
}