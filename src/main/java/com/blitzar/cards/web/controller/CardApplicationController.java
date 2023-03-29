package com.blitzar.cards.web.controller;

import com.blitzar.cards.service.CardApplicationEventHandler;
import com.blitzar.cards.events.CardApplicationEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/cards")
public class CardApplicationController {

    private CardApplicationEventHandler cardApplicationEventHandler;
    private MessageSource messageSource;

    @Autowired
    public CardApplicationController(CardApplicationEventHandler cardApplicationEventHandler, MessageSource messageSource) {
        this.cardApplicationEventHandler = cardApplicationEventHandler;
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/application", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCardApplication(@RequestHeader(name ="Accept-Language", required= false) Locale locale, @Valid @RequestBody CardApplicationEvent cardApplicationEvent){
        if(locale != null){
            LocaleContextHolder.setLocale(locale);
        }
        cardApplicationEventHandler.handle(cardApplicationEvent);
        return ResponseEntity.accepted().body(messageSource.getMessage("card.application.accepted", new Object[]{}, LocaleContextHolder.getLocale()));
    }
}
