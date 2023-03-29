package com.blitzar.cards.web.controller;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.AddCardService;
import com.blitzar.cards.web.controller.request.AddCardRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class AddCardController {

    private final AddCardService addCardService;

    @Autowired
    public AddCardController(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCard(@Valid @RequestBody AddCardRequest request){
        Card card = addCardService.addCard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(card.getCardId());
    }
}