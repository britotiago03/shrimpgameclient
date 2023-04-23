package org.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.userinterface.GameScreen;

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
  private final String name;
  private Timer roundTimer;
  private final GameSettings settings;
  private final int number;
  private final Map<Integer, Round> rounds;
  private int currentRoundNum;
  private final Map<String, Player> players;
  private final List<String> messages;

  /**
   * Constructs a new Game object with the specified game ID, name, settings, status, and players.
   *
   * @param name     the name of the game
   * @param settings the settings of the game
   * @param players  the list of players in the game
   */
  public Game(String name, GameSettings settings, Map<String, Player> players, int number) {
    this.name = name;
    this.settings = settings;
    this.players = players;
    this.messages = new ArrayList<String>();
    this.rounds = new HashMap<Integer, Round>();
    this.currentRoundNum = 1;
    this.number = number;
  }

  /**
   * Returns the name of the game.
   *
   * @return the game name
   */
  public String getName() {
    return this.name;
  }

  public Map<Integer, Round> getRounds() {
    return this.rounds;
  }

  public int getNumber() {
    return this.number;
  }

  public Map<String, Player> getPlayers() {
    return this.players;
  }

  public List<String> getMessages()
  {
    return this.messages;
  }

  /**
   * Returns the settings of the game.
   *
   * @return the game settings
   */
  public GameSettings getSettings() {
    return this.settings;
  }

  public int getCurrentRoundNum() {
    return this.currentRoundNum;
  }

  public void setCurrentRoundNum(int currentRoundNum) {
    this.currentRoundNum = currentRoundNum;
  }

  public Timer getRoundTimer()
  {
    return this.roundTimer;
  }

  public void setRoundTimer(Timer roundTimer)
  {
    this.roundTimer = roundTimer;
  }
}
