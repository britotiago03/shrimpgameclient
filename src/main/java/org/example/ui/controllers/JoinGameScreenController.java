package org.example.ui.controllers;

import javafx.scene.control.Alert;
import org.example.ShrimpGameApp;
import org.example.model.Lobby;

/**
 * This class is the controller for the join game screen in the Shrimp Game application. It handles
 * user input and updates the UI accordingly. It allows the user to join a selected lobby or leave a
 * lobby they have already joined.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public class JoinGameScreenController {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates a new instance of the {@code JoinGameScreenController} class.
   *
   * @param shrimpGameApp the instance of the main application class
   */
  public JoinGameScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;

  }

  /**
   * Handles the "Join" button being pressed. 
   * Sends a join lobby request to the server and updates the UI accordingly.
   *
   * @param selectedLobby the selected lobby object
   */
  public void handleJoinButton(Lobby selectedLobby) {
    try {
      synchronized (this.shrimpGameApp.getServerConnection())
      {
        this.shrimpGameApp.getServerConnection().sendJoinLobbyRequest(selectedLobby.getLobbyName());
      }
      Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
      successDialog.setTitle("Success");
      successDialog.setHeaderText(null);
      successDialog.setContentText("Joined the lobby successfully!");
      this.shrimpGameApp.addIconToDialog(successDialog);
      this.shrimpGameApp.setSelectedLobby(selectedLobby);
      synchronized (this.shrimpGameApp)
      {
        if (this.shrimpGameApp.isGameStarted())
        {
          this.shrimpGameApp.setScene(this.shrimpGameApp.getGameStartedScreen());
        }
        else {
          successDialog.showAndWait();
          if (!this.shrimpGameApp.isGameStarted())
          {
            this.shrimpGameApp.setScene(this.shrimpGameApp.getJoinedGameScreen());
          }
        }
      }
    }
    catch (RuntimeException exception) {
      Alert errorDialog = new Alert(Alert.AlertType.ERROR);
      errorDialog.setTitle("Error");
      errorDialog.setHeaderText(null);
      errorDialog.setContentText(exception.getMessage());
      this.shrimpGameApp.addIconToDialog(errorDialog);
      errorDialog.showAndWait();
    }

  }

  /**
   * Handles the leave button click event. Sends a leave lobby request to the server and updates
   * the UI accordingly.
   */
  public void handleLeaveButton() {
    try {
      synchronized (this.shrimpGameApp.getServerConnection())
      {
        this.shrimpGameApp.getServerConnection().sendLeaveLobbyRequest();
      }
      this.shrimpGameApp.setSelectedLobby(null);
    }
    catch (RuntimeException exception) {
      Alert errorDialog = new Alert(Alert.AlertType.ERROR);
      errorDialog.setTitle("Error");
      errorDialog.setHeaderText(null);
      errorDialog.setContentText(exception.getMessage());
      this.shrimpGameApp.addIconToDialog(errorDialog);
      errorDialog.showAndWait();
    }
    this.shrimpGameApp.setScene(this.shrimpGameApp.getJoinGameScreen());

  }
}
