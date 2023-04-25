package org.example.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Round} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Round} where the parameters given
 * are valid.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instances of {@link Round} where the parameters
 * given are invalid.</li>
 * </ul>
 */
public class RoundTest {
    /**
     * Positive test where the {@link Round} class is able to handle creation
     * of instance where the round number, shrimp price, map of shrimp caught per
     * player and map of money per player provided are valid.
     * <p>
     * Tests that a new instance of a {@code Round} is successfully
     * initialized and has valid field values.
     */
    @Test
    public void testCreateInstanceOfRoundWithValidParameters() {
        Player player1 = new Player("Atari", 5);
        Player player2 = new Player("BMI", 5);
        Player player3 = new Player("Commodore", 5);
        Map<String, Player> players = new HashMap<>();
        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        players.put(player3.getName(), player3);

        int shrimpCaughtAmount1 = 30;
        int shrimpCaughtAmount2 = 50;
        int shrimpCaughtAmount3 = 20;
        Map<Player, Integer> playerShrimpCaughtMap = new HashMap<>();
        playerShrimpCaughtMap.put(player1, shrimpCaughtAmount1);
        playerShrimpCaughtMap.put(player2, shrimpCaughtAmount2);
        playerShrimpCaughtMap.put(player3, shrimpCaughtAmount3);

        int playerMoney1 = 300;
        int playerMoney2 = 700;
        int playerMoney3 = 260;
        Map<Player, Integer> playerMoneyMap = new HashMap<>();
        playerMoneyMap.put(player1, playerMoney1);
        playerMoneyMap.put(player1, playerMoney2);
        playerMoneyMap.put(player1, playerMoney3);

        int number = 1;
        int shrimpPrice = 15;

        Round round = new Round(number, shrimpPrice, playerShrimpCaughtMap, playerMoneyMap);

        assertEquals("Round " + number, round.getName());
        assertEquals(number, round.getNumber());
        assertEquals(shrimpPrice, round.getShrimpPrice());
        assertEquals(playerShrimpCaughtMap, round.getPlayerShrimpCaughtMap());
        assertEquals(playerMoneyMap, round.getPlayerMoneyMap());
        assertEquals(players, round.getPlayers());
        assertEquals(shrimpCaughtAmount1 + shrimpCaughtAmount2 + shrimpCaughtAmount3, round.getTotalAmountOfShrimp());
    }

    /**
     * Negative test where the {@link Round} class is able to handle creation of
     * instances where the parameters are invalid.
     * <p>
     * Tests that the {@code Round} class with invalid parameters throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfRoundWithInvalidParameters() {
        Player player1 = new Player("Atari", 5);
        Player player2 = new Player("BMI", 5);
        Player player3 = new Player("Commodore", 5);
        Map<String, Player> players = new HashMap<>();
        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        players.put(player3.getName(), player3);

        int shrimpCaughtAmount1 = 30;
        int shrimpCaughtAmount2 = 50;
        int shrimpCaughtAmount3 = 20;
        Map<Player, Integer> playerShrimpCaughtMap = new HashMap<>();
        playerShrimpCaughtMap.put(player1, shrimpCaughtAmount1);
        playerShrimpCaughtMap.put(player2, shrimpCaughtAmount2);
        playerShrimpCaughtMap.put(player3, shrimpCaughtAmount3);

        int playerMoney1 = 300;
        int playerMoney2 = 700;
        int playerMoney3 = 260;
        Map<Player, Integer> playerMoneyMap = new HashMap<>();
        playerMoneyMap.put(player1, playerMoney1);
        playerMoneyMap.put(player1, playerMoney2);
        playerMoneyMap.put(player1, playerMoney3);

        int number = 1;
        int shrimpPrice = 15;

        assertThrows(IllegalArgumentException.class,
                () -> new Round(-1, shrimpPrice, playerShrimpCaughtMap, playerMoneyMap));
        assertThrows(IllegalArgumentException.class,
                () -> new Round(number, -1, playerShrimpCaughtMap, playerMoneyMap));
        assertThrows(IllegalArgumentException.class, () -> new Round(number, shrimpPrice, null, playerMoneyMap));
        assertThrows(IllegalArgumentException.class, () -> new Round(number, shrimpPrice, playerShrimpCaughtMap, null));
        assertThrows(IllegalArgumentException.class, () -> new Round(-1, -1, null, null));
    }
}
