package org.example.logic;

import java.util.List;

/**
 * The Game class represents a game with a unique identifier, name, game settings, status, and a
 * list of players.
 * <p>
 * The class provides methods for managing the game, including starting and ending the game,
 * updating player stats,
 * <p>
 * broadcasting messages, sending game results, and disconnecting clients.
 */
public class Game {
  private String name;
  private GameSettings settings;
  private int islandNum;
  private String gameStatus;
  private int currentRoundNum;
  private Round currentRound;
  private List<Player> players;

  /**
   * Constructs a new Game object with the specified game ID, name, settings, status, and players.
   *
   * @param gameID     the unique identifier for the game
   * @param name       the name of the game
   * @param settings   the settings of the game
   * @param gameStatus the current status of the game
   * @param players    the list of players in the game
   */
  public Game(String name, GameSettings settings, List<Player> players, int islandNum) {
    this.name = name;
    this.settings = settings;
    this.players = players;
    this.currentRoundNum = 1;
    this.islandNum = islandNum;
    this.currentRound = this.getNextRound();
  }

  /**
   * Returns the name of the game.
   *
   * @return the game name
   */
  public String getName() {
    return this.name;
  }

  public Round getCurrentRound() {
    return this.currentRound;
  }

  public Round getNextRound()
  {
    Round nextRound = new Round(this, this.currentRoundNum);
    this.currentRoundNum++;
    return nextRound;
  }

  public int getIslandNum()
  {
    return this.islandNum;
  }

  public List<Player> getPlayers()
  {
    return this.players;
  }

  /**
   * Sets the name of the game.
   *
   * @param name the new game name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the settings of the game.
   *
   * @return the game settings
   */
  public GameSettings getSettings() {
    return this.settings;
  }

  /**
   * Sets the settings of the game.
   *
   * @param settings the new game settings
   */
  public void setSettings(GameSettings settings) {
    this.settings = settings;
  }
}
