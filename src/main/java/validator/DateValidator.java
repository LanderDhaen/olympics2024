package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDateTime> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {

        if(value == null) {
            return false;
        }

        LocalDate startDate = LocalDate.of(2024, 7, 26);
        LocalDate endDate = LocalDate.of(2024, 8, 11);
        LocalDate dateToCheck = value.toLocalDate();
        if (dateToCheck.isBefore(startDate) || dateToCheck.isAfter(endDate)) {
            return false;
        }

        if (value.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            return false;
        }

        return true;
    }
}
