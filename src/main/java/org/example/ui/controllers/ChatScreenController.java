package org.example.ui.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.ShrimpGameApp;

/**
 * Represents the controller for {@code ChatScreen}.
 */
public class ChatScreenController {
  private final ShrimpGameApp shrimpGameApp;


  /**
   * Creates a new instance of {@code ChatScreenController}.
   * 
   * @param shrimpGameApp the {@code ShrimpGameApp} object used to get the game information.
   */
  public ChatScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles pressing the "Send" button.
   * 
   * @param messageTxtArea the text area for the message to be sent.
   * @param errorLbl the label displaying an error if the amount is invalid.
   */
  public void handleSendButton(TextArea messageTxtArea, Label errorLbl) {
    if (messageTxtArea.getText().isEmpty()) {
      errorLbl.setText("Please enter a message");
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
          this.shrimpGameApp.getServerConnection().getNextServerPacket();
          errorLbl.setVisible(false);
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
