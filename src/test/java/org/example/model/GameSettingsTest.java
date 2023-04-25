package org.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link GameSettings} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link GameSettings} where the
 * parameters given are valid.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link GameSettings} where the
 * communication rounds given is {@code null}.</li>
 * <li>Test for creating instances of {@link GameSettings} where the {@code int}
 * parameters given are invalid.</li>
 * </ul>
 */
public class GameSettingsTest {
    /**
     * Positive test where the {@link GameSettings} class is able to handle creation
     * of instance where the number of players, number of rounds, round time,
     * communication rounds, minimum shrimp and maximum shrimp provided are valid.
     * <p>
     * Tests that a new instance of a {@code GameSettings} is successfully
     * initialized and has valid field values.
     */
    @Test
    public void testCreateInstanceOfGameSettingsWithValidParameters() {
        int numberOfPlayers = 3;
        int numberOfRounds = 8;
        int roundTime = 120;
        String communicationRounds = "4,6";
        int communicationRoundTime = 600;
        int minShrimpKilograms = 0;
        int maxShrimpKilograms = 75;

        GameSettings gameSettings = new GameSettings(numberOfPlayers, numberOfRounds, roundTime, communicationRounds,
                communicationRoundTime, minShrimpKilograms, maxShrimpKilograms);

        assertEquals(numberOfPlayers, gameSettings.getNumberOfPlayers());
        assertEquals(numberOfRounds, gameSettings.getNumberOfRounds());
        assertEquals(roundTime, gameSettings.getRoundTime());
        assertEquals(communicationRounds, gameSettings.getCommunicationRounds());
        assertEquals(communicationRoundTime, gameSettings.getCommunicationRoundTime());
        assertEquals(minShrimpKilograms, gameSettings.getMinShrimpKilograms());
        assertEquals(maxShrimpKilograms, gameSettings.getMaxShrimpKilograms());
    }

    /**
     * Negative test where the {@link GameSettings} class is able to handle creation
     * of instance where the communication rounds provided is {@code null}.
     * <p>
     * Tests that the {@code GameSettings} class with {@code null} as communication
     * rounds throws an {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfGameSettingsWithNullCommunicationRounds() {
        String communicationRounds = null;

        assertThrows(IllegalArgumentException.class,
                () -> new GameSettings(3, 8, 120, communicationRounds, 600, 0, 75));
    }

    /**
     * Negative test where the {@link GameSettings} class is able to handle creation
     * of instances where the {@code int} parameters provided are invalid.
     * <p>
     * Tests that the {@code GameSettings} class with invalid {@code int} parameters
     * throws {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfGameSettingsWithInvalidIntParameters() {
        int numberOfPlayers = 3;
        int numberOfRounds = 8;
        int roundTime = 120;
        String communicationRounds = "4,6";
        int communicationRoundTime = 600;
        int minShrimpKilograms = 0;
        int maxShrimpKilograms = 75;

        assertThrows(IllegalArgumentException.class, () -> new GameSettings(-1, numberOfRounds, roundTime,
                communicationRounds, communicationRoundTime, minShrimpKilograms, maxShrimpKilograms));
        assertThrows(IllegalArgumentException.class, () -> new GameSettings(numberOfPlayers, -1, roundTime,
                communicationRounds, communicationRoundTime, minShrimpKilograms, maxShrimpKilograms));
        assertThrows(IllegalArgumentException.class, () -> new GameSettings(numberOfPlayers, numberOfRounds, -1,
                communicationRounds, communicationRoundTime, minShrimpKilograms, maxShrimpKilograms));
        assertThrows(IllegalArgumentException.class, () -> new GameSettings(numberOfPlayers, numberOfRounds, roundTime,
                communicationRounds, -1, minShrimpKilograms, maxShrimpKilograms));
        assertThrows(IllegalArgumentException.class, () -> new GameSettings(numberOfPlayers, numberOfRounds, roundTime,
                communicationRounds, communicationRoundTime, -1, maxShrimpKilograms));
        assertThrows(IllegalArgumentException.class, () -> new GameSettings(numberOfPlayers, numberOfRounds, roundTime,
                communicationRounds, communicationRoundTime, minShrimpKilograms, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new GameSettings(-1, -1, -1, communicationRounds, -1, -1, -1));
    }
}
