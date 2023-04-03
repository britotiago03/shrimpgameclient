package org.example.logic;

import java.util.ArrayList;
import java.util.List;
import org.example.User;

/**
 * The Round class represents a single round in the game, including the round number, the player
 * actions for the round, and whether communication is enabled.
 */
public class Round
{

    private int roundNumber;
    private Game game;

    /**
     * Constructs a new Round object with the given round number
     *
     * @param roundNumber the number of the round
     */
    public Round(Game game, int roundNumber)
    {
        this.game = game;
        this.roundNumber = roundNumber;
    }

    /**
     * Returns the round number of this Round object
     *
     * @return the round number of this Round object
     */
    public int getRoundNumber()
    {
        return this.roundNumber;
    }

    /**
     * Sets the round number of this Round object
     *
     * @param roundNumber the new round number
     */
    public void setRoundNumber(int roundNumber)
    {
        this.roundNumber = roundNumber;
    }
}
