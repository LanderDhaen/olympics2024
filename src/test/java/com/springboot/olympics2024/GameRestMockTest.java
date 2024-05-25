package com.springboot.olympics2024;

import com.springboot.olympics2024.controllers.GameRestController;
import domain.Discipline;
import domain.Game;
import domain.Sport;
import domain.Stadium;
import exceptions.GameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import service.GameService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static util.DateFormat.*;

@SpringBootTest
public class GameRestMockTest {

    @Mock
    private GameService gameService;

    private GameRestController gameRestController;
    private MockMvc mockMvc;

    private final Sport sport = new Sport("basketball");
    private final Stadium stadium = new Stadium("Place de la Concorde 1", 30);
    private final Discipline discipline = new Discipline("3x3", true, sport);
    private String expectedFormattedDateTime;
    private double expectedTicketPrice;
    private int expectedRemainingSeats;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        gameRestController  = new GameRestController();
        mockMvc = standaloneSetup(gameRestController).build();
        ReflectionTestUtils.setField(gameRestController, "gameService", gameService);
    }

    private Game aGame(int olympicNumber1, int olympicNumber2, int remainingSeats, double ticketPrice) {

        Game game = new Game(LocalDateTime.of(2024, Month.JULY, 31, 18, 35), olympicNumber1, olympicNumber2, remainingSeats, ticketPrice, List.of(discipline), stadium, sport);
        expectedFormattedDateTime = game.getDate().format(FORMATTER);
        expectedTicketPrice = game.getTicketPrice();
        expectedRemainingSeats = game.getRemainingSeats();

        return game;
    }

    private void performRestGetGames(String uri) throws Exception {
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket_price").value(expectedTicketPrice))
                .andExpect(jsonPath("$.date").value(expectedFormattedDateTime));
    }

    private void performRestGetSeats(String uri) throws Exception {
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedRemainingSeats)));
    }


    @Test
    public void testGetSeats_isOk() throws Exception {
        Long id = 1L;
        Mockito.when(gameService.findRemainingSeatsByGame(id)).thenReturn(aGame(55555, 55540, 5, 99.99).getRemainingSeats());
        performRestGetSeats("/rest/games/" + id + "/seats");
        Mockito.verify(gameService).findRemainingSeatsByGame(id);
    }

    @Test
    public void testGetSeats_notFound() throws Exception {
        Long id = 50L;
        Mockito.when(gameService.findRemainingSeatsByGame(id)).thenThrow(new GameNotFoundException("This game doesn't exist"));
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/rest/games/" + id + "/seats")).andReturn();
        });

        assertTrue(exception.getCause() instanceof GameNotFoundException);

        Mockito.verify(gameService).findRemainingSeatsByGame(id);

    }

    @Test
    public void testGetAllGamesBySport_emptyList() throws Exception {
        Long id = 1L;
        Mockito.when(gameService.findGamesBySport(id)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/sports/" + id + "/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(gameService).findGamesBySport(id);
    }

    @Test
    public void testGetAllGamesBySport_noEmptyList() throws Exception {
        Long id = 1L;
        Game game1 = aGame(55555, 55540, 5, 99.99);
        String expectedFormattedDateTime1 = expectedFormattedDateTime;
        double expectedTicketPrice1 = expectedTicketPrice;
        Game game2 = aGame(23456, 23345, 20, 49.99);
        String expectedFormattedDateTime2 = expectedFormattedDateTime;
        double expectedTicketPrice2 = expectedTicketPrice;
        List<Game> listGame = List.of(game1, game2);
        Mockito.when(gameService.findGamesBySport(id)).thenReturn(listGame);

        mockMvc.perform(get("/rest/sports/" + id + "/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].ticketPrice").value(expectedTicketPrice1))
                .andExpect(jsonPath("$[0].date").value(expectedFormattedDateTime1))
                .andExpect(jsonPath("$[1].ticketPrice").value(expectedTicketPrice2))
                .andExpect(jsonPath("$[1].date").value(expectedFormattedDateTime2));

        Mockito.verify(gameService).findGamesBySport(id);
    }




}
