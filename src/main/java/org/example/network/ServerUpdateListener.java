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
              Player player1 = new Player(this.shrimpGameApp.getUser().getName(), 5);
              Player player2 = new Player(packetData[2], 5);
              Player player3 = new Player(packetData[3], 5);
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

            case "SHRIMP_CAUGHT":
              String playerName = packetData[2];
              int shrimpCaught = Integer.parseInt(packetData[3]);
              Player player = this.shrimpGameApp.getGame().getPlayers().get(playerName);
              player.setShrimpCaught(shrimpCaught);

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
