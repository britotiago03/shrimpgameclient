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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.model.GameResult;

public abstract class DownloadGameDataScreen {
  public static Scene getDownloadGameDataScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 0, 50, 0));
    Scene lobbyListScene = new Scene(root, 800, 600);
    lobbyListScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/styles.css").toExternalForm());
    Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

    Label headingLbl = new Label("Download Game Data");
    headingLbl.setFont(helvetica);
    headingLbl.getStyleClass().add("title-label");

    TableView<GameResult> gameResultTableView = shrimpGameApp.getGameResultTableView();
    shrimpGameApp.setGameResultTableView(gameResultTableView);

    Button downloadGameBtn = new Button("DOWNLOAD GAME");
    downloadGameBtn.setPrefWidth(350);
    downloadGameBtn.setPrefHeight(80);
    downloadGameBtn.setOnAction(event ->
                                {
                                  GameResult selectedGameResult =
                                      gameResultTableView.getSelectionModel().getSelectedItem();
                                  if (selectedGameResult != null) {
                                    shrimpGameApp.getDownloadGameDataScreenController()
                                                 .handleDownloadGameButton(selectedGameResult);
                                  }
                                });

    Button returnBtn = new Button("RETURN");
    returnBtn.setPrefWidth(350);
    returnBtn.setPrefHeight(80);
    returnBtn.setOnAction(event ->
                          {
                            shrimpGameApp.setScene(shrimpGameApp.getMainScreen());
                          });

    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);

    root.getChildren().addAll(spacer1, headingLbl, gameResultTableView, downloadGameBtn, returnBtn,
                              spacer2);
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
