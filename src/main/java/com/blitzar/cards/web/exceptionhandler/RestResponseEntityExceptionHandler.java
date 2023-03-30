package com.blitzar.cards.web.exceptionhandler;

import com.blitzar.cards.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public RestResponseEntityExceptionHandler(@Qualifier("exceptionMessageSource") MessageSource messageSource) {
        super.setMessageSource(messageSource);
        this.messageSource = messageSource;
    }

    private record FieldError(String field, String message){}


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create(StringUtils.EMPTY));

        List<FieldError> fieldErrors = ex.getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), messageSource.getMessage(error.getDefaultMessage(), null, request.getLocale())))
                .collect(Collectors.toList());

        problemDetail.setProperty("errors", fieldErrors);

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(ResourceNotFoundException e, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(StringUtils.EMPTY));
        problemDetail.setDetail(messageSource.getMessage(e.getMessage(), new Object[]{ e.getRejectedIdentifier() }, request.getLocale()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}