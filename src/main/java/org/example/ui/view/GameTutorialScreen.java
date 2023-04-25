package org.example.ui.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;

/**
 * This class represents the game tutorial screen. It contains the tutorial content and a button to
 * return to the main screen.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public abstract class GameTutorialScreen {
  /**
   * Returns a {@link javafx.scene.Scene Scene} object representing the game tutorial screen.
   *
   * @param shrimpGameApp the instance of the {@link ShrimpGameApp} class.
   * @return a {@code Scene} object representing the game tutorial screen.
   */
  public static Scene getGameTutorialScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    Scene gameTutorialScene = new Scene(root, 800, 600);
    gameTutorialScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/tutorial.css").toExternalForm());

    Label titleLbl = new Label("Game Tutorial");
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
    titleLbl.getStyleClass().add("title-label");
    titleLbl.setPadding(new Insets(20, 50, 20, 50));

    VBox lblContainer = new VBox();
    lblContainer.setPadding(new Insets(0, 50, 0, 50));
    // Add the tutorial content
    String tutorialText =
        "Welcome to Shrimp Game, an exciting and strategic multiplayer game where you get to run "
        + "your own shrimp fishing business!\n\n"
        + "The goal of the game is to make as much money as possible by catching and selling "
        + "shrimp at the market.\n\n"
        + "But be careful, the market price for shrimp can fluctuate wildly, and your "
        + "competitors may try to sabotage your business.\n\n"
        + "You will need to manage your resources carefully and make smart business decisions to "
        + "succeed.";
    Label tutorialLbl = new Label(tutorialText);
    tutorialLbl.setFont(Font.font("Helvetica", 20));
    tutorialLbl.setWrapText(true);
    tutorialLbl.setTextFill(Color.WHITE); // set the text fill to white
    tutorialLbl.setStyle(
        " -fx-effect: dropshadow" + "(gaussian, rgba(0, 0, 0, 0.8), 30, 0.5, 0, 0);");
    lblContainer.getChildren().add(tutorialLbl);

    VBox buttonContainer = new VBox();
    buttonContainer.setPadding(new Insets(20, 50, 20, 50));
    Button continueBtn = new Button("CONTINUE");
    continueBtn.setPrefWidth(320);
    continueBtn.setPrefHeight(80);
    continueBtn.setOnAction(event ->
                            {
                              shrimpGameApp.setScene(shrimpGameApp.getMainScreen());
                            });
    buttonContainer.getChildren().add(continueBtn);

    root.getChildren().addAll(titleLbl, lblContainer, buttonContainer);

    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));

    return gameTutorialScene;
  }
}
