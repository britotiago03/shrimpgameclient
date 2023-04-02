package org.example.userinterface;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * The JoinGameScreen abstract class represents the screen for users to join a game lobby in the
 * Shrimp Game
 * application. It contains a static method, getJoinGameScreen(), which returns a Scene object
 * representing
 * the join game screen. This class is responsible for displaying the lobby list, providing a
 * button to join a
 * selected lobby, and setting the background image of the join game screen.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public abstract class JoinGameScreen {

  /**
   * Returns a Scene object representing the screen for users to join a game lobby in the Shrimp
   * Game application.
   *
   * @param shrimpGameApp the ShrimpGameApp object used to set the scene and get other resources.
   * @return a Scene object representing the join game screen.
   */
  public static Scene getJoinGameScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 0, 50, 0));
    Scene lobbyListScene = new Scene(root, 800, 600);
    lobbyListScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/styles.css").toExternalForm());
    Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

    Label headingLbl = new Label("Lobby List");
    headingLbl.setFont(helvetica);
    headingLbl.getStyleClass().add("title-label");

    TableView<Lobby> lobbyTableView = shrimpGameApp.getJoinGameLobbyTableView();
    shrimpGameApp.setLobbyTableView(lobbyTableView);

    Button joinBtn = new Button("JOIN");
    joinBtn.setPrefWidth(320);
    joinBtn.setPrefHeight(80);
    joinBtn.setOnAction(event ->
                        {
                          Lobby selectedLobby =
                              lobbyTableView.getSelectionModel().getSelectedItem();
                          if (selectedLobby != null) {
                            shrimpGameApp.getJoinGameScreenController().handleJoinButton(
                                selectedLobby);
                          }
                        });

    Button returnBtn = new Button("RETURN");
    returnBtn.setPrefWidth(320);
    returnBtn.setPrefHeight(80);
    returnBtn.setOnAction(event ->
                          {
                            shrimpGameApp.setScene(shrimpGameApp.getMainScreen());
                          });

    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);

    root.getChildren().addAll(spacer1, headingLbl, lobbyTableView, joinBtn, returnBtn, spacer2);
    root.setAlignment(Pos.CENTER);

    // Set the background image for the lobby list screen
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));


    return lobbyListScene;
  }
}
