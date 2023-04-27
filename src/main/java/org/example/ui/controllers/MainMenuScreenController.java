package org.example.ui.controllers;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.example.ShrimpGameApp;

/**
 * This class serves as the controller for the main menu screen of the Shrimp Game application.
 * It handles the user interface events and interactions for the main menu screen, such as
 * becoming an administrator by entering the admin password.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public class MainMenuScreenController {
  private final ShrimpGameApp shrimpGameApp;


  /**
   * Constructor for the MainMenuScreenController class.
   *
   * @param shrimpGameApp The main application instance.
   */
  public MainMenuScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles the event when the user clicks the "Become Administrator" button on the main menu
   * screen.
   * A dialog window will be displayed to ask the user to enter the admin password.
   * If the password is correct, the user will be granted admin privileges and directed to the
   * main menu screen.
   * If the password is incorrect, an error message will be displayed.
   */
  public void handleBecomeAdminButton() {
    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Become Administrator");
    dialog.setHeaderText("To become an administrator, please enter the admin password:");

    // Set the dialog icon
    this.shrimpGameApp.addIconToDialog(dialog);

    // Set the button types
    ButtonType loginButtonType = new ButtonType("OK");
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    // Create the password field
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Password");
    GridPane.setHgrow(passwordField, Priority.ALWAYS);
    GridPane.setFillWidth(passwordField, true);

    // Create the grid pane and add the password field to it
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setMaxWidth(Region.USE_PREF_SIZE);
    grid.add(new Label("Password:"), 0, 0);
    grid.add(passwordField, 1, 0);

    dialog.getDialogPane().setContent(grid);

    // Request focus on the password field by default
    Platform.runLater(passwordField::requestFocus);

    // Convert the result to a password string when the login button is clicked
    dialog.setResultConverter(dialogButton ->
                              {
                                if (dialogButton == loginButtonType) {
                                  return passwordField.getText();
                                }
                                return null;
                              });

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      try {
        this.shrimpGameApp.getServerConnection().connect();
        int serverPacketsSize =
            this.shrimpGameApp.getServerConnection().getServerPackets().size();
        this.shrimpGameApp.getServerConnection().sendBecomeAdminRequest(result.get());
        String serverResponse = "";
        synchronized (this.shrimpGameApp.getServerConnection().getServerPackets()) {
          while (this.shrimpGameApp.getServerConnection().getServerPackets().size()
                 == serverPacketsSize) {
            try {
              this.shrimpGameApp.getServerConnection().getServerPackets().wait();
            }
            catch (InterruptedException exception) {
              throw new RuntimeException("Thread was interrupted");
            }
          }
          serverResponse = this.shrimpGameApp.getServerConnection().getNextServerPacket();
        }
        if (serverResponse.equals("BECOME_ADMIN_SUCCESSFUL")) {
          this.shrimpGameApp.getUser().setIsAdmin(true);
          this.shrimpGameApp.setScene(this.shrimpGameApp.getMainScreen());
        }
        else if (serverResponse.equals("BECOME_ADMIN_FAILED")) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Incorrect Password");
          alert.setHeaderText(null);
          alert.setContentText("The password you entered is incorrect. Please try again.");
          this.shrimpGameApp.addIconToDialog(alert);
          alert.showAndWait();
        }
        else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No Server Connection");
          alert.setHeaderText(null);
          alert.setContentText("Failed to receive a response from the server. Make sure you"
                               + " are connected to the Internet!");
          this.shrimpGameApp.addIconToDialog(alert);
          alert.showAndWait();
        }
      }
      catch (RuntimeException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(exception.getMessage());
        this.shrimpGameApp.addIconToDialog(alert);
        alert.showAndWait();
      }
    }
  }
}