package org.example.model;

/**
 * The Player class represents a player in the game. It contains information about the player
 * such as name, money, expenses and shrimp kilograms caught.
 */
public class Player {
  private String name;
  private int previousTotalMoney;
  private int currentTotalMoney;
  private int roundProfit;
  private int expenses;
  private int shrimpCaught;

  /**
   * Constructor for creating a Player object with a specified name and expenses.
   *
   * @param name     the name of the player.
   * @param expenses the total expenses of the player.
   * @throws IllegalArgumentException if any of the parameters provided are invalid.
   */
  public Player(String name, int expenses) throws IllegalArgumentException {
    this.setName(name);
    this.previousTotalMoney = 0;
    this.currentTotalMoney = 0;
    this.roundProfit = 0;
    this.expenses = expenses;
    this.shrimpCaught = -1;
  }

  /**
   * Constructor for copying a {@code Player} object.
   * 
   * @param player the {@code Player} to copy from.
   * @throws IllegalArgumentException if any of the parameters provided are invalid.
   */
  public Player(Player player) throws IllegalArgumentException
  {
    setName(player.getName());
    this.previousTotalMoney = player.getPreviousTotalMoney();
    this.currentTotalMoney = player.getCurrentTotalMoney();
    this.roundProfit = player.getRoundProfit();
    this.expenses = player.getExpenses();
    this.shrimpCaught = player.getShrimpCaught();
  }

  /**
   * Sets the name of the player.
   * 
   * @param name the name to assign to the player.
   * @throws IllegalArgumentException if the name provided is {@code null}.
   */
  private void setName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be set to null");
    }
    this.name = name;
  }

  /**
   * Gets the name of the player.
   * 
   * @return the name of the player.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the amount of money the player has.
   *
   * @return the amount of money the player has.
   */
  public int getPreviousTotalMoney() {
    return this.previousTotalMoney;
  }


  /**
   * Sets the amount of money the player has.
   */
  public void setPreviousTotalMoney(int previousTotalMoney) {
    this.previousTotalMoney = previousTotalMoney;
  }

  /**
   * Gets the current total amount of money the player has.
   * 
   * @return the current amount of money.
   */
  public int getCurrentTotalMoney() {
    return this.currentTotalMoney;
  }

  /**
   * Sets the current total amount of money the player has.
   * 
   * @param currentTotalMoney the current total amount of money.
   */
  public void setCurrentTotalMoney(int currentTotalMoney) {
    this.currentTotalMoney = currentTotalMoney;
  }

  /**
   * Gets the round profit of the player.
   * 
   * @return the round profit of the player.
   */
  public int getRoundProfit() {
    return this.roundProfit;
  }

  /**
   * Sets the round profit of the player.
   * 
   * @param roundProfit the round profit of the player.
   */
  public void setRoundProfit(int roundProfit) {
    this.roundProfit = roundProfit;
  }

  /**
   * Returns the total expenses of the player.
   *
   * @return the total expenses of the player.
   */
  public int getExpenses() {
    return this.expenses;
  }

  /**
   * Sets the total expenses of the player.
   *
   * @param expenses the total expenses of the player.
   */
  public void setExpenses(int expenses) {
    this.expenses = expenses;
  }

  /**
   * Gets the total amount of shrimp caught by the player.
   *
   * @return the total amount of shrimp caught by the player.
   */
  public int getShrimpCaught() {
    return this.shrimpCaught;
  }

  /**
   * Sets the total amount of shrimp caught by the player.
   *
   * @param shrimpCaught The total amount of shrimp caught by the player.
   */
  public void setShrimpCaught(int shrimpCaught) {
    this.shrimpCaught = shrimpCaught;
  }

  public boolean hasCaughtShrimp() {
    boolean hasCaughtShrimp = false;
    if (this.shrimpCaught != -1) {
      hasCaughtShrimp = true;
    }
    return hasCaughtShrimp;
  }

  /**
   * Calculates the profit of the player based on the price of shrimp and the expenses incurred.
   *
   * @param shrimpPrice the price of shrimp per kilogram.
   * @return the calculated profit of the player.
   */
  public int calculateProfit(int shrimpPrice) {
    return (this.shrimpCaught * shrimpPrice) - this.expenses;
  }

  /**
   * Resets the shrimp kilograms caught and expenses of the player to 0.
   */
  public void resetStats() {
    this.shrimpCaught = 0;
    this.expenses = 0;
  }
}
