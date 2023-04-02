package org.example.logic;

public class Lobby
{
    private String lobbyName;
    private int numPlayers;
    private int maxPlayers;

    public Lobby(String lobbyName, int numPlayers, int maxPlayers)
    {
        this.lobbyName = lobbyName;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public String getLobbyName()
    {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName)
    {
        this.lobbyName = lobbyName;
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers)
    {
        this.numPlayers = numPlayers;
    }
}
