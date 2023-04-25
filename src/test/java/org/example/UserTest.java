package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.model.User;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link User} class.
 * <p>
 * The following <b>positive</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link User} where the parameters given
 * are valid.</li>
 * </ul>
 * <p>
 * The following <b>negative</b> tests are performed:
 * <ul>
 * <li>Test for creating an instance of {@link User} where the name given is
 * {@code null}.</li>
 * </ul>
 */
public class UserTest {
    /**
     * Positive test where the {@link User} class is able to handle creation of
     * instance where the name and admin status provided are valid.
     * <p>
     * Tests that a new instance of a {@code User} is successfully initialized and
     * has valid field values.
     */
    @Test
    public void testCreateInstanceOfUserWithValidParameters() {
        String name = "Atari";
        boolean isAdmin = false;
        User user = new User(name, isAdmin);

        assertEquals(name, user.getName());
        assertFalse(user.isAdmin());
    }

    /**
     * Negative test where the {@link User} class is able to handle creation of
     * instance where the name provided is {@code null}.
     * <p>
     * Tests that the {@code User} class with {@code null} as parameter throws an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateInstanceOfUserWithNullName() {
        String name = null;
        boolean isAdmin = false;

        assertThrows(IllegalArgumentException.class, () -> new User(name, isAdmin));
    }
}
