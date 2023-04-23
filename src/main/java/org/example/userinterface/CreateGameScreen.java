package org.example.userinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;

/**
 * This class represents the Create Game screen of the Shrimp Game application, allowing the user to
 * create a new game lobby with various customizable settings such as the lobby name, maximum number
 * of players, number of rounds, round time, minimum and maximum amount of shrimp to catch.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public abstract class CreateGameScreen {

  /**
   * Returns a new Scene representing the Create Game screen, with various input fields allowing
   * the user
   * to customize the game lobby settings.
   *
   * @param shrimpGameApp The instance of the ShrimpGameApp class representing the application.
   * @return A new Scene representing the Create Game screen.
   */
  public static Scene getCreateGameScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    Scene createGameScene = new Scene(root, 800, 600);
    createGameScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/styles.css").toExternalForm());
    Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

    Label headingLbl = new Label("Create New Game");
    headingLbl.setFont(helvetica);
    headingLbl.setPadding(new Insets(20, 0, 0, 0));
    headingLbl.getStyleClass().add("title-label");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);

    Label gameLobbyNameLabel = new Label("Game lobby name:");
    TextField gameLobbyNameTextField = new TextField();
    gameLobbyNameTextField.setPrefWidth(320);
    gameLobbyNameTextField.setMaxWidth(320);
    grid.add(gameLobbyNameLabel, 0, 0);
    grid.add(gameLobbyNameTextField, 1, 0);

    Label maxPlayersLbl = new Label("Max players:");
    TextField maxPlayersTextField = new TextField();
    maxPlayersTextField.setPrefWidth(320);
    maxPlayersTextField.setMaxWidth(320);
    grid.add(maxPlayersLbl, 0, 1);
    grid.add(maxPlayersTextField, 1, 1);

    Label numRoundsLbl = new Label("Number of rounds:");
    TextField numRoundsTextField = new TextField();
    numRoundsTextField.setPrefWidth(320);
    numRoundsTextField.setMaxWidth(320);
    grid.add(numRoundsLbl, 0, 2);
    grid.add(numRoundsTextField, 1, 2);

    Label roundTimeLbl = new Label("Round time in seconds:");
    TextField roundTimeTextField = new TextField();
    roundTimeTextField.setPrefWidth(320);
    roundTimeTextField.setMaxWidth(320);
    grid.add(roundTimeLbl, 0, 3);
    grid.add(roundTimeTextField, 1, 3);

    Label commRoundsLbl = new Label("Communication rounds (Format: 1, 4, 5):");
    TextField commRoundsTextField = new TextField();
    commRoundsTextField.setPrefWidth(320);
    commRoundsTextField.setMaxWidth(320);
    grid.add(commRoundsLbl, 0, 4);
    grid.add(commRoundsTextField, 1, 4);

    Label commRoundTimeLbl = new Label("Communication time in seconds:");
    TextField commRoundTimeTextField = new TextField();
    commRoundTimeTextField.setPrefWidth(320);
    commRoundTimeTextField.setMaxWidth(320);
    grid.add(commRoundTimeLbl, 0, 5);
    grid.add(commRoundTimeTextField, 1, 5);

    Label minShrimpLbl = new Label("Min amount of shrimp in kg to catch:");
    TextField minShrimpTextField = new TextField();
    minShrimpTextField.setPrefWidth(320);
    minShrimpTextField.setMaxWidth(320);
    grid.add(minShrimpLbl, 0, 6);
    grid.add(minShrimpTextField, 1, 6);

    Label maxShrimpLbl = new Label("Max amount of shrimp in kg to catch:");
    TextField maxShrimpTextField = new TextField();
    maxShrimpTextField.setPrefWidth(320);
    maxShrimpTextField.setMaxWidth(320);
    grid.add(maxShrimpLbl, 0, 7);
    grid.add(maxShrimpTextField, 1, 7);

    Label errorLbl = new Label();
    errorLbl.setTextFill(Color.RED);
    errorLbl.setVisible(false);

    Button createBtn = new Button("CREATE GAME");
    createBtn.setPrefWidth(320);
    createBtn.setPrefHeight(60);
    createBtn.setOnAction(event ->
                          {
                            shrimpGameApp.getCreateGameScreenController().handleCreateGameButton(
                                gameLobbyNameTextField, maxPlayersTextField, numRoundsTextField,
                                roundTimeTextField, commRoundsTextField, commRoundTimeTextField,
                                minShrimpTextField, maxShrimpTextField, errorLbl);
                          });
    Button cancelBtn = new Button("CANCEL");
    cancelBtn.setPrefWidth(320);
    cancelBtn.setPrefHeight(60);
    cancelBtn.setOnAction(event -> shrimpGameApp.setScene(shrimpGameApp.getMainScreen()));
    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);

    root.getChildren().addAll(spacer1, headingLbl, grid, errorLbl, createBtn, cancelBtn, spacer2);
    root.setAlignment(Pos.CENTER);

    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));
    return createGameScene;
  }
}
