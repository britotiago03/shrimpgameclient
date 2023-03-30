package org.example.controllers;

import java.util.Arrays;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.ShrimpGameApp;
import org.example.network.ServerConnection;

/**
 * The CreateGameScreenController class manages the CreateGameScreen UI and handles user input
 * and button presses
 * <p>
 * related to the Create Game screen. It receives a GameController object as a parameter in its
 * constructor and
 * <p>
 * initializes the CreateGameScreen object with the GameController object. The class provides
 * methods for displaying
 * <p>
 * the CreateGameScreen, setting the action to be performed when the create game button is
 * clicked, and getting the
 * <p>
 * game name, password, number of rounds, and time per round selected by the user in the
 * CreateGameScreen. It also
 * <p>
 * includes a method to create a new Game object using the game name and password entered by the
 * user in the
 * <p>
 * CreateGameScreen.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class CreateGameScreenController
{
    private ShrimpGameApp shrimpGameApp;
    private ServerConnection serverConnection;

    /**
     * Constructs a CreateGameScreenController object with the specified GameController object
     * and initializes the
     * createGameScreen field with a new CreateGameScreen object.
     */
    public CreateGameScreenController(ShrimpGameApp shrimpGameApp,
                                      ServerConnection serverConnection)
    {
        this.shrimpGameApp = shrimpGameApp;
        this.serverConnection = serverConnection;
    }


    /**
     * Handles the action of the create game button when clicked by delegating to the
     * GameController object.
     */
    public void handleCreateGameButton(TextField gameLobbyNameField, TextField maxPlayersField,
                                       TextField numRoundsField, TextField roundTimeField,
                                       TextField minShrimpField, TextField maxShrimpField,
                                       Label errorLbl)
    {
        if (gameLobbyNameField.getText().isEmpty() || maxPlayersField.getText().isEmpty()
            || numRoundsField.getText().isEmpty() || roundTimeField.getText().isEmpty()
            || minShrimpField.getText().isEmpty() || maxShrimpField.getText().isEmpty())
        {
            errorLbl.setText("Please fill all fields.");
            errorLbl.setVisible(true);
        }
        else
        {
            try
            {
                int maxPlayers = Integer.parseInt(maxPlayersField.getText());
                int numRounds = Integer.parseInt(numRoundsField.getText());
                int roundTime = Integer.parseInt(roundTimeField.getText());
                int minShrimp = Integer.parseInt(minShrimpField.getText());
                int maxShrimp = Integer.parseInt(maxShrimpField.getText());
                if (Arrays.stream(gameLobbyNameField.getText().split(" ")).toList().size() > 1)
                {
                    throw new IllegalArgumentException("Game lobby name cannot be multiple words");
                }
                else if (gameLobbyNameField.getText().matches("(.*)\\d(.*)"))
                {
                    throw new IllegalArgumentException("Game lobby name cannot contain numbers");
                }
                else if (maxPlayers < 3 || maxPlayers > 10)
                {
                    throw new IllegalArgumentException("Max players has to be between 3 and 10");
                }
                else if (numRounds < 4 || numRounds > 10)
                {
                    throw new IllegalArgumentException(
                        "Number of rounds has to be between 4 and 10");
                }
                else if (roundTime < 30 || roundTime > 120)
                {
                    throw new IllegalArgumentException(
                        "Round time has to be between 30 and 120 seconds");
                }
                else if (minShrimp < 10 || minShrimp > 30)
                {
                    throw new IllegalArgumentException(
                        "Minimum shrimp to catch per round has to be between 10 and 30 pounds");
                }
                else if (maxShrimp < 50 || maxShrimp > 80)
                {
                    throw new IllegalArgumentException(
                        "Maximum shrimp to catch per round has to be between 50 and 80 pounds");
                }
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Create Game");
                confirmDialog.setHeaderText("Confirm Settings:");
                confirmDialog.setContentText(String.format(
                    "Game Name: %s%nMax Players: %d%nNumber of Rounds: "
                    + "%d%nRound Time: %d seconds%nMinimum Shrimp "
                    + "Pounds to Catch: %dkg%nMaximum Shrimp Pounds to Catch: %dkg%n",
                    gameLobbyNameField.getText(), maxPlayers, numRounds, roundTime, minShrimp,
                    maxShrimp));
                this.shrimpGameApp.addIconToDialog(confirmDialog);
                Optional<ButtonType> result = confirmDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    try
                    {
                        this.serverConnection.sendCreateLobbyRequest(gameLobbyNameField.getText(),
                                                                     maxPlayers, numRounds,
                                                                     roundTime, minShrimp,
                                                                     maxShrimp);
                        String response = this.serverConnection.receive();

                        if (response.equals("CREATE_LOBBY_SUCCESS"))
                        {
                            // Lobby created successfully
                            Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
                            successDialog.setTitle("Success");
                            successDialog.setHeaderText(null);
                            successDialog.setContentText("Game lobby created successfully!");
                            this.shrimpGameApp.addIconToDialog(successDialog);
                            successDialog.showAndWait();
                        }
                        else
                        {
                            // Failed to create lobby
                            throw new RuntimeException("Failed to create game lobby.");
                        }
                    }
                    catch (RuntimeException exception)
                    {
                        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                        errorDialog.setTitle("Error");
                        errorDialog.setHeaderText(null);
                        errorDialog.setContentText(exception.getMessage());
                        this.shrimpGameApp.addIconToDialog(errorDialog);
                        errorDialog.showAndWait();
                    }
                    // TODO: Create game with the given settings and
                    //  show success message
                }
            }
            catch (NumberFormatException exception)
            {
                errorLbl.setText("Invalid input.");
                errorLbl.setVisible(true);
            }
            catch (IllegalArgumentException exception)
            {
                errorLbl.setText(exception.getMessage());
                errorLbl.setVisible(true);
            }
        }
        this.shrimpGameApp.setScene(this.shrimpGameApp.getMainScene());
    }


}
