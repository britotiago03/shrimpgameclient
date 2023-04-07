package org.example.controllers;

import java.util.Arrays;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.ShrimpGameApp;

public class CatchShrimpScreenController {
  private final ShrimpGameApp shrimpGameApp;


  public CatchShrimpScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }


  public void handleOkButton(TextField catchShrimpTextFld, Label errorLbl) {
    if (catchShrimpTextFld.getText().isEmpty()) {
      errorLbl.setText("Please fill all fields.");
      errorLbl.setVisible(true);
    }
    else {
      try {
        int shrimpCaught = Integer.parseInt(catchShrimpTextFld.getText());
        if (shrimpCaught < this.shrimpGameApp.getGame().getSettings().getMinShrimpPounds()) {
          throw new IllegalArgumentException(
              "Amount of shrimp cannot be less than " + this.shrimpGameApp.getGame().getSettings()
                                                                          .getMinShrimpPounds());
        }
        else if (shrimpCaught > this.shrimpGameApp.getGame().getSettings().getMaxShrimpPounds()) {
          throw new IllegalArgumentException(
              "Amount of shrimp cannot be greater than " + this.shrimpGameApp.getGame()
                                                                             .getSettings()
                                                                             .getMaxShrimpPounds());
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
            String response = this.shrimpGameApp.getServerConnection().getNextServerPacket();

            if (response.equals("CAUGHT_SUCCESSFULLY")) {
              Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
              successDialog.setTitle("Success");
              successDialog.setHeaderText(null);
              successDialog.setContentText("Caught shrimp successfully!");

              catchShrimpTextFld.setText("");
              this.shrimpGameApp.addIconToDialog(successDialog);
              successDialog.showAndWait();
              this.shrimpGameApp.setScene(this.shrimpGameApp.getGameCaughtShrimpScreen());
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
        errorLbl.setText("Make sure you input one number without spaces or decimal points.");
        errorLbl.setVisible(true);
      }
      catch (IllegalArgumentException exception) {
        errorLbl.setText(exception.getMessage());
        errorLbl.setVisible(true);
      }
    }
  }
}
