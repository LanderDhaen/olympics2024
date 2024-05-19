package validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DisciplinesValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValidDisciplines {

    String message() default "{validator.validDisciplines}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    int max();

}
