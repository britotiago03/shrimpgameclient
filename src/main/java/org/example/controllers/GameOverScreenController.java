package org.example.controllers;

import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.ShrimpGameApp;

public class GameOverScreenController {
  private final ShrimpGameApp shrimpGameApp;


  public GameOverScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }


  public void handleLeaveGameButton() {
    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmDialog.setTitle("Leave Game");
    confirmDialog.setHeaderText("Confirm decision to leave game");
    confirmDialog.setContentText(("Are you sure you want to leave the game?"));
    this.shrimpGameApp.addIconToDialog(confirmDialog);
    Optional<ButtonType> result = confirmDialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      shrimpGameApp.setScene(shrimpGameApp.getMainScreen());
      shrimpGameApp.setGameStarted(false);
      shrimpGameApp.updateChatMessageGrid(new ArrayList<String>());
    }
  }
}
