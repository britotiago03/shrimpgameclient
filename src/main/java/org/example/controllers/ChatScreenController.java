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
    } else {
      try {
        String messageTxt = messageTxtArea.getText();
        messageTxtArea.clear();
        String[] input = messageTxt.split(" ");
        StringBuilder message = new StringBuilder();
        for (int index = 0; index < input.length; index++) {
          message.append(input[index]);
          if (index < input.length - 1) {
            message.append(".");
          }
        }
        String trimmedMessage = message.toString().trim();

        if (trimmedMessage.length() > 300) {
          throw new IllegalArgumentException("Message cannot be greater than 300 characters.");
        }
        try {
          this.shrimpGameApp.getServerConnection().sendMessageRequest(trimmedMessage);
          try {
            Thread.sleep(200);
          } catch (InterruptedException exception) {
            throw new RuntimeException("Thread was interrupted.");
          }
          this.shrimpGameApp.getServerConnection().getNextServerPacket();
        } catch (RuntimeException exception) {
          Alert errorDialog = new Alert(Alert.AlertType.ERROR);
          errorDialog.setTitle("Error");
          errorDialog.setHeaderText(null);
          errorDialog.setContentText(exception.getMessage());
          this.shrimpGameApp.addIconToDialog(errorDialog);
          errorDialog.showAndWait();
        }
      } catch (IllegalArgumentException exception) {
        errorLbl.setText(exception.getMessage());
        errorLbl.setVisible(true);
      }
    }
  }
}
