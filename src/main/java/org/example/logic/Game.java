package org.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Game class represents a game with a unique identifier, name, game
 * settings, timer, amount of rounds, current round, messages, and a list of
 * players.
 */
public class Game {
  private String name;
  private Timer roundTimer;
  private GameSettings settings;
  private final int number;
  private final Map<Integer, Round> rounds;
  private int currentRoundNum;
  private Map<String, Player> players;
  private final List<String> messages;

  /**
   * Creates a new instance of the Game class with specified name, settings, players and identifier.
   *
   * @param name     the name of the game.
   * @param settings the settings of the game.
   * @param players  the list of players in the game.
   * @param number   the unique identifier of the game.
   * @throws IllegalArgumentException if any of the provided parameters are invalid.
   */
  public Game(String name, GameSettings settings, Map<String, Player> players, int number) throws IllegalArgumentException {
    this.setName(name);
    this.setSettings(settings);
    this.setPlayers(players);
    this.messages = new ArrayList<String>();
    this.rounds = new HashMap<Integer, Round>();
    this.setCurrentRoundNum(1);
    this.number = number;
  }

  /**
   * Sets the name of the game.
   * 
   * @param name the name to assign to the game.
   * @throws IllegalArgumentException if the name provided is {@code null}.
   */
  private void setName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be set to null");
    }
    this.name = name;
  }

  /**
   * Sets the settings of the game.
   * 
   * @param settings the settings of the game.
   * @throws IllegalArgumentException if the settings provided is {@code null}.
   */
  private void setSettings(GameSettings settings) throws IllegalArgumentException {
    if (settings == null) {
      throw new IllegalArgumentException("settings cannot be set to null");
    }
    this.settings = settings;
  }

  /**
   * Sets the players of the game.
   * 
   * @param players the players of the game.
   * @throws IllegalArgumentException if the players provided is {@code null}.
   */
  private void setPlayers(Map<String, Player> players) throws IllegalArgumentException {
    if (players == null) {
      throw new IllegalArgumentException("players cannot be set to null");
    }
    this.players = players;
  }

  /**
   * Gets the name of the game.
   *
   * @return the game name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the rounds of the game.
   * 
   * @return the rounds of the game.
   */
  public Map<Integer, Round> getRounds() {
    return this.rounds;
  }

  /**
   * Gets the game's identifier.
   * 
   * @return the number of the game.
   */
  public int getNumber() {
    return this.number;
  }

  /**
   * Gets the players of the game.
   * 
   * @return the players of the game.
   */
  public Map<String, Player> getPlayers() {
    return this.players;
  }

  /**
   * Gets the messages from the game.
   * 
   * @return the messages between players during the game.
   */
  public List<String> getMessages() {
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

  /**
   * Gets the current round number of the game.
   * 
   * @return the current round number.
   */
  public int getCurrentRoundNum() {
    return this.currentRoundNum;
  }

  /**
   * Sets the current round number of the game.
   * 
   * @param currentRoundNum the current round number of the game. Must be a number of {@code 0} or over.
   * @throws IllegalArgumentException if the specified round number is less than {@code 0}.
   */
  public void setCurrentRoundNum(int currentRoundNum) throws IllegalArgumentException {
    if (currentRoundNum < 0) {
      throw new IllegalArgumentException("The current round number is invalid");
    }
    this.currentRoundNum = currentRoundNum;
  }

  public Timer getRoundTimer() {
    return this.roundTimer;
  }

  public void setRoundTimer(Timer roundTimer) {
    this.roundTimer = roundTimer;
  }
}
