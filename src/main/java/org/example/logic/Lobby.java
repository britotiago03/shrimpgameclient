package org.example.logic;

public class Lobby {
  private final String lobbyName;
  private int numPlayers;
  private final int maxPlayers;

  public Lobby(String lobbyName, int numPlayers, int maxPlayers) {
    this.lobbyName = lobbyName;
    this.numPlayers = numPlayers;
    this.maxPlayers = maxPlayers;
  }

  public int getMaxPlayers() {
    return this.maxPlayers;
  }

  public String getLobbyName() {
    return lobbyName;
  }

  public int getNumPlayers()
  {
    return this.numPlayers;
  }
}
