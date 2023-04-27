package org.example.model;

import java.util.List;

/**
 * Represents the results of a game.
 */
public class GameResult {
  private final String name;
  private final int numberOfRounds;
  private final List<String> csvData;
  private final String timeFinished;

  /**
   * Creates a new instance of {@code GameResult}.
   * 
   * @param name the name of the results.
   * @param numberOfRounds the number of rounds.
   * @param csvData the csv data.
   * @param timeFinished the time at which the game finished.
   */
  public GameResult(String name, int numberOfRounds, List<String> csvData, String timeFinished)
  {
    this.name = name;
    this.numberOfRounds = numberOfRounds;
    this.csvData = csvData;
    this.timeFinished = timeFinished;
  }

  /**
   * Gets the name of the result.
   * 
   * @return the name of the result.
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets the number of rounds.
   * 
   * @return the number of rounds.
   */
  public int getNumberOfRounds()
  {
    return this.numberOfRounds;
  }

  /**
   * Gets the csv data.
   * 
   * @return the csv data.
   */
  public List<String> getCsvData()
  {
    return this.csvData;
  }

  /**
   * Gets the time at which the game finished.
   * 
   * @return the time at which the game finished.
   */
  public String getTimeFinished() {
    return this.timeFinished;
  }
}
