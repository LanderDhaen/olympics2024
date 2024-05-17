package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OlympicNumber2Validator implements ConstraintValidator<ValidOlympicNumber2, Integer> {

    @Override
    public void initialize(ValidOlympicNumber2 constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer  value, ConstraintValidatorContext context) {

        return true;
    }
}
