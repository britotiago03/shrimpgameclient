package org.example.model;

import java.util.List;

public class GameResult {
  private final String name;
  private final int numberOfRounds;
  private final List<String> csvData;
  private final String timeFinished;

  public GameResult(String name, int numberOfRounds, List<String> csvData, String timeFinished)
  {
    this.name = name;
    this.numberOfRounds = numberOfRounds;
    this.csvData = csvData;
    this.timeFinished = timeFinished;
  }

  public String getName()
  {
    return this.name;
  }

  public int getNumberOfRounds()
  {
    return this.numberOfRounds;
  }

  public List<String> getCsvData()
  {
    return this.csvData;
  }

  public String getTimeFinished() {
    return this.timeFinished;
  }
}
