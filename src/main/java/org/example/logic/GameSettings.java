package org.example.logic;

/**
 * The GameSettings class represents the settings of a game.
 * <p>
 * It contains information such as the number of players, number of rounds,
 * <p>
 * round time, minimum shrimp pounds to catch, and maximum shrimp pounds to catch.
 */
public class GameSettings
{
    private int numberOfPlayers;
    private int numberOfRounds;
    private int roundTime;
    private int minShrimpPounds;
    private int maxShrimpPounds;

    /**
     * Constructor for the GameSettings class.
     *
     * @param numberOfPlayers the number of players in the game.
     * @param numberOfRounds  the number of rounds in the game.
     * @param roundTime       the time (in seconds) for each round.
     * @param minShrimpPounds the minimum amount of shrimp pounds that can be caught in a round.
     * @param maxShrimpPounds the maximum amount of shrimp pounds that can be caught in a round.
     */
    public GameSettings(int numberOfPlayers, int numberOfRounds, int roundTime, int minShrimpPounds,
                        int maxShrimpPounds)
    {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.roundTime = roundTime;
        this.minShrimpPounds = minShrimpPounds;
        this.maxShrimpPounds = maxShrimpPounds;
    }

    /**
     * Getter for the number of players in the game.
     *
     * @return the number of players in the game.
     */
    public int getNumberOfPlayers()
    {
        return this.numberOfPlayers;
    }

    /**
     * Setter for the number of players in the game.
     *
     * @param numberOfPlayers the number of players in the game.
     */
    public void setNumberOfPlayers(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Getter for the number of rounds in the game.
     *
     * @return the number of rounds in the game.
     */
    public int getNumberOfRounds()
    {
        return this.numberOfRounds;
    }

    /**
     * Setter for the number of rounds in the game.
     *
     * @param numberOfRounds the number of rounds in the game.
     */
    public void setNumberOfRounds(int numberOfRounds)
    {
        this.numberOfRounds = numberOfRounds;
    }

    /**
     * Getter for the time (in seconds) for each round.
     *
     * @return the time (in seconds) for each round.
     */
    public int getRoundTime()
    {
        return this.roundTime;
    }

    /**
     * Setter for the time (in seconds) for each round.
     *
     * @param roundTime the time (in seconds) for each round.
     */
    public void setRoundTime(int roundTime)
    {
        this.roundTime = roundTime;
    }

    /**
     * Getter for the minimum amount of shrimp pounds that can be caught in a round.
     *
     * @return the minimum amount of shrimp pounds that can be caught in a round.
     */
    public int getMinShrimpPounds()
    {
        return this.minShrimpPounds;
    }

    /**
     * Setter for the minimum amount of shrimp pounds that can be caught in a round.
     *
     * @param minShrimpPounds the minimum amount of shrimp pounds that can be caught in a round.
     */
    public void setMinShrimpPounds(int minShrimpPounds)
    {
        this.minShrimpPounds = minShrimpPounds;
    }

    /**
     * Getter for the maximum amount of shrimp pounds that can be caught in a round.
     *
     * @return the maximum amount of shrimp pounds that can be caught in a round.
     */
    public int getMaxShrimpPounds()
    {
        return this.maxShrimpPounds;
    }

    /**
     * Sets the maximum number of shrimp pounds that can be caught in a round.
     *
     * @param maxShrimpPounds the maximum number of shrimp pounds that can be caught in a round.
     */
    public void setMaxShrimpPounds(int maxShrimpPounds)
    {
        this.maxShrimpPounds = maxShrimpPounds;
    }
}
