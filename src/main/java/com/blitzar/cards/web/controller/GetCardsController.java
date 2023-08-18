package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.GetCardService;
import com.blitzar.cards.web.dto.CardsDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.BASE_PATH_API_V1_MAPPING)
public class GetCardsController {

    private final GetCardService getCardService;

    @Inject
    public GetCardsController(GetCardService getCardService) {
        this.getCardService = getCardService;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<CardsDTO> getGoals(){
        return HttpResponse.ok(getCardService.findAll());
    }
}