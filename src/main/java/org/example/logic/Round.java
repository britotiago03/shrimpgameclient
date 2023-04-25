package org.example.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * The Round class represents a single round in the game, including the round number, round name, the shrimp price,
 * and the player actions for the round.
 */
public class Round {
  private int number;
  private String name;
  private int shrimpPrice;
  private Map<Player, Integer> playerShrimpCaughtMap;
  private Map<Player, Integer> playerMoneyMap;

  /**
   * Creates a new instance of {@code Round}.
   * 
   * @param number the round number.
   * @param shrimpPrice the price of the shrimp.
   * @param playerShrimpCaughtMap the amount of shrimp caught per player.
   * @param playerMoneyMap the amount of money per player.
   * @throws IllegalArgumentException if any of the parameters given are invalid.
   */
  public Round(int number, int shrimpPrice, Map<Player, Integer> playerShrimpCaughtMap,
               Map<Player, Integer> playerMoneyMap) throws IllegalArgumentException {
    this.setRoundNumber(number);
    this.setRoundName(number);
    this.setShrimpPrice(shrimpPrice);
    this.setPlayerShrimpCaughtMap(playerShrimpCaughtMap);
    this.setPlayerMoneyMap(playerMoneyMap);
  }

  /**
   * Sets the round number.
   * 
   * @param number the number to assign to the round.
   * @throws IllegalArgumentException if the number provided is less than {@code 0}.
   */
  private void setRoundNumber(int number) throws IllegalArgumentException {
    if (number < 0) {
      throw new IllegalArgumentException("round number cannot be less than 0");
    }
    this.number = number;
  }

  /**
   * Sets the round name.
   * 
   * @param number the number of the round.
   * @throws IllegalArgumentException if the round number provided is less than {@code 0}.
   */
  private void setRoundName(int number) throws IllegalArgumentException {
    if (number < 0) {
      throw new IllegalArgumentException("round number cannot be less than 0");
    }
    this.name = "Round " + number;
  }

  /**
   * Sets the shrimp price.
   * 
   * @param shrimpPrice the price of the shrimp during the round.
   * @throws IllegalArgumentException if the shrimp price provided is less than {@code 0}.
   */
  private void setShrimpPrice(int shrimpPrice) throws IllegalArgumentException {
    if (shrimpPrice < 0) {
      throw new IllegalArgumentException("shrimp price cannot be less than 0");
    }
    this.shrimpPrice = shrimpPrice;
  }

  /**
   * Sets the map of shrimp caught per player.
   * 
   * @param playerShrimpCaughtMap the amount of shrimp caught per player.
   * @throws IllegalArgumentException if the {@link Map} provided is {@code null}.
   */
  private void setPlayerShrimpCaughtMap(Map<Player, Integer> playerShrimpCaughtMap) throws IllegalArgumentException {
    if (playerShrimpCaughtMap == null) {
      throw new IllegalArgumentException("map of amount of shrimp caught per player cannot be set to null");
    }
    this.playerShrimpCaughtMap = playerShrimpCaughtMap;
  }

  /**
   * Sets the map of shrimp caught per player.
   * 
   * @param playerMoneyMap the amount of money per player
   * @throws IllegalArgumentException if the {@link Map} provided is {@code null}.
   */
  private void setPlayerMoneyMap(Map<Player, Integer> playerMoneyMap) throws IllegalArgumentException {
    if (playerMoneyMap == null) {
      throw new IllegalArgumentException("map of amount of money per player cannot be set to null");
    }
    this.playerMoneyMap = playerMoneyMap;
  }

  /**
   * Gets the number of the round.
   * 
   * @return the round number.
   */
  public int getNumber() {
    return this.number;
  }

  /**
   * Gets the name of the round.
   * 
   * @return the round name.
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets a {@link Map} of the amount of shrimp caught per player.
   * 
   * @return the amount of shrimp caught per player
   */
  public Map<Player, Integer> getPlayerShrimpCaughtMap() {
    return this.playerShrimpCaughtMap;
  }

  /**
   * Gets a {@link Map} of the amount of money per player.
   * 
   * @return the amount of money per player
   */
  public Map<Player, Integer> getPlayerMoneyMap() {
    return this.playerMoneyMap;
  }

  /**
   * Gets a {@link Map} of the players in the round.
   * 
   * @return the players in the round.
   */
  public Map<String, Player> getPlayers()
  {
    Map<String, Player> players = new HashMap<>();
    for (Player player : this.playerShrimpCaughtMap.keySet())
    {
      players.put(player.getName(), player);
    }
    return players;
  }

  /**
   * Gets the shrimp price of the round.
   * 
   * @return the shrimp price.
   */
  public int getShrimpPrice() {
    return this.shrimpPrice;
  }

  /**
   * Gets the total amount of shrimp caught.
   * 
   * @return the total amount of shrimp.
   */
  public int getTotalAmountOfShrimp()
  {
    int totalAmountOfShrimp = 0;
    for (Player player : this.playerShrimpCaughtMap.keySet())
    {
      totalAmountOfShrimp += this.playerShrimpCaughtMap.get(player);
    }
    return totalAmountOfShrimp;
  }
}