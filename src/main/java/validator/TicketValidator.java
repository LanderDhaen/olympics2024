package validator;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import domain.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TicketValidator implements Validator {

    private static final int MAX_GAME = 20;
    private static final int MAX_TOTAL = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return TicketForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TicketForm form = (TicketForm) target;

        int amount = form.getAmount();

        Game game = form.getGame();
        Spectator spectator = form.getSpectator();

        int ticketsForGame = 0;

        for (Ticket ticket : spectator.getTickets()) {
            if (ticket.getGame().getId().equals(game.getId())) {
                ticketsForGame++;
            }
        }

        int totalTickets = spectator.getTickets().size();

        if (ticketsForGame + amount > MAX_GAME) {
            errors.rejectValue("amount", "validator.validTickets.maxGame", new Object[]{MAX_GAME}, "validator.validTickets.default");
        }

        if (totalTickets + amount > MAX_TOTAL) {
            errors.rejectValue("amount", "validator.validTickets.maxTickets", new Object[]{MAX_TOTAL}, "validator.validTickets.default");
        }

        if (amount > game.getRemainingSeats()) {
            errors.rejectValue("amount", "validator.validTickets.remainingSeats", new Object[]{game.getRemainingSeats()}, "validator.validTickets.default");
        }
    }
}
