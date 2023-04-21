package org.example.userinterface;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.logic.Game;
import org.example.logic.Player;

public abstract class CatchShrimpScreen {

  /**
   * Returns a Scene object representing the game started screen of the Shrimp Game application.
   *
   * @param shrimpGameApp the ShrimpGameApp object used to get the game information.
   * @return a Scene object representing the game started screen.
   */
  public static Scene getCatchShrimpScene(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(50, 50, 50, 50));
    Scene catchShrimpScene = new Scene(root, 800, 600);
    catchShrimpScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/catchShrimp.css").toExternalForm());

    Label titleLbl = new Label("Catch Shrimp");
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

    VBox catchShrimpBox = new VBox();
    catchShrimpBox.setSpacing(10);
    catchShrimpBox.setAlignment(Pos.CENTER);
    Label catchShrimpLbl = new Label(
        "How many kg of shrimp do you want to catch this round " + shrimpGameApp.getUser().getName()
        + "?");
    catchShrimpLbl.setWrapText(true);
    catchShrimpLbl.setPadding(new Insets(20));
    catchShrimpLbl.getStyleClass().add("catch-shrimp-label");
    TextArea catchShrimpTextArea = new TextArea();
    catchShrimpTextArea.setPrefHeight(50);

    Label errorLbl = new Label();
    errorLbl.setPadding(new Insets(10));
    errorLbl.getStyleClass().add("catch-shrimp-label");
    errorLbl.setTextFill(Color.RED);
    errorLbl.setVisible(false);

    catchShrimpBox.getChildren().addAll(catchShrimpLbl, catchShrimpTextArea, errorLbl);

    HBox buttonsBox = new HBox();
    buttonsBox.setSpacing(20);
    buttonsBox.setAlignment(Pos.CENTER);
    // Create the ok button
    Button okBtn = new Button("OK");
    okBtn.setPrefWidth(320);
    okBtn.setPrefHeight(80);
    okBtn.setOnAction(event -> shrimpGameApp.getCatchShrimpScreenController().handleOkButton(catchShrimpTextArea, errorLbl));

    Button cancelBtn = new Button("CANCEL");
    cancelBtn.setPrefWidth(320);
    cancelBtn.setPrefHeight(80);
    cancelBtn.setOnAction(event -> shrimpGameApp.setScene(shrimpGameApp.getGameScreen()));

    buttonsBox.getChildren().addAll(okBtn, cancelBtn);

    // Add the man image and the scroll pane to the GridPane
    grid.add(mayorImage, 0, 0);
    grid.add(catchShrimpBox, 1, 0);

    root.getChildren().addAll(titleLbl, grid, buttonsBox);

    // Set the background image
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/market.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));
    root.setAlignment(Pos.CENTER);

    return catchShrimpScene;
  }

}
