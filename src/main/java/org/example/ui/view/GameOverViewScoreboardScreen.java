package org.example.ui.view;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.model.Round;

/**
 * This class represents the game over screen of the Shrimp Game application.
 */
public abstract class GameOverViewScoreboardScreen {
  /**
   * Returns a {@link javafx.scene.Scene Scene} object representing the game over screen
   * of the Shrimp Game application.
   *
   * @param shrimpGameApp the {@link ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the game over screen.
   */
  public static Scene getGameOverViewScoreboardScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 0, 50, 0));
    Scene gameOverScene = new Scene(root, 800, 600);
    gameOverScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/gameover.css").toExternalForm());
    Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

    Label headingLbl = new Label("Game Over");
    headingLbl.setFont(helvetica);
    headingLbl.getStyleClass().add("title-label");

    TableView<Round> gameOverScoreboardTableview = shrimpGameApp.getGameOverScoreboardTableview();
    if (!shrimpGameApp.isGameOverScoreboardTableviewInitialized()) {
      shrimpGameApp.setScoreboardTableView(gameOverScoreboardTableview);
      shrimpGameApp.setGameOverScoreboardTableviewInitialized(true);
    }

    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(20);

    Button viewChatBtn = new Button("VIEW CHAT");
    viewChatBtn.setPrefWidth(320);
    viewChatBtn.setPrefHeight(80);
    viewChatBtn.setOnAction(event ->
                            {
                              shrimpGameApp.getGameOverScreenController().handleViewChatButton();
                            });

    Button leaveGameBtn = new Button("LEAVE GAME");
    leaveGameBtn.setPrefWidth(320);
    leaveGameBtn.setPrefHeight(80);
    leaveGameBtn.setOnAction(event ->
                             {
                               shrimpGameApp.getGameOverScreenController().handleLeaveGameButton();
                             });

    buttonBox.getChildren().addAll(viewChatBtn, leaveGameBtn);

    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);

    root.getChildren().addAll(spacer1, headingLbl, gameOverScoreboardTableview, buttonBox, spacer2);
    root.setAlignment(Pos.CENTER);

    // Set the background image for the lobby list screen
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));


    return gameOverScene;
  }
}
