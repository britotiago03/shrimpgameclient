package org.example.ui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.model.Game;
import org.example.model.Player;

/**
 * This class represents the game started screen of the Shrimp Game application.
 */
public abstract class GameStartedScreen {
  /**
   * Returns a {@link javafx.scene.Scene Scene} object representing the game started screen 
   * of the Shrimp Game application.
   *
   * @param shrimpGameApp the {@link ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the game started screen.
   */
  public static Scene getGameStartedScene(ShrimpGameApp shrimpGameApp) {
    Game game = shrimpGameApp.getGame();
    Map<String, Player> otherPlayers = new HashMap<>(game.getPlayers());
    otherPlayers.remove(shrimpGameApp.getUser().getName(),
                        otherPlayers.get(shrimpGameApp.getUser().getName()));
    Iterator<Player> iterator = otherPlayers.values().iterator();

    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 50, 50, 50));
    Scene gameStartedScene = new Scene(root, 800, 600);
    gameStartedScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/tutorial.css").toExternalForm());

    Label titleLbl = new Label("Game Start");
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
    titleLbl.getStyleClass().add("title-label");

    // Create a GridPane with 2 columns
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(20);

    // Create the image of the mayor
    ImageView mayorImage = new ImageView(
        new Image(shrimpGameApp.getClass().getResourceAsStream("/images/mayor.png")));
    mayorImage.setFitWidth(200);
    mayorImage.setPreserveRatio(true);
    mayorImage.setSmooth(true);

    // Create the label with the game introduction
    Label introLbl = new Label(
        "Welcome to the Shrimp Game, " + shrimpGameApp.getUser().getName() + "!\n\nIn this game, "
        + "you are a fisherman competing against" + " other " + "fishermen on " + game.getName()
        + " Island " + game.getNumber() + ".\n\n"
        + "Your goal is to make the most profit possible each round.\n\n"
        + "The other fishermen you are competing against are " + iterator.next().getName() + " and "
        + iterator.next().getName() + ".\n\n" + "The game consists of " + game.getSettings()
                                                                              .getNumberOfRounds()
        + " rounds, with each round lasting for " + game.getSettings().getRoundTime() + " seconds"
        + ".\n\nDuring each round, you must decide how much shrimp you want to catch this round "
        + "within the time limit.\n\n If you fail to decide how much shrimp you want to catch "
        + "before the time limit, you will automatically catch the minimum amount of shrimp.\n\n "
        + "You can catch a minimum of " + game.getSettings().getMinShrimpKilograms() + "kg of shrimp"
        + " and a maximum of " + game.getSettings().getMaxShrimpKilograms() + "kg of shrimp each "
        + "round.");
    introLbl.setFont(Font.font("Helvetica", 20));
    introLbl.setWrapText(true);
    introLbl.setPadding(new Insets(20));
    introLbl.getStyleClass().add("text");


    // Create the scroll pane and add the label to it
    ScrollPane introScrollPane = new ScrollPane();
    introScrollPane.setContent(introLbl);
    introScrollPane.setFitToWidth(true);
    introScrollPane.getStyleClass().add("scroll-pane");

    // Create the continue button
    Button continueBtn = new Button("CONTINUE");
    continueBtn.setPrefWidth(320);
    continueBtn.setPrefHeight(80);
    continueBtn.setOnAction(event ->
                            {
                              String[] communicationRounds =
                                  shrimpGameApp.getGame().getSettings().getCommunicationRounds().split("\\+");
                              List<Integer> commRoundNums = new ArrayList<Integer>();
                              for (String communicationRound : communicationRounds) {
                                commRoundNums.add(Integer.parseInt(communicationRound));
                              }
                              if (commRoundNums.contains(shrimpGameApp.getGame().getCurrentRoundNum()))
                              {
                                GameScreen.setOPTION("Chat");
                                shrimpGameApp.initGameScreens();
                                shrimpGameApp.setScene(shrimpGameApp.getGameScreen());
                              }
                              else if (shrimpGameApp.getGame().getCurrentRoundNum()
                                       <= shrimpGameApp.getGame().getSettings().getNumberOfRounds()) {
                                GameScreen.setOPTION("Overview");
                                shrimpGameApp.initGameScreens();
                                shrimpGameApp.setScene(shrimpGameApp.getGameScreen());
                              }
                            });

    // Add the man image and the scroll pane to the GridPane
    grid.add(mayorImage, 0, 0);
    grid.add(introScrollPane, 1, 0);

    root.getChildren().addAll(titleLbl, grid, continueBtn);

    // Set the background image
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/market.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));
    root.setAlignment(Pos.CENTER);

    return gameStartedScene;
  }
}