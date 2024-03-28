package com.blitzar.cards.web.exception_handler;

import com.blitzar.cards.exception.ResourceNotFoundException;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Status;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;

@Produces
@Singleton
@Requires(classes = { ResourceNotFoundException.class })
public class ResourceNotFoundExceptionHandler implements ExceptionHandler<ResourceNotFoundException, HttpResponse<?>> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundExceptionHandler.class);

    private final MessageSource messageSource;
    private final ErrorResponseProcessor<?> errorResponseProcessor;

    @Inject
    public ResourceNotFoundExceptionHandler(@Named("exceptionMessageSource") MessageSource messageSource, ErrorResponseProcessor<?> errorResponseProcessor) {
        this.messageSource = messageSource;
        this.errorResponseProcessor = errorResponseProcessor;
    }

    @Override
    @Status(value = HttpStatus.NOT_FOUND)
    public HttpResponse<?> handle(HttpRequest request, ResourceNotFoundException e) {
        var errorMessage = messageSource.getMessage(e.getMessage(), Locale.getDefault()).orElse(e.getMessage());

        logger.error(errorMessage);
        return errorResponseProcessor.processResponse(ErrorContext.builder(request)
                .cause(e)
                .errorMessage(errorMessage)
                .build(), HttpResponse.notFound());
    }
}