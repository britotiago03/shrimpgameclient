package org.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.User;

/**
 * The Round class represents a single round in the game, including the round number, the player
 * actions for the round, and whether communication is enabled.
 */
public class Round {
  private final int number;
  private final String name;
  private final int shrimpPrice;
  private final Map<Player, Integer> playerShrimpCaughtMap;
  private final Map<Player, Integer> playerMoneyMap;

  public Round(int number, int shrimpPrice, Map<Player, Integer> playerShrimpCaughtMap,
               Map<Player, Integer> playerMoneyMap) {
    this.number = number;
    this.name = "Round " + number;
    this.shrimpPrice = shrimpPrice;
    this.playerShrimpCaughtMap = playerShrimpCaughtMap;
    this.playerMoneyMap = playerMoneyMap;
  }

  public int getNumber() {
    return this.number;
  }

  public String getName()
  {
    return this.name;
  }

  public Map<Player, Integer> getPlayerShrimpCaughtMap() {
    return this.playerShrimpCaughtMap;
  }

  public Map<Player, Integer> getPlayerMoneyMap() {
    return this.playerMoneyMap;
  }

  public Map<String, Player> getPlayers()
  {
    Map<String, Player> players = new HashMap<>();
    for (Player player : this.playerShrimpCaughtMap.keySet())
    {
      players.put(player.getName(), player);
    }
    return players;
  }

  public int getShrimpPrice() {
    return this.shrimpPrice;
  }

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