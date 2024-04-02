package com.blitzar.cards.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull(message = "card.bankAccountId.notNull")
@Positive(message = "card.bankAccountId.positive")
@Target(value = { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(value = RUNTIME)
@Constraint(validatedBy = {})
public @interface BankAccountId {

    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
