package com.blitzar.cards.web.controller;

import com.blitzar.cards.domain.Card;
import com.blitzar.cards.service.AddCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/cards")
public class AddCardController {

    private final AddCardService addCardService;

    @Autowired
    public AddCardController(AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCard(@RequestBody AddCardRequest request){
        Card card = addCardService.addCard(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(card.getCardId())
                .toUri();

        String url = null;
        try {
            url = location.toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", url).body(card.getCardId());
    }
}