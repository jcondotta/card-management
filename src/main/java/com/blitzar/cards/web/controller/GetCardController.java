package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.GetCardService;
import com.blitzar.cards.web.dto.CardDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller(CardAPIConstants.BASE_PATH_API_V1_MAPPING)
public class GetCardController {

    private final GetCardService getCardService;

    @Inject
    public GetCardController(GetCardService getCardService) {
        this.getCardService = getCardService;
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> byId(@PathVariable("id") Long id){
        CardDTO cardDTO = getCardService.byId(id);
        return HttpResponse.ok().body(cardDTO);
    }
}