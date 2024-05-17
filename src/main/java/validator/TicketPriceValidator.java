package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TicketPriceValidator implements ConstraintValidator<ValidTicketPrice, Double> {

    @Override
    public void initialize(ValidTicketPrice constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Or true depending on your validation logic for null values
        }

        double min = 0.00; // Define your minimum value
        double max = 150.00; // Define your maximum value

        if (value <= min || value >= max) {
            String messageTemplate = context.getDefaultConstraintMessageTemplate();
            String interpolatedMessage = messageTemplate
                    .replace("{min}", String.valueOf(min))
                    .replace("{max}", String.valueOf(max));
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(interpolatedMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
