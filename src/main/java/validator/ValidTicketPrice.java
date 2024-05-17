package validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = TicketPriceValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValidTicketPrice {

    String message() default "This ticket price is impossible";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}