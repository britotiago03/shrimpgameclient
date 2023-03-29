package org.example.controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.network.ServerConnection;
import org.example.userinterface.MainMenuScreen;

/**
 * The MainMenuScreenController class is responsible for managing the main menu screen of the
 * Shrimp Game application.
 * It contains fields and methods for handling user input related to the main menu screen and
 * delegating these actions
 * to the appropriate components, such as the GameController and ServerConnection objects.
 */
public class MainMenuScreenController
{
    private MainMenuScreen mainMenuScreen;
    private ServerConnection serverConnection;
    private GameController gameController;

    /**
     * Initializes a MainMenuScreenController with references to a MainMenuScreen,
     * ServerConnection, and GameController object.
     *
     * @param mainMenuScreen   a MainMenuScreen object to display the main menu screen
     * @param serverConnection a ServerConnection object used to send and receive messages to and
     *                         from the server
     * @param gameController   a GameController object used to manage the game logic and user
     *                         interface
     */
    public MainMenuScreenController(MainMenuScreen mainMenuScreen,
                                    ServerConnection serverConnection,
                                    GameController gameController)
    {
        this.mainMenuScreen = mainMenuScreen;
        this.serverConnection = serverConnection;
        this.gameController = gameController;
    }

    public void showMainMenuScreen()
    {
        boolean isAdmin = false;
        this.mainMenuScreen.display(this, isAdmin);
    }

    /**
     * Handles the action of the create game button when clicked by delegating to the
     * GameController object.
     */
    public void handleCreateGameButton()
    {
        gameController.createGame("game1", "password");
    }

    /**
     * Handles the action of the join game button when clicked by delegating to the
     * GameController object.
     */
    public void handleJoinGameButton()
    {
        gameController.joinGame("gameId", "password");
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
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:./src/main/resources/Images/shrimp_logo.png"));


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
                boolean sentBecomeAdminMessage = this.serverConnection.sendBecomeAdminMessage(
                    result.get());
                if (sentBecomeAdminMessage)
                {
                    String serverResponse = this.serverConnection.receive();
                    if (serverResponse.equals("BECOME_ADMIN_SUCCESSFUL"))
                    {
                        this.mainMenuScreen.initAsAdmin(this);
                    }
                    else if (serverResponse.equals("BECOME_ADMIN_FAILED"))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Incorrect Password");
                        alert.setHeaderText(null);
                        alert.setContentText(
                            "The password you entered is incorrect. Please try again.");
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
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed To Check Password");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to check the password with the server. Make sure"
                                         + " you are connected to the Internet! ");
                    alert.showAndWait();
                }

            }
            catch (IOException exception)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed Connection With Server");
                alert.setHeaderText(null);
                alert.setContentText(
                    "Failed to connect to the server. Make sure you are connected to the Internet"
                    + " and that the server is running!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Handles the action of the exit button when clicked.
     */
    public void handleExitButton()
    {
        Platform.exit();
    }
}