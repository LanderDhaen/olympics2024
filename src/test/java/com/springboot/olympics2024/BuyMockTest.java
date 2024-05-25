package com.springboot.olympics2024;

import com.springboot.olympics2024.controllers.BuyController;
import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import service.GameService;
import service.SpectatorService;
import service.TicketService;
import util.Role;
import validator.TicketValidator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class BuyMockTest {

    @Mock
    private GameService gameService;

    @Mock
    private SpectatorService spectatorService;

    @Mock
    private TicketValidator ticketValidator;

    @Mock
    private TicketService ticketService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private Authentication authentication;

    private BuyController buyController;
    private MockMvc mockMvc;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    private Spectator spectator = new Spectator("User", encoder.encode("12345678"), Role.USER);
    private final Sport sport = new Sport("basketball");
    private final Stadium stadium = new Stadium("Place de la Concorde 1", 30);
    private final Discipline discipline = new Discipline("3x3", true, sport);
    private Game game = new Game(1L, LocalDateTime.of(2024, Month.JULY, 31, 18, 35), 55555, 55540, 5, 99.99, List.of(discipline), stadium, sport);

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        buyController = new BuyController();
        mockMvc = standaloneSetup(buyController).build();
        ReflectionTestUtils.setField(buyController, "gameService", gameService);
        ReflectionTestUtils.setField(buyController, "spectatorService", spectatorService);
        ReflectionTestUtils.setField(buyController, "ticketValidator", ticketValidator);
        ReflectionTestUtils.setField(buyController, "ticketService", ticketService);
        ReflectionTestUtils.setField(buyController, "messageSource", messageSource);
    }

    @Test
    public void testBuyTickets_correctValue() throws Exception {
        when(authentication.getName()).thenReturn("User");
        when(spectatorService.findByUsername("User")).thenReturn(spectator);
        when(gameService.findById(1L)).thenReturn(Optional.of(game));
        when(messageSource.getMessage("ticket.success", new Object[]{2}, Locale.getDefault())).thenReturn("Ticket purchase successful!");

        TicketForm ticketForm = new TicketForm();
        ticketForm.setGame(game);
        ticketForm.setSpectator(spectator);
        ticketForm.setAmount(2);

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/" + game.getId() + "/buy")
                        .param("amount", "2")
                        .flashAttr("ticketForm", ticketForm)
                        .principal(authentication))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/olympics2024/sports/" + sport.getName()));

        Mockito.verify(ticketService).buyTickets(spectator, game, ticketForm.getAmount());
    }
}
