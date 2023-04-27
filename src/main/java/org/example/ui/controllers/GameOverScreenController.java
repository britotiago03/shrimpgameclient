package org.example.ui.controllers;

import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.ShrimpGameApp;

/**
 * Represents the controller for the game over screens.
 */
public class GameOverScreenController {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates a new instance of {@code GameOverScreenController}.
   * 
   * @param shrimpGameApp the {@link ShrimpGameApp} to get the game information from.
   */
  public GameOverScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles pressing the leave game button.
   */
  public void handleLeaveGameButton() {
    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmDialog.setTitle("Leave Game");
    confirmDialog.setHeaderText("Confirm decision to leave game");
    confirmDialog.setContentText(("Are you sure you want to leave the game?"));
    this.shrimpGameApp.addIconToDialog(confirmDialog);
    Optional<ButtonType> result = confirmDialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      this.shrimpGameApp.setScene(this.shrimpGameApp.getMainScreen());
      this.shrimpGameApp.setGameStarted(false);
      this.shrimpGameApp.updateChatMessageGrid(new ArrayList<String>());
    }
  }

  /**
   * Handles pressing the view chat button.
   */
  public void handleViewChatButton() {
    this.shrimpGameApp.setScene(this.shrimpGameApp.getGameOverViewChatScreen());
  }

  /**
   * Handles pressing the scoreboard button.
   */
  public void handleViewScoreboardButton() {
    this.shrimpGameApp.setScene(this.shrimpGameApp.getGameOverViewScoreboardScreen());
  }
}
