package org.example.ui.view;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.ShrimpGameApp;

/**
 * The MainScreen abstract class represents the main screen of the Shrimp Game application. It
 * contains
 * a static method, getMainScreen(), which returns a Scene object representing the main screen.
 * This class is responsible for displaying the welcome message, providing buttons to join a game,
 * become an admin, and quit the game, and setting the background image of the main screen.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public abstract class MainScreen {

  /**
   * Returns a Scene object representing the main screen of the Shrimp Game application.
   *
   * @param shrimpGameApp the ShrimpGameApp object used to set the scene and get other resources.
   * @return a Scene object representing the main screen.
   */
  public static Scene getMainScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(150, 0, 0, 0));
    Scene mainScene = new Scene(root, 800, 600);
    mainScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/styles.css").toExternalForm());

    Label welcomeLbl =
        new Label("Welcome to the Shrimp Game, " + shrimpGameApp.getUser().getName() + "!");
    welcomeLbl.getStyleClass().add("title-label");

    Button joinGameBtn = new Button("JOIN GAME");
    joinGameBtn.setOnAction(event ->
                            {
                              shrimpGameApp.setScene(shrimpGameApp.getJoinGameScreen());
                            });
    joinGameBtn.setPrefWidth(320);
    joinGameBtn.setPrefHeight(80);

    Button becomeAdminBtn = new Button("BECOME ADMIN");
    becomeAdminBtn.setOnAction(
        event -> shrimpGameApp.getMainMenuScreenController().handleBecomeAdminButton());
    becomeAdminBtn.setPrefWidth(320);
    becomeAdminBtn.setPrefHeight(80);

    Button quitBtn = new Button("QUIT");
    quitBtn.setOnAction(event ->
                        {
                          event.consume(); // prevent default close behavior
                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                          shrimpGameApp.addIconToDialog(alert);
                          alert.setTitle("Confirm Exit");
                          alert.setHeaderText("Are you sure you want to exit?");
                          alert.setContentText("Any unsaved changes will be lost.");
                          Optional<ButtonType> result = alert.showAndWait();
                          if (result.get() == ButtonType.OK) {
                            System.exit(0);
                            Platform.exit();
                          }
                        });
    quitBtn.setPrefWidth(320);
    quitBtn.setPrefHeight(80);

    Region spacer1 = new Region();
    Region spacer2 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    VBox.setVgrow(spacer2, Priority.ALWAYS);


    root.getChildren().addAll(spacer1, welcomeLbl, joinGameBtn, becomeAdminBtn, quitBtn, spacer2);
    root.setAlignment(Pos.CENTER);

    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/main_menu.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));
    return mainScene;
  }
}
