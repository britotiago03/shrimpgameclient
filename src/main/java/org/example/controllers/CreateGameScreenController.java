package org.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.ShrimpGameApp;
import org.w3c.dom.Text;

/**
 * The CreateGameScreenController class controls the behavior of the Create Game screen, where
 * players can create a new game lobby by entering specific game settings. The class validates the
 * user input, creates a game lobby upon receiving valid input and displays error messages for
 * invalid input or failures in the game creation process.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public class CreateGameScreenController {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates an instance of the CreateGameScreenController class.
   *
   * @param shrimpGameApp the ShrimpGameApp instance to associate with the controller instance.
   */
  public CreateGameScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles the "Create Game" button press by validating user input, displaying error messages for
   * invalid input or failures in the game creation process, and creating a game lobby upon
   * receiving valid input.
   *
   * @param gameLobbyNameField the TextField for entering the game lobby name.
   * @param maxPlayersField    the TextField for entering the maximum number of players.
   * @param numRoundsField     the TextField for entering the number of rounds.
   * @param roundTimeField     the TextField for entering the round time.
   * @param minShrimpField     the TextField for entering the minimum amount of shrimp to catch.
   * @param maxShrimpField     the TextField for entering the maximum amount of shrimp to catch.
   * @param errorLbl           the Label for displaying error messages.
   */
  public void handleCreateGameButton(TextField gameLobbyNameField, TextField maxPlayersField,
                                     TextField numRoundsField, TextField roundTimeField,
                                     TextField commRoundsTextField, TextField minShrimpField,
                                     TextField maxShrimpField, Label errorLbl) {
    if (gameLobbyNameField.getText().isEmpty() || maxPlayersField.getText().isEmpty()
        || numRoundsField.getText().isEmpty() || roundTimeField.getText().isEmpty()
        || minShrimpField.getText().isEmpty() || maxShrimpField.getText().isEmpty()) {
      errorLbl.setText("Please fill all fields.");
      errorLbl.setVisible(true);
    }
    else {
      try {
        int maxPlayers = Integer.parseInt(maxPlayersField.getText());
        int numRounds = Integer.parseInt(numRoundsField.getText());
        int roundTime = Integer.parseInt(roundTimeField.getText());
        int minShrimp = Integer.parseInt(minShrimpField.getText());
        int maxShrimp = Integer.parseInt(maxShrimpField.getText());
        String communicationRounds = commRoundsTextField.getText().replace(" ", "");
        if (Arrays.stream(gameLobbyNameField.getText().split(" ")).toList().size() > 1) {
          throw new IllegalArgumentException("Game lobby name cannot be multiple words");
        }
        else if (gameLobbyNameField.getText().matches("(.*)\\d(.*)")) {
          throw new IllegalArgumentException("Game lobby name cannot contain numbers");
        }
        else if (maxPlayers % 3 != 0) {
          throw new IllegalArgumentException("Max players has to be divisible by 3");
        }
        else if (numRounds < 2 || numRounds > 10) {
          throw new IllegalArgumentException("Number of rounds has to be between 2 and 10");
        }
        else if (roundTime < 30 || roundTime > 120) {
          throw new IllegalArgumentException("Round time has to be between 30 and 120 seconds");
        }
        else if (communicationRounds.matches("(.*)[^\\d,](.*)"))
        {
          throw new IllegalArgumentException(
              "Communication rounds cannot contain characters.");
        }
        else if (minShrimp < 0 || minShrimp > 30) {
          throw new IllegalArgumentException(
              "Min shrimp to catch has to be between 0 and 30 pounds");
        }
        else if (maxShrimp < 50 || maxShrimp > 80) {
          throw new IllegalArgumentException(
              "Max shrimp to catch has to be between 50 and 80 pounds");
        }
        List<String> rounds = Arrays.stream(communicationRounds.split(",")).toList();
        for (String round : rounds)
        {
          if (Integer.parseInt(round) < 1)
          {
            throw new IllegalArgumentException(
                "There are no negative rounds in the game");
          }
          else if (Integer.parseInt(round) > numRounds)
          {
            throw new IllegalArgumentException(
                "The last round in the game is round " + numRounds);
          }
        }
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Create Game");
        confirmDialog.setHeaderText("Confirm Settings:");
        confirmDialog.setContentText(String.format(
            "Game Name: %s%nMax Players: %d%nNumber of Rounds: "
            + "%d%nRound Time: %d seconds%nCommunication rounds: %s%nMinimum Shrimp "
            + "Pounds to Catch: %dkg%nMaximum Shrimp Pounds to Catch: %dkg%n",
            gameLobbyNameField.getText(), maxPlayers, numRounds, roundTime, communicationRounds, minShrimp,
            maxShrimp));
        this.shrimpGameApp.addIconToDialog(confirmDialog);
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
          try {
            this.shrimpGameApp.getServerConnection().sendCreateLobbyRequest(
                gameLobbyNameField.getText(), maxPlayers, numRounds, roundTime, communicationRounds, minShrimp,
                maxShrimp);
            String response = this.shrimpGameApp.getServerConnection().getNextServerPacket();

            if (response.equals("CREATE_LOBBY_SUCCESS")) {
              // Lobby created successfully
              Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
              successDialog.setTitle("Success");
              successDialog.setHeaderText(null);
              successDialog.setContentText("Game lobby created successfully!");
              // Reset the text fields
              gameLobbyNameField.setText("");
              maxPlayersField.setText("");
              numRoundsField.setText("");
              roundTimeField.setText("");
              minShrimpField.setText("");
              maxShrimpField.setText("");
              this.shrimpGameApp.addIconToDialog(successDialog);
              successDialog.showAndWait();
              this.shrimpGameApp.setScene(this.shrimpGameApp.getMainScreen());
            }
            else {
              // Failed to create lobby
              throw new RuntimeException("Failed to create game lobby.");
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
        errorLbl.setText("Invalid input.");
        errorLbl.setVisible(true);
      }
      catch (IllegalArgumentException exception) {
        errorLbl.setText(exception.getMessage());
        errorLbl.setVisible(true);
      }
    }
  }


}
