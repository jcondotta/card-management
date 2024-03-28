package com.blitzar.cards.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
