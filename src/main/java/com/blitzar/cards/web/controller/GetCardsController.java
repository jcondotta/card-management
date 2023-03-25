package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.GetCardService;
import com.blitzar.cards.web.dto.CardsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class GetCardsController {

    private final GetCardService getCardService;

    @Autowired
    public GetCardsController(GetCardService getCardService) {
        this.getCardService = getCardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardsDTO> getGoals(){
        return new ResponseEntity<>(getCardService.findAll(), HttpStatus.OK);
    }
}