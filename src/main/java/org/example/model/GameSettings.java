package org.example.model;

/**
 * The GameSettings class represents the settings of a game.
 * <p>
 * It contains information such as the number of players, number of rounds, round time, 
 * communication rounds, communication round time minimum shrimp kilograms to catch, and 
 * maximum shrimp kilograms to catch.
 */
public class GameSettings {
  private int numberOfPlayers;
  private int numberOfRounds;
  private int roundTime;
  private String communicationRounds;
  private int communicationRoundTime;
  private int minShrimpKilograms;
  private int maxShrimpKilograms;

  /**
   * Constructor for the GameSettings class.
   *
   * @param numberOfPlayers the number of players in the game.
   * @param numberOfRounds  the number of rounds in the game.
   * @param roundTime       the time (in seconds) for each round.
   * @param minShrimpKilograms the minimum amount of shrimp kilograms that can be caught in a round.
   * @param maxShrimpKilograms the maximum amount of shrimp kilograms that can be caught in a round.
   * @throws IllegalArgumentException if any of the provided parameters are invalid.
   */
  public GameSettings(int numberOfPlayers, int numberOfRounds, int roundTime,
                      String communicationRounds, int communicationRoundTime, int minShrimpKilograms,
                      int maxShrimpKilograms) {
    this.setNumberOfPlayers(numberOfPlayers);
    this.setNumberOfRounds(numberOfRounds);
    this.setRoundTime(roundTime);
    this.setCommunicationRounds(communicationRounds);
    this.setCommunicationRoundTime(communicationRoundTime);
    this.setMinShrimpKilograms(minShrimpKilograms);
    this.setMaxShrimpKilograms(maxShrimpKilograms);
  }

  /**
   * Getter for the number of players in the game.
   *
   * @return the number of players in the game.
   */
  public int getNumberOfPlayers() {
    return this.numberOfPlayers;
  }

  /**
   * Setter for the number of players in the game.
   *
   * @param numberOfPlayers the number of players in the game. Must be a value of {@code 0} or greater.
   * @throws IllegalArgumentException if the number of players provided is less than {@code 0}.
   */
  private void setNumberOfPlayers(int numberOfPlayers) throws IllegalArgumentException {
    if (numberOfPlayers < 0) {
      throw new IllegalArgumentException("number of players cannot be less than 0");
    }
    this.numberOfPlayers = numberOfPlayers;
  }

  /**
   * Getter for the number of rounds in the game.
   *
   * @return the number of rounds in the game.
   */
  public int getNumberOfRounds() {
    return this.numberOfRounds;
  }

  /**
   * Setter for the number of rounds in the game.
   *
   * @param numberOfRounds the number of rounds in the game. Must be a value of {@code 1} or greater.
   * @throws IllegalArgumentException if the number of rounds provided is less than {@code 1}. 
   */
  private void setNumberOfRounds(int numberOfRounds) throws IllegalArgumentException {
    if (numberOfRounds < 1) {
      throw new IllegalArgumentException("number of rounds cannot be less than 1");
    }
    this.numberOfRounds = numberOfRounds;
  }

  /**
   * Getter for the time (in seconds) for each round.
   *
   * @return the time (in seconds) for each round.
   */
  public int getRoundTime() {
    return this.roundTime;
  }

  /**
   * Setter for the time (in seconds) for each round.
   *
   * @param roundTime the time (in seconds) for each round.
   * @throws IllegalArgumentException if the round time provided is less than {@code 0}.
   */
  private void setRoundTime(int roundTime) throws IllegalArgumentException {
    if (roundTime < 0) {
      throw new IllegalArgumentException("round time cannot be less than 0");
    }
    this.roundTime = roundTime;
  }

  /**
   * Gets the communication rounds.
   * 
   * @return the communication rounds.
   */
  public String getCommunicationRounds()
  {
    return this.communicationRounds;
  }

  /**
   * Sets the communication rounds.
   * 
   * @param communicationRounds the communication rounds.
   * @throws IllegalArgumentException if the communication rounds provided is set to {@code null}.
   */
  private void setCommunicationRounds(String communicationRounds) throws IllegalArgumentException
  {
    if (communicationRounds   == null) {
      throw new IllegalArgumentException("communication rounds cannot be set to null");
    }
    this.communicationRounds = communicationRounds;
  }

  /**
   * Gets the communication round time.
   * 
   * @return the time (in seconds) for communication rounds.
   */
  public int getCommunicationRoundTime()
  {
    return this.communicationRoundTime;
  }

  /**
   * Sets the communication round time.
   * 
   * @param communicationRoundTime the time (in seconds) for communication rounds.
   * @throws IllegalArgumentException if the time provided is less than {@code 0}.
   */
  public void setCommunicationRoundTime(int communicationRoundTime) throws IllegalArgumentException
  {
    if (communicationRoundTime < 0) {
      throw new IllegalArgumentException("communication round time cannot be less than 0");
    }
    this.communicationRoundTime = communicationRoundTime;
  }

  /**
   * Getter for the minimum amount of shrimp kilograms that can be caught in a round.
   *
   * @return the minimum amount of shrimp kilograms that can be caught in a round.
   */
  public int getMinShrimpKilograms() {
    return this.minShrimpKilograms;
  }

  /**
   * Setter for the minimum amount of shrimp kilograms that can be caught in a round.
   *
   * @param minShrimpKilograms the minimum amount of shrimp kilograms that can be caught in a round.
   * @throws IllegalArgumentException if the minimum shrimp kilograms provided is less than {@code 0}.
   */
  private void setMinShrimpKilograms(int minShrimpKilograms) throws IllegalArgumentException {
    if (minShrimpKilograms < 0) {
      throw new IllegalArgumentException("minimum shrimp kilograms cannot be less than 0");
    }
    this.minShrimpKilograms = minShrimpKilograms;
  }

  /**
   * Getter for the maximum amount of shrimp kilograms that can be caught in a round.
   *
   * @return the maximum amount of shrimp kilograms that can be caught in a round.
   */
  public int getMaxShrimpKilograms() {
    return this.maxShrimpKilograms;
  }

  /**
   * Sets the maximum number of shrimp kilograms that can be caught in a round.
   *
   * @param maxShrimpKilograms the maximum number of shrimp kilograms that can be caught in a round.
   * @throws IllegalArgumentException if the maximum shrimp kilograms provided is less than {@code 0}.
   */
  private void setMaxShrimpKilograms(int maxShrimpKilograms) throws IllegalArgumentException {
    if (maxShrimpKilograms < 0) {
      throw new IllegalArgumentException("maximum shrimp kilograms cannot be less than 0");
    }
    this.maxShrimpKilograms = maxShrimpKilograms;
  }
}
