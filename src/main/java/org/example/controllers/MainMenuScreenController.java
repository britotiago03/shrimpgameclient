package org.example.controllers;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.ShrimpGameApp;
import org.example.network.ServerConnection;

/**
 * The MainMenuScreenController class is responsible for managing the main menu screen of the
 * Shrimp Game application.
 * It contains fields and methods for handling user input related to the main menu screen and
 * delegating these actions
 * to the appropriate components, such as the GameController and ServerConnection objects.
 */
public class MainMenuScreenController
{
    private ShrimpGameApp shrimpGameApp;
    private String username;
    private ServerConnection serverConnection;

    /**
     * Initializes a MainMenuScreenController with references to a MainMenuScreen,
     * ServerConnection, and GameController object.
     *
     * @param serverConnection a ServerConnection object used to send and receive messages to and
     *                         from the server
     */
    public MainMenuScreenController(ShrimpGameApp shrimpGameApp, ServerConnection serverConnection,
                                    String username)
    {
        this.shrimpGameApp = shrimpGameApp;
        this.username = username;
        this.serverConnection = serverConnection;
    }

    public String getUsername()
    {
        return this.username;
    }


    /**
     * Handles the action of the join game button when clicked by delegating to the
     * GameController object.
     */
    public void handleJoinGameButton()
    {
    }

    /**
     * Handles the action of the become admin button when clicked by delegating to the
     * GameController object.
     */
    public void handleBecomeAdminButton()
    {
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
                                      if (dialogButton == loginButtonType)
                                      {
                                          return passwordField.getText();
                                      }
                                      return null;
                                  });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            try
            {
                this.serverConnection.connect();
                this.serverConnection.sendBecomeAdminRequest(result.get());
                String serverResponse = this.serverConnection.receive();
                if (serverResponse.equals("BECOME_ADMIN_SUCCESSFUL"))
                {
                    this.shrimpGameApp.setIsAdmin(true);
                    this.shrimpGameApp.setScene(this.shrimpGameApp.getMainScene());
                }
                else if (serverResponse.equals("BECOME_ADMIN_FAILED"))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Password");
                    alert.setHeaderText(null);
                    alert.setContentText(
                        "The password you entered is incorrect. Please try again.");
                    this.shrimpGameApp.addIconToDialog(alert);
                    alert.showAndWait();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Server Connection");
                    alert.setHeaderText(null);
                    alert.setContentText(
                        "Failed to receive a response from the server. Make sure you"
                        + " are connected to the Internet!");
                    this.shrimpGameApp.addIconToDialog(alert);
                    alert.showAndWait();
                }
            }
            catch (RuntimeException exception)
            {
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