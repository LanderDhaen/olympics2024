package validator;

import domain.Game;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

public class RemainingSeatsValidator implements Validator {

    private final static int MIN = 0;
    private final static int MAX = 50;

    @Override
    public boolean supports(Class<?> klass) {
        return Game.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Game game = (Game) target;

        int remainingSeats = game.getRemainingSeats();
        int capacity = game.getStadium().getCapacity();

        System.out.println(capacity);

        if (remainingSeats < MIN || remainingSeats > MAX) {
            errors.rejectValue("remainingSeats", "validator.validSeats.range", new Object[]{MIN, MAX}, "validator.validSeats.default");
        }

        if(remainingSeats > capacity) {
            errors.rejectValue("remainingSeats", "validator.validSeats.maxCapacity", new Object[]{capacity},"validator.validSeats.default");
        }

    }

}
