package org.example.userinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.FontWeight;
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
   * Returns a {@code Scene} object representing the game tutorial screen.
   *
   * @param shrimpGameApp the instance of the {@code ShrimpGameApp} class
   * @return a {@code Scene} object representing the game tutorial screen
   */
  public static Scene getGameTutorialScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 50, 50, 50));
    Scene gameTutorialScene = new Scene(root, 800, 600);
    gameTutorialScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/tutorial.css").toExternalForm());

    Label titleLbl = new Label("How to Play");
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
    titleLbl.getStyleClass().add("title-label");

    // Add the tutorial content
    String tutorialText =
        "Welcome to the game tutorial! In this game, you will be presented with a series of "
        + "challenges that you must complete to progress to the next level.\n\n"
        + "To complete a challenge, you must use the arrow keys to move your character and "
        + "reach the goal. Along the way, you will encounter obstacles and enemies that you "
        + "must avoid or defeat using your special abilities.\n\n"
        + "You can view your progress and stats by clicking on the 'Stats' button on the main"
        + " menu. Good luck!";
    Label tutorialLbl = new Label(tutorialText);
    tutorialLbl.setFont(Font.font("Helvetica", 20));
    tutorialLbl.setWrapText(true);
    tutorialLbl.setMaxWidth(800);
    tutorialLbl.setAlignment(Pos.CENTER);
    tutorialLbl.setTextFill(Color.WHITE); // set the text fill to white
    tutorialLbl.setPadding(new Insets(0, 0, 20, 0));
    tutorialLbl.setStyle(
        " -fx-effect: dropshadow" + "(gaussian, rgba(0, 0, 0, 0.8), 30, 0.5, 0, 0);");

    Button continueBtn = new Button("CONTINUE");
    continueBtn.setPrefWidth(320);
    continueBtn.setPrefHeight(80);
    continueBtn.setOnAction(event ->
                            {
                              shrimpGameApp.setScene(shrimpGameApp.getMainScreen());
                            });
    root.getChildren().addAll(titleLbl, tutorialLbl, continueBtn);

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
