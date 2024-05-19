package validator;

import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import service.GameService;

public class OlympicNumberValidator implements Validator {

    @Autowired
    GameService gameService;

    private final static int RANGE = 1000;
    private final static int AMOUNT = 5;
    private final static int INVALID_FIRSTDIGIT = 0;
    private final static int MIN = 10000;
    private final static int MAX = 99999;


    @Override
    public boolean supports(Class<?> klass) {
        return Game.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Game game = (Game) target;

        int olympicNumber1 = game.getOlympicNumber1();
        int olympicNumber2 = game.getOlympicNumber2();

        int firstNumber = olympicNumber1 / 10000;
        int lastNumber = olympicNumber1 % 10;


        if (olympicNumber1 < MIN || olympicNumber1 > MAX) {
            errors.rejectValue("olympicNumber1", "validator.validOlympicNumber1.digitAmount", new Object[]{AMOUNT, INVALID_FIRSTDIGIT}, "validator.validOlympicNumber1.default");

        }

        if (olympicNumber2 < (olympicNumber1 - RANGE) || olympicNumber2 > (olympicNumber1 + RANGE)) {
            errors.rejectValue("olympicNumber1", "validator.validOlympicNumber2.range", new Object[]{RANGE}, "Invalid Olympic number");
            errors.rejectValue("olympicNumber2", "validator.validOlympicNumber2.range", new Object[]{RANGE}, "Invalid Olympic number");
        }

        if (firstNumber == lastNumber) {
            errors.rejectValue("olympicNumber1", "validator.validOlympicNumber1.firstLastDifferent", "validator.validOlympicNumber1.default");
        }

        if (gameService.gameWithOlympicNumber1Exists(olympicNumber1)) {
            errors.rejectValue("olympicNumber1", "validator.validOlympicNumber1.alreadyExists", "validator.validOlympicNumber1.default");
        }
    }
}
