package com.blitzar.cards.web.exceptionhandler;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolationsException;
import com.blitzar.cards.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpServletRequest request;
    private MessageSource messageSource;

    @Autowired
    public RestResponseEntityExceptionHandler(HttpServletRequest request, @Qualifier("exceptionMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
        this.request = request;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ErrorResponse handleConflict(ResourceNotFoundException e, HttpServletRequest request) {
        String errorMessage= null;
        if(LocaleContextHolder.getLocale() != null){
            errorMessage = messageSource.getMessage(e.getMessage(), new Object[]{e.getRejectedIdentifier()}, "Something", LocaleContextHolder.getLocale());
        }
        else{
            errorMessage = messageSource.getMessage(e.getMessage(), new Object[]{e.getRejectedIdentifier()}, "Something", Locale.getDefault());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, errorMessage);
        problemDetail.setProperty("timestamp", LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS));
        problemDetail.setType(URI.create(request.getRequestURL().toString()));

        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage())
                .title("Bookmark not found")
                .type(URI.create("https://api.bookmarks.com/errors/not-found"))
                .property("errorCategory", "Generic")
                .property("timestamp", Instant.now())
                .build();

//        return ResponseEntity.status(404).body(problemDetail);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { ConstraintViolationsException.class })
    protected ResponseEntity<?> handleConflict(ConstraintViolationsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        Map<String, String> errorMessages = new HashMap<>();
        for (ConstraintViolation violation : exception.violations()) {
            String exceptionMessage = messageSource.getMessage(violation.message(), new Object[]{}, Locale.getDefault());
            errorMessages.put("message", String.join(": ", violation.name(), exceptionMessage));
        }

        problemDetail.setProperty("errors", errorMessages);

        return ResponseEntity.badRequest().body(problemDetail);
    }
}