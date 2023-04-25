package org.example.ui.controllers;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.ShrimpGameApp;
import org.example.ui.view.GameScreen;
import org.example.ui.view.CatchShrimpScreen;

/**
 * Represents the controller for {@code CatchShrimpScreen}.
 * 
 * @see CatchShrimpScreen
 */
public class CatchShrimpScreenController {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates a new instance of {@code CatchShrimpScreenController}.
   * 
   * @param shrimpGameApp the {@code ShrimpGameApp} object used to get the game information.
   */
  public CatchShrimpScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles pressing the "Ok" button.
   * 
   * @param catchShrimpTextFld the text area with the amount of shrimp to catch.
   * @param errorLbl the label displaying an error if the amount is invalid.
   */
  public void handleOkButton(TextArea catchShrimpTextArea, Label errorLbl) {
    if (catchShrimpTextArea.getText().isEmpty()) {
      errorLbl.setText("Please fill all fields");
      errorLbl.setVisible(true);
    }
    else {
      try {
        int shrimpCaught = Integer.parseInt(catchShrimpTextArea.getText());
        if (shrimpCaught < this.shrimpGameApp.getGame().getSettings().getMinShrimpKilograms()) {
          throw new IllegalArgumentException(
              "Amount of shrimp cannot be less than " + this.shrimpGameApp.getGame().getSettings()
                                                                          .getMinShrimpKilograms());
        }
        else if (shrimpCaught > this.shrimpGameApp.getGame().getSettings().getMaxShrimpKilograms()) {
          throw new IllegalArgumentException(
              "Amount of shrimp cannot be greater than " + this.shrimpGameApp.getGame()
                                                                             .getSettings()
                                                                             .getMaxShrimpKilograms());
        }
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Catch Shrimp");
        confirmDialog.setHeaderText("Confirm Amount of Shrimp to Catch:");
        confirmDialog.setContentText(String.format("Amount of Shrimp: %dkg", shrimpCaught));
        this.shrimpGameApp.addIconToDialog(confirmDialog);
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
          try {
            this.shrimpGameApp.getServerConnection().sendCatchShrimpRequest(shrimpCaught);
            this.shrimpGameApp.getGame().getPlayers().get(this.shrimpGameApp.getUser().getName())
                              .setShrimpCaught(shrimpCaught);
            String response = this.shrimpGameApp.getServerConnection().getNextServerPacket();

            if (response.equals("CAUGHT_SUCCESSFULLY")) {
              Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
              successDialog.setTitle("Success");
              successDialog.setHeaderText(null);
              successDialog.setContentText("Caught shrimp successfully!");

              for (Label amountOfShrimpCaughtValueLbl : GameScreen.amountOfShrimpCaughtValueLabels)
              {
                amountOfShrimpCaughtValueLbl.setText(shrimpCaught + "kg");
              }
              catchShrimpTextArea.setText("");
              errorLbl.setVisible(false);
              this.shrimpGameApp.addIconToDialog(successDialog);

              synchronized (this.shrimpGameApp)
              {
                if (this.shrimpGameApp.allPlayersCaughtShrimp()) {
                  this.shrimpGameApp.setScene(this.shrimpGameApp.getShrimpCaughtSummaryScreen());
                }
                else {
                  int roundNum = this.shrimpGameApp.getGame().getCurrentRoundNum();
                  successDialog.showAndWait();
                  if (this.shrimpGameApp.getGame().getCurrentRoundNum() == roundNum) {
                    this.shrimpGameApp.setScene(this.shrimpGameApp.getGameCaughtShrimpScreen());
                  }
                }
              }
            }
            else {
              throw new RuntimeException("Failed to catch shrimp.");
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
      }
      catch (NumberFormatException exception) {
        errorLbl.setText("Numbers with space/decimals are invalid.");
        errorLbl.setVisible(true);
      }
      catch (IllegalArgumentException exception) {
        errorLbl.setText(exception.getMessage());
        errorLbl.setVisible(true);
      }
    }
  }
}
