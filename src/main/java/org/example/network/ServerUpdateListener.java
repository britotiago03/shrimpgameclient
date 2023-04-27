package org.example.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import org.example.ShrimpGameApp;
import org.example.model.Game;
import org.example.model.GameSettings;
import org.example.model.Lobby;
import org.example.model.Player;
import org.example.model.Round;
import org.example.model.Timer;

/**
 * Represents a listener that listens to server updates.
 */
public class ServerUpdateListener implements Runnable {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates a new instance of {@code ServerUpdateListener}.
   *
   * @param shrimpGameApp the main application.
   * @throws IllegalArgumentException if the parameter given is set to {@code null}.
   */
  public ServerUpdateListener(ShrimpGameApp shrimpGameApp) throws IllegalArgumentException {
    if (shrimpGameApp == null) {
      throw new IllegalArgumentException("shrimpGameApp cannot be set to null");
    }
    this.shrimpGameApp = shrimpGameApp;
  }

  @Override
  public void run() {
    while (true) {
      String serverPacket = this.shrimpGameApp.getServerConnection().receive();
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
            Platform.runLater(() ->
                              {
                                this.shrimpGameApp.setLobbies(lobbiesInServer);
                                this.shrimpGameApp.updateLobbyTable(
                                    this.shrimpGameApp.getLobbies());
                              });
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
            String communicationRounds = packetData[6];
            int communicationRoundTime = Integer.parseInt(packetData[7]);
            int minShrimp = Integer.parseInt(packetData[8]);
            int maxShrimp = Integer.parseInt(packetData[9]);
            int islandNum = Integer.parseInt(packetData[10]);
            String gameName = packetData[11];
            GameSettings gameSettings = new GameSettings(3, numberOfRounds, roundTime,
                                                         communicationRounds,
                                                         communicationRoundTime, minShrimp,
                                                         maxShrimp);
            Game game = new Game(gameName, gameSettings, players, islandNum);
            this.shrimpGameApp.setGameStarted(true);
            this.shrimpGameApp.setGame(game);
            this.shrimpGameApp.getLobbies().remove(this.shrimpGameApp.getSelectedLobby());
            this.shrimpGameApp.setSelectedLobby(null);

            this.createRoundTimer();

            Platform.runLater(() ->
                              {
                                this.shrimpGameApp.updateLobbyTable(
                                    this.shrimpGameApp.getLobbies());
                                this.shrimpGameApp.resetScoreboardTables();
                                this.shrimpGameApp.initGameScreens();
                                this.shrimpGameApp.setScene(
                                    this.shrimpGameApp.getGameStartedScreen());
                                this.shrimpGameApp.getGame().getRoundTimer().start();
                              });
            break;

          case "ROUND_FINISHED":
            Map<Player, Integer> playerShrimpCaughtMap = new HashMap<Player, Integer>();
            Map<Player, Integer> playerMoneyMap = new HashMap<Player, Integer>();
            int roundNum = this.shrimpGameApp.getGame().getCurrentRoundNum();
            int shrimpPrice = Integer.parseInt(packetData[2]);
            this.shrimpGameApp.setAllPlayersCaughtShrimp(true);
            player1 = this.shrimpGameApp.getGame().getPlayers().get(packetData[3]);
            int player1shrimpCaught = Integer.parseInt(packetData[4]);
            player1.setShrimpCaught(player1shrimpCaught);
            int player1roundProfit = Integer.parseInt(packetData[5]);
            player1.setRoundProfit(player1roundProfit);
            player1.setPreviousTotalMoney(player1.getCurrentTotalMoney());
            player1.setCurrentTotalMoney(player1.getCurrentTotalMoney() + player1.getRoundProfit());
            shrimpGameApp.getGame().getPlayers().get(shrimpGameApp.getUser().getName())
                         .setCurrentTotalMoney(player1.getCurrentTotalMoney());
            playerShrimpCaughtMap.put(new Player(player1), player1shrimpCaught);
            playerMoneyMap.put(new Player(player1), player1roundProfit);
            player2 = this.shrimpGameApp.getGame().getPlayers().get(packetData[6]);
            int player2shrimpCaught = Integer.parseInt(packetData[7]);
            player2.setShrimpCaught(player2shrimpCaught);
            int player2roundProfit = Integer.parseInt(packetData[8]);
            player2.setRoundProfit(player2roundProfit);
            player2.setPreviousTotalMoney(player2.getCurrentTotalMoney());
            player2.setCurrentTotalMoney(player2.getCurrentTotalMoney() + player2.getRoundProfit());
            playerShrimpCaughtMap.put(new Player(player2), player2shrimpCaught);
            playerMoneyMap.put(new Player(player2), player2roundProfit);

            player3 = this.shrimpGameApp.getGame().getPlayers().get(packetData[9]);
            int player3shrimpCaught = Integer.parseInt(packetData[10]);
            player3.setShrimpCaught(player3shrimpCaught);
            int player3roundProfit = Integer.parseInt(packetData[11]);
            player3.setRoundProfit(player3roundProfit);
            player3.setPreviousTotalMoney(player3.getCurrentTotalMoney());
            player3.setCurrentTotalMoney(player3.getCurrentTotalMoney() + player3.getRoundProfit());
            playerShrimpCaughtMap.put(new Player(player3), player3shrimpCaught);
            playerMoneyMap.put(new Player(player3), player3roundProfit);

            Round round = new Round(roundNum, shrimpPrice, playerShrimpCaughtMap, playerMoneyMap);
            this.shrimpGameApp.getGame().getRounds().put(round.getNumber(), round);
            this.shrimpGameApp.getGame().setCurrentRoundNum(roundNum + 1);
            this.shrimpGameApp.initRoundResultsScreens();
            this.shrimpGameApp.getGame().getPlayers().get(this.shrimpGameApp.getUser().getName())
                              .setShrimpCaught(-1);
            Platform.runLater(() ->
                              {
                                this.shrimpGameApp.setScene(
                                    this.shrimpGameApp.getShrimpCaughtSummaryScreen());
                                this.shrimpGameApp.getGame().getRoundTimer().stop();
                                if (this.shrimpGameApp.getGame().getCurrentRoundNum()
                                    <= this.shrimpGameApp.getGame().getSettings()
                                                         .getNumberOfRounds()) {
                                  this.createRoundTimer();
                                  this.shrimpGameApp.getGame().getRoundTimer().start();
                                }
                                this.shrimpGameApp.updateScoreboardTable(new ArrayList<>(
                                    this.shrimpGameApp.getGame().getRounds().values()));
                              });

            break;

          case "MESSAGE_SENT":
            String username = packetData[2];
            String message = packetData[3];
            String date = packetData[4];
            this.shrimpGameApp.getGame().getMessages().add(
                username + "." + message.replace(".", " ") + "." + date);
            Platform.runLater(() ->
                              {
                                this.shrimpGameApp.updateChatMessageGrid(
                                    this.shrimpGameApp.getGame().getMessages());
                              });
            break;

          default:
            break;
        }
      }
      else {
        ServerConnection serverConnection = this.shrimpGameApp.getServerConnection();
        synchronized (serverConnection.getServerPackets()) {
          serverConnection.getServerPackets().add(serverPacket);
          serverConnection.getServerPackets().notify();

        }
      }
    }
  }

  /**
   * Creates the round timer.
   */
  public void createRoundTimer() {
    this.shrimpGameApp.getGame().setRoundTimer(
        new Timer(this.shrimpGameApp, this.shrimpGameApp.getRoundTimerLabels()));
    String[] gameCommunicationRounds =
        this.shrimpGameApp.getGame().getSettings().getCommunicationRounds().split(",");
    List<Integer> commRoundNums = new ArrayList<Integer>();
    for (String communicationRound : gameCommunicationRounds) {
      commRoundNums.add(Integer.parseInt(communicationRound));
    }
    if (commRoundNums.contains(this.shrimpGameApp.getGame().getCurrentRoundNum())) {
      this.shrimpGameApp.getGame().getRoundTimer().setSecondsLeft(
          this.shrimpGameApp.getGame().getRoundTimer().getSecondsLeft()
          + this.shrimpGameApp.getGame().getSettings().getCommunicationRoundTime());
    }
  }
}
