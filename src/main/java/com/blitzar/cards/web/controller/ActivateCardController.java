package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.ActivateCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class ActivateCardController {

    private final ActivateCardService activateCardService;

    @Autowired
    public ActivateCardController(ActivateCardService activateCardService) {
        this.activateCardService = activateCardService;
    }

    @PatchMapping(value = "/{id}/activation")
    public ResponseEntity<?> addCard(@PathVariable("id") Long id){
        activateCardService.activateCard(id);
        return ResponseEntity.noContent().build();
    }
}