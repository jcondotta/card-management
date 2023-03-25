package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.CancelCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CancelCardController {

    private final CancelCardService cancelCardService;

    @Autowired
    public CancelCardController(CancelCardService cancelCardService) {
        this.cancelCardService = cancelCardService;
    }

    @PatchMapping(value = "/{id}/cancellation")
    public ResponseEntity<?> addCard(@PathVariable("id") Long id){
        cancelCardService.cancelCard(id);
        return ResponseEntity.noContent().build();
    }
}