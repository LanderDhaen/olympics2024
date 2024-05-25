package com.springboot.olympics2024;

import com.springboot.olympics2024.config.SecurityConfig;
import com.springboot.olympics2024.controllers.BuyController;
import com.springboot.olympics2024.controllers.GameController;
import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import service.GameService;
import service.SportService;
import service.StadiumService;
import service.TicketService;
import util.Role;
import validator.OlympicNumberValidator;
import validator.RemainingSeatsValidator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@Import(SecurityConfig.class)
public class GameMockTest {

    @Mock
    private GameService gameService;

    @Mock
    private OlympicNumberValidator olympicNumberValidator;

    @Mock
    private RemainingSeatsValidator remainingSeatsValidator;

    @Mock
    private SportService sportService;

    @Mock
    private StadiumService stadiumService;

    private GameController gameController;
    private MockMvc mockMvc;

    private final Sport sport = new Sport("basketball");
    private final Stadium stadium = new Stadium(1L, "Place de la Concorde 1", 30);
    private final Discipline discipline = new Discipline("3x3", true, sport);

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController();
        mockMvc = standaloneSetup(gameController).build();
        ReflectionTestUtils.setField(gameController, "gameService", gameService);
        ReflectionTestUtils.setField(gameController, "olympicNumberValidator", olympicNumberValidator);
        ReflectionTestUtils.setField(gameController, "remainingSeatsValidator", remainingSeatsValidator);
        ReflectionTestUtils.setField(gameController, "sportService", sportService);
        ReflectionTestUtils.setField(gameController, "stadiumService", stadiumService);
    }

    @Test
    public void testAddGame_correctValue() throws Exception {

        when(sportService.findByName(sport.getName())).thenReturn(sport);
        when(stadiumService.findAllStadiums()).thenReturn(List.of(stadium));

        Game game = new Game();
        game.setStadium(stadium);

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/create")
                        .param("date", "2024-07-31T18:35")
                        .param("olympicNumber1", "55556")
                        .param("olympicNumber2", "55540")
                        .param("remainingSeats", "5")
                        .param("ticketPrice", "99.99")
                        .flashAttr("game", game)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/olympics2024/sports/" + sport.getName()));
    }


    @Test
    public void testAddGame_invalidDate() throws Exception {

        when(sportService.findByName(sport.getName())).thenReturn(sport);
        when(stadiumService.findAllStadiums()).thenReturn(List.of(stadium));

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/create")
                        .param("date", ""))
                .andExpect(view().name("gameform"))
                .andExpect(model().attributeHasFieldErrors("game", "date"));

    }

    @Test
    public void testAddGame_invalidOlympicNumber1() throws Exception {

        when(sportService.findByName(sport.getName())).thenReturn(sport);
        when(stadiumService.findAllStadiums()).thenReturn(List.of(stadium));

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/create")
                        .param("olympicNumber1", ""))
                .andExpect(view().name("gameform"))
                .andExpect(model().attributeHasFieldErrors("game", "olympicNumber1"));

    }

    @Test
    public void testAddGame_invalidOlympicNumber2() throws Exception {

        when(sportService.findByName(sport.getName())).thenReturn(sport);
        when(stadiumService.findAllStadiums()).thenReturn(List.of(stadium));

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/create")
                        .param("olympicNumber2", ""))
                .andExpect(view().name("gameform"))
                .andExpect(model().attributeHasFieldErrors("game", "olympicNumber2"));

    }

    @Test
    public void testAddGame_invalidTicketPrice() throws Exception {

        when(sportService.findByName(sport.getName())).thenReturn(sport);
        when(stadiumService.findAllStadiums()).thenReturn(List.of(stadium));

        mockMvc.perform(post("/olympics2024/sports/" + sport.getName() + "/games/create")
                        .param("ticketPrice", ""))
                .andExpect(view().name("gameform"))
                .andExpect(model().attributeHasFieldErrors("game", "ticketPrice"));

    }
}
