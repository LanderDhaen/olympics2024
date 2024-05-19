package validator;

import domain.Discipline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisciplinesValidator implements ConstraintValidator<ValidDisciplines, List<Discipline>> {

    private int max;

    @Override
    public void initialize(ValidDisciplines constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<Discipline> value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if(value == null) {
            return true;
        }

        int size = value.size();
        int uniques = new HashSet<>(value).size();

        if (size > max) {
            context.buildConstraintViolationWithTemplate("{validator.validDisciplines.size}")
                    .addConstraintViolation();
            return false;
        }

        if(uniques < size) {
            context.buildConstraintViolationWithTemplate("{validator.validDisciplines.duplicate}").addConstraintViolation();
            return false;
        }

        return true;
    }
}
