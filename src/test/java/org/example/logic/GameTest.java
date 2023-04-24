package org.example.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Game} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Game} where the parameters given
 * are valid.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Game} where the name, settings
 * and players given are {@code null}.</li>
 * </ul>
 */
public class GameTest {
    /**
     * Positive test where the {@link Game} class is able to handle creation of
     * instance where the name, game settings, players and number provided are
     * valid.
     * <p>
     * Tests that a new instance of a {@code Game} is successfully initialized and
     * has valid field values.
     */
    @Test
    public void testCreateInstanceOfGameWithValidParameters() {
        String name = "game 1";
        GameSettings gameSettings = new GameSettings(3, 8, 120, "4,6", 600, 0, 75);
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Atari", 5);
        Player player2 = new Player("BMI", 5);
        Player player3 = new Player("Commodore", 5);
        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        players.put(player3.getName(), player3);
        int number = 1;
        Game game = new Game(name, gameSettings, players, number);

        assertEquals(name, game.getName());
        assertEquals(gameSettings, game.getSettings());
        assertEquals(players, game.getPlayers());
        assertEquals(number, game.getNumber());
        assertEquals(1, game.getCurrentRoundNum());
        assertTrue(game.getMessages().isEmpty());
        assertTrue(game.getRounds().isEmpty());
    }

    /**
     * Negative test where the {@link Game} class is able to handle creation of
     * instance where the name, settings and players provided are {@code null}.
     * <p>
     * Tests that the {@code Game} class with {@code null} as parameters throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfGameWithNullParameters() {
        String name = "game 1";
        GameSettings gameSettings = new GameSettings(3, 8, 120, "4,6", 600, 0, 75);
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Atari", 5);
        Player player2 = new Player("BMI", 5);
        Player player3 = new Player("Commodore", 5);
        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        players.put(player3.getName(), player3);
        int number = 1;

        assertThrows(IllegalArgumentException.class, () -> new Game(null, gameSettings, players, number));
        assertThrows(IllegalArgumentException.class, () -> new Game(name, null, players, number));
        assertThrows(IllegalArgumentException.class, () -> new Game(name, gameSettings, null, number));
        assertThrows(IllegalArgumentException.class, () -> new Game(null, null, null, number));
    }
}
