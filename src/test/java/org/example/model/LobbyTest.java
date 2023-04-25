package org.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Lobby} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Lobby} where the parameters given
 * are valid.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link Lobby} where the name given is
 * {@code null}.</li>
 * <li>Test for creating an instance of {@link Lobby} where the {@code int}
 * values given are invalid.</li>
 * </ul>
 */
public class LobbyTest {
    /**
     * Positive test where the {@link Lobby} class is able to handle creation of
     * instance where the lobby name, number of players and max players provided are
     * valid.
     * <p>
     * Tests that a new instance of a {@code Lobby} is successfully initialized and
     * has valid field values.
     */
    @Test
    public void testCreateInstanceOfLobbyWithValidParameters() {
        String lobbyName = "Lobby 1";
        int numPlayers = 0;
        int maxPlayers = 3;
        Lobby lobby = new Lobby(lobbyName, numPlayers, maxPlayers);

        assertEquals(lobbyName, lobby.getLobbyName());
        assertEquals(numPlayers, lobby.getNumPlayers());
        assertEquals(maxPlayers, lobby.getMaxPlayers());
    }

    /**
     * Negative test where the {@link Lobby} class is able to handle creation of
     * instance where the name provided is {@code null}.
     * <p>
     * Tests that the {@code Lobby} class with {@code null} as name throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfLobbyWithNullName() {
        String lobbyName = null;
        int numPlayers = 0;
        int maxPlayers = 3;

        assertThrows(IllegalArgumentException.class, () -> new Lobby(lobbyName, numPlayers, maxPlayers));
    }

    /**
     * Negative test where the {@link Lobby} class is able to handle creation of
     * instance where the {@code int} parameters given are invalid.
     * <p>
     * Tests that the {@code Lobby} class with invalid {@code int} values throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfLobbyWithInvalidIntParameters() {
        String lobbyName = "Lobby 1";
        int numPlayers = 0;
        int maxPlayers = 3;

        assertThrows(IllegalArgumentException.class, () -> new Lobby(lobbyName, -1, maxPlayers));
        assertThrows(IllegalArgumentException.class, () -> new Lobby(lobbyName, numPlayers, -1));
        assertThrows(IllegalArgumentException.class, () -> new Lobby(lobbyName, -1, -1));
    }
}
