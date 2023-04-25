package org.example.userinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.logic.Lobby;

/**
 * This abstract class represents the joined game screen of the Shrimp Game App. It contains a
 * static method to create a scene for the joined game screen. The scene displays a list of lobbies the user
 * has joined and a label indicating that the user is waiting for enough players to join the lobby. The user can
 * leave the lobby by clicking on the "LEAVE LOBBY" button.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public abstract class JoinedGameScreen {
  /**
   * Returns a {@link javafx.scene.Scene Scene} object for the joined game screen of the Shrimp Game App.
   *
   * @param shrimpGameApp the {@link ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the joined game screen.
   */
  public static Scene getJoinedGameScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 0, 50, 0));
    Scene joinedLobbyScene = new Scene(root, 800, 600);
    joinedLobbyScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/styles.css").toExternalForm());
    Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

    Label headingLbl = new Label("Lobby List");
    headingLbl.setFont(helvetica);
    headingLbl.getStyleClass().add("title-label");

    TableView<Lobby> lobbyTableView = shrimpGameApp.getJoinedGameLobbyTableView();

    shrimpGameApp.setLobbyTableView(lobbyTableView);

    Label waitingLabel = new Label("Waiting for enough players to join the lobby");
    waitingLabel.setFont(helvetica);

    Button leaveBtn = new Button("LEAVE LOBBY");
    leaveBtn.setPrefWidth(320);
    leaveBtn.setPrefHeight(80);
    leaveBtn.setOnAction(event ->
                         {
                           shrimpGameApp.getJoinGameScreenController().handleLeaveButton();
                         });

    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);

    root.getChildren().addAll(spacer1, headingLbl, lobbyTableView, waitingLabel, leaveBtn, spacer2);
    root.setAlignment(Pos.CENTER);

    // Set the background image for the lobby list screen
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));

    return joinedLobbyScene;
  }
}
