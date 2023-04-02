package org.example.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.ShrimpGameApp;
import org.example.logic.Lobby;

public class ServerUpdateListener implements Runnable {

  private final ShrimpGameApp shrimpGameApp;

  public ServerUpdateListener(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  @Override
  public void run() {
    while (true) {
      try {
        String serverPacket = this.shrimpGameApp.getServerConnection().getBufferedReader().readLine();
        String[] packetData = serverPacket.split(" ");
        if (packetData[0].equals("UPDATE")) {
          switch (packetData[1]) {
            case "LOBBY":
              List<Lobby> lobbies = new ArrayList<Lobby>();
              for (int index = 2; index < packetData.length; index++) {
                String[] lobby = packetData[index].split("\\.");
                lobbies.add(new Lobby(lobby[0], Integer.parseInt(lobby[1]), Integer.parseInt(lobby[2])));
              }
              this.shrimpGameApp.updateLobbyTable(lobbies);
              break;

            default:
              break;
          }
        }
        this.shrimpGameApp.getServerConnection().getServerPackets().add(serverPacket);
      }
      catch (IOException exception) {
        throw new RuntimeException("Failed to receive message from the server.");
      }
    }
  }
}
