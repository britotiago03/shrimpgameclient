package org.example.logic;

/**
 * Represents a lobby with a name and player amounts.
 */
public class Lobby {
  private String lobbyName;
  private int numPlayers;
  private int maxPlayers;

  /**
   * Creates a new instance of {@code Lobby}.
   * 
   * @param lobbyName the name of the lobby.
   * @param numPlayers the number of players currently in the lobby.
   * @param maxPlayers the maximum amount of players allowed in the lobby.
   * @throws IllegalArgumentException if any of the parameters provided are invalid.
   */
  public Lobby(String lobbyName, int numPlayers, int maxPlayers) throws IllegalArgumentException {
    this.setLobbyName(lobbyName);
    this.setNumPlayers(numPlayers);
    this.setMaxPlayers(maxPlayers);
  }

  /**
   * Sets the name of the lobby.
   * 
   * @param lobbyName the name to assign to the lobby.
   * @throws IllegalArgumentException if the lobby name provided is {@code null}.
   */
  private void setLobbyName(String lobbyName) throws IllegalArgumentException {
    if (lobbyName == null) {
      throw new IllegalArgumentException("lobby name cannot be null");
    }
    this.lobbyName = lobbyName;
  }

  /**
   * Sets the number of players.
   * 
   * @param numPlayers the number of players. Must be a value greater than or equal to {@code 0}.
   * @throws IllegalArgumentException if the number of players provided is less than {@code 0}.
   */
  private void setNumPlayers(int numPlayers) throws IllegalArgumentException {
    if (numPlayers < 0) {
      throw new IllegalArgumentException("the number of players cannot be less than 0");
    }
    this.numPlayers = numPlayers;
  }
  
  /**
   * Sets the maximum number of players.
   * 
   * @param maxPlayers the maximum number of players. Must be a value greater than or equal to {@code 0}.
   * @throws IllegalArgumentException if the maximum number of players provided is less than {@code 0}.
   */
  private void setMaxPlayers(int maxPlayers) throws IllegalArgumentException {
    if (maxPlayers < 0) {
      throw new IllegalArgumentException("the maximum number of players cannot be less than 0");
    }
    this.maxPlayers = maxPlayers;
  }

  /**
   * Gets the maximum amount of players allowed in the lobby.
   * @return the maximum amount of players.
   */
  public int getMaxPlayers() {
    return this.maxPlayers;
  }

  /**
   * Gets the name of the lobby.
   * @return the lobby name.
   */
  public String getLobbyName() {
    return this.lobbyName;
  }

  /**
   * Gets the number of players currently in the lobby.
   * 
   * @return the amount of players in the lobby.
   */
  public int getNumPlayers()
  {
    return this.numPlayers;
  }
}
