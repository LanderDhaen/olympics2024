package domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TicketForm {

    @NotNull(message = "{validator.emptyInput}")
    @Min(value = 1, message = "{validator.validAmount.min}")
    private int amount;

    private Game game;

    private Spectator spectator;

}

