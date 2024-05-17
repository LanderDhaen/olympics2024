package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OlympicNumber1Validator implements ConstraintValidator<ValidOlympicNumber1, Integer> {
    @Override
    public void initialize(ValidOlympicNumber1 constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer  value, ConstraintValidatorContext context) {

        return true;
    }
}
