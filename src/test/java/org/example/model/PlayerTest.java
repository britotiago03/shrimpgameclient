package org.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Player} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Player} where the parameters
 * given are valid.</li>
 * <li>Test for setting and getting previous total money of a valid instance of
 * {@link Player}.</li>
 * <li>Test for setting and getting current total money of a valid instance of
 * {@link Player}.</li>
 * <li>Test for setting and getting round profit of a valid instance of
 * {@link Player}.</li>
 * <li>Test for setting and getting expenses of a valid instance of
 * {@link Player}.</li>
 * <li>Test for setting and getting shrimp caught of a valid instance of
 * {@link Player}.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Player} where the name given is
 * {@code null}.</li>
 * </ul>
 */
public class PlayerTest {
    /**
     * Returns a valid instance of player.
     * 
     * @return a valid instance of player
     */
    private Player createValidPlayerInstance() {
        String name = "Atari";
        int expenses = 5;
        Player player = new Player(name, expenses);

        return player;
    }

    /**
     * Positive test where the {@link Player} class is able to handle creation of
     * instance where the name and expenses provided are valid.
     * <p>
     * Tests that a new instance of a {@code Player} is successfully initialized and
     * has valid field values.
     */
    @Test
    public void testCreateInstanceOfPlayerWithValidParameters() {
        String name = "Atari";
        int expenses = 5;
        Player player = new Player(name, expenses);

        assertEquals(name, player.getName());
        assertEquals(expenses, player.getExpenses());
        assertEquals(0, player.getCurrentTotalMoney());
        assertEquals(0, player.getPreviousTotalMoney());
        assertEquals(0, player.getRoundProfit());
        assertEquals(-1, player.getShrimpCaught());
    }

    /**
     * Negative test where the {@link Player} class is able to handle creation of
     * instance where the name provided is {@code null}.
     * <p>
     * Tests that the {@code Player} class with {@code null} as name throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfPlayerWithNullName() {
        int expenses = 5;

        assertThrows(IllegalArgumentException.class, () -> new Player(null, expenses));
    }

    /**
     * Positive test where a valid instance of the {@link Player} class is able to
     * handle setting and getting previous total money.
     * <p>
     * Tests that a previous total money value can be set and gotten from a valid
     * {@code Player} instance.
     */
    @Test
    public void testSettingAndGettingPreviousTotalMoney() {
        Player player = createValidPlayerInstance();

        int previousTotalMoney = 700;
        player.setPreviousTotalMoney(previousTotalMoney);

        assertEquals(previousTotalMoney, player.getPreviousTotalMoney());
    }

    /**
     * Positive test where a valid instance of the {@link Player} class is able to
     * handle setting and getting current total money.
     * <p>
     * Tests that a current total money value can be set and gotten from a valid
     * {@code Player} instance.
     */
    @Test
    public void testSettingAndGettingCurrentTotalMoney() {
        Player player = createValidPlayerInstance();

        int currentTotalMoney = 700;
        player.setCurrentTotalMoney(currentTotalMoney);

        assertEquals(currentTotalMoney, player.getCurrentTotalMoney());
    }

    /**
     * Positive test where a valid instance of the {@link Player} class is able to
     * handle setting and getting round profit.
     * <p>
     * Tests that a round profit value can be set and gotten from a valid
     * {@code Player} instance.
     */
    @Test
    public void testSettingAndGettingRoundProfit() {
        Player player = createValidPlayerInstance();

        int roundProfit = 700;
        player.setRoundProfit(roundProfit);

        assertEquals(roundProfit, player.getRoundProfit());
    }

    /**
     * Positive test where a valid instance of the {@link Player} class is able to
     * handle setting and getting expenses.
     * <p>
     * Tests that a expenses value can be set and gotten from a valid
     * {@code Player} instance.
     */
    @Test
    public void testSettingAndGettingExpenses() {
        Player player = createValidPlayerInstance();

        int expenses = 10;
        player.setExpenses(expenses);

        assertEquals(expenses, player.getExpenses());
    }

    /**
     * Positive test where a valid instance of the {@link Player} class is able to
     * handle setting and getting shrimp caught.
     * <p>
     * Tests that a shrimp caught value can be set and gotten from a valid
     * {@code Player} instance.
     */
    @Test
    public void testSettingAndGettingShrimpCaught() {
        Player player = createValidPlayerInstance();

        int shrimpCaught = 50;
        player.setShrimpCaught(shrimpCaught);

        assertEquals(shrimpCaught, player.getShrimpCaught());
    }
}
