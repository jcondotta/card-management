package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.GetCardService;
import com.blitzar.cards.web.dto.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/cards")
public class GetCardController {

    private final GetCardService getCardService;

    @Autowired
    public GetCardController(GetCardService getCardService) {
        this.getCardService = getCardService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> byId(@RequestHeader(name ="Accept-Language", required= false) Locale locale, @PathVariable("id") Long id){
        if(locale != null){
            LocaleContextHolder.setLocale(locale);
        }
        CardDTO cardDTO = getCardService.byId(id);
        return ResponseEntity.ok().body(cardDTO);
    }
}