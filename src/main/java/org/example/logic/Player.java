package org.example.logic;

/**
 * The Player class represents a player in the game. It contains information about the player
 * such as name,
 * <p>
 * money, expenses, island and shrimp pounds caught.
 */
public class Player {
  private final String name;
  private int money;
  private int expenses;
  private int shrimpCaught;

  /**
   * Constructor for creating a Player object with the given name, money, expenses, island and
   * shrimp pounds caught.
   *
   * @param name     The name of the player.
   * @param expenses The total expenses of the player.
   */
  public Player(String name, int expenses) {
    this.name = name;
    this.money = 0;
    this.expenses = expenses;
    this.shrimpCaught = 0;
  }

  public String getName() {
    return this.name;
  }

  /**
   * Returns the amount of money the player has.
   *
   * @return The amount of money the player has.
   */
  public int getMoney() {
    return this.money;
  }

  /**
   * Sets the amount of money the player has.
   *
   * @param money The amount of money the player has.
   */
  public void setMoney(int money) {
    this.money = money;
  }

  /**
   * Returns the total expenses of the player.
   *
   * @return The total expenses of the player.
   */
  public int getExpenses() {
    return this.expenses;
  }

  /**
   * Sets the total expenses of the player.
   *
   * @param expenses The total expenses of the player.
   */
  public void setExpenses(int expenses) {
    this.expenses = expenses;
  }

  /**
   * Returns the total amount of shrimp pounds caught by the player.
   *
   * @return The total amount of shrimp pounds caught by the player.
   */
  public int getShrimpCaught() {
    return this.shrimpCaught;
  }

  /**
   * Sets the total amount of shrimp pounds caught by the player.
   *
   * @param shrimpCaught The total amount of shrimp pounds caught by the player.
   */
  public void setShrimpCaught(int shrimpCaught) {
    this.shrimpCaught = shrimpCaught;
  }

  /**
   * Calculates the profit of the player based on the price of shrimp and the expenses incurred.
   *
   * @param shrimpPrice the price of shrimp per pound
   * @return the calculated profit of the player
   */
  public int calculateProfit(int shrimpPrice) {
    return (this.shrimpCaught * shrimpPrice) - this.expenses;
  }

  /**
   * Resets the shrimp pounds caught and expenses of the player to 0.
   */
  public void resetStats() {
    this.shrimpCaught = 0;
    this.expenses = 0;
  }
}
