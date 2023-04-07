package org.example.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.ShrimpGameApp;
import org.example.logic.Game;
import org.example.logic.GameSettings;
import org.example.logic.Lobby;
import org.example.logic.Player;
import org.example.logic.Round;

public class ServerUpdateListener implements Runnable {

  private final ShrimpGameApp shrimpGameApp;

  public ServerUpdateListener(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  @Override
  public void run() {
    while (true) {
      try {
        String serverPacket =
            this.shrimpGameApp.getServerConnection().getBufferedReader().readLine();
        String[] packetData = serverPacket.split(" ");
        Map<String, Player> players = null;
        Player player1 = null;
        Player player2 = null;
        Player player3 = null;
        if (packetData[0].equals("UPDATE")) {
          switch (packetData[1]) {
            case "LOBBY":
              List<Lobby> lobbiesInServer = new ArrayList<Lobby>();
              for (int index = 2; index < packetData.length; index++) {
                String[] lobby = packetData[index].split("\\.");
                lobbiesInServer.add(
                    new Lobby(lobby[0], Integer.parseInt(lobby[1]), Integer.parseInt(lobby[2])));
              }
              this.shrimpGameApp.setLobbies(lobbiesInServer);
              this.shrimpGameApp.updateLobbyTable(this.shrimpGameApp.getLobbies());
              break;
            case "GAME_STARTED":
              player1 = new Player(this.shrimpGameApp.getUser().getName(), 5);
              player2 = new Player(packetData[2], 5);
              player3 = new Player(packetData[3], 5);
              players = new HashMap<String, Player>();
              players.put(player1.getName(), player1);
              players.put(player2.getName(), player2);
              players.put(player3.getName(), player3);
              int numberOfRounds = Integer.parseInt(packetData[4]);
              int roundTime = Integer.parseInt(packetData[5]);
              int minShrimp = Integer.parseInt(packetData[6]);
              int maxShrimp = Integer.parseInt(packetData[7]);
              int islandNum = Integer.parseInt(packetData[8]);
              String islandName = packetData[9];
              GameSettings gameSettings = new GameSettings(3, numberOfRounds, roundTime, minShrimp,
                                                           maxShrimp);
              Game game = new Game(islandName, gameSettings, players, islandNum);
              this.shrimpGameApp.setGame(game);
              this.shrimpGameApp.setGameStarted(true);
              this.shrimpGameApp.getLobbies().clear();
              this.shrimpGameApp.updateLobbyTable(this.shrimpGameApp.getLobbies());
              this.shrimpGameApp.initGameScreens();
              this.shrimpGameApp.setScene(this.shrimpGameApp.getGameStartedScreen());
              break;

            case "ROUND_FINISHED":
              Map<Player, Integer> playerShrimpCaughtMap = new HashMap<Player, Integer>();
              Map<Player, Integer> playerMoneyMap = new HashMap<Player, Integer>();
              int roundNum = this.shrimpGameApp.getGame().getCurrentRoundNum();
              int shrimpPrice = Integer.parseInt(packetData[2]);
              player1 = this.shrimpGameApp.getGame().getPlayers().get(packetData[3]);
              playerShrimpCaughtMap.put(player1, Integer.parseInt(packetData[4]));
              playerMoneyMap.put(player1, Integer.parseInt(packetData[5]));
              player2 = this.shrimpGameApp.getGame().getPlayers().get(packetData[6]);
              playerShrimpCaughtMap.put(player2, Integer.parseInt(packetData[7]));
              playerMoneyMap.put(player2, Integer.parseInt(packetData[8]));
              player3 = this.shrimpGameApp.getGame().getPlayers().get(packetData[9]);
              playerShrimpCaughtMap.put(player3, Integer.parseInt(packetData[10]));
              playerMoneyMap.put(player3, Integer.parseInt(packetData[11]));
              Round round = new Round(roundNum, shrimpPrice, playerShrimpCaughtMap, playerMoneyMap);
              this.shrimpGameApp.getGame().getRounds().put(round.getNumber(), round);
              this.shrimpGameApp.getGame().setCurrentRoundNum(roundNum + 1);
              // DISPLAY RESULTS SCREEN, UPDATE GAME SCREENS, UPDATE SCOREBOARDTABLE
            default:
              break;
          }
        }
        else {
          this.shrimpGameApp.getServerConnection().getServerPackets().add(serverPacket);
        }
      }
      catch (IOException exception) {
        throw new RuntimeException("Failed to receive message from the server.");
      }
    }
  }
}
