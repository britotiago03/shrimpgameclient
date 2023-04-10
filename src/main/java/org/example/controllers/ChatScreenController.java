package org.example.controllers;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.ShrimpGameApp;

public class ChatScreenController {
  private final ShrimpGameApp shrimpGameApp;


  public ChatScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }


  public void handleSendButton(TextArea messageTxtArea, Label errorLbl) {
    if (messageTxtArea.getText().isEmpty()) {
      errorLbl.setText("Please enter a message.");
      errorLbl.setVisible(true);
    }
    else {
      try {
        String messageTxt = messageTxtArea.getText();
        String[] input = messageTxt.split(" ");
        StringBuilder message = new StringBuilder();
        for (int index = 0; index < input.length; index++)
        {
          message.append(input[index] + ".");
        }
        String trimmedMessage = messageTxt.trim();

        if (trimmedMessage.length() > 50) {
          throw new IllegalArgumentException("Message cannot be greater than 50 characters.");
        }
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Send Message");
        confirmDialog.setHeaderText("Confirm Message To Send:");
        confirmDialog.setContentText(String.format("Message: %s", messageTxt));
        this.shrimpGameApp.addIconToDialog(confirmDialog);
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
          try {
            this.shrimpGameApp.getServerConnection().sendMessageRequest(message.toString());
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException exception) {
              throw new RuntimeException("Thread was interrupted.");
            }
            String response = this.shrimpGameApp.getServerConnection().getNextServerPacket();

            if (response.equals("MESSAGE_RECEIVED")) {
              Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
              successDialog.setTitle("Success");
              successDialog.setHeaderText(null);
              successDialog.setContentText("Sent message successfully!");

              messageTxtArea.setText("");
              this.shrimpGameApp.addIconToDialog(successDialog);
              try {
                Thread.sleep(500);
              }
              catch (InterruptedException exception) {
                throw new RuntimeException("Thread was interrupted.");
              }
              successDialog.showAndWait();
            }
            else {
              throw new RuntimeException("Failed to send message.");
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
      catch (IllegalArgumentException exception) {
        errorLbl.setText(exception.getMessage());
        errorLbl.setVisible(true);
      }
    }
  }
}
