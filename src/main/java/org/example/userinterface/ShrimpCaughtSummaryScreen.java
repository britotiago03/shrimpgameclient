package org.example.userinterface;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.ShrimpGameApp;
import org.example.logic.Player;

public class ShrimpCaughtSummaryScreen {
  public static Scene getShrimpCaughtSummaryScreen(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/summaryScreen.css").toExternalForm());
    // Add title
    Label titleLbl = new Label("Shrimp Caught Summary");
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 32));
    titleLbl.getStyleClass().add("title-label");
    titleLbl.setPadding(new Insets(20, 0, 0, 0));

    // Add player information
    GridPane playersGrid = new GridPane();
    playersGrid.setStyle("-fx-background-color: white;");
    VBox gridContainer = new VBox();
    gridContainer.setAlignment(Pos.CENTER);
    gridContainer.getChildren().add(playersGrid);
    gridContainer.setPadding(new Insets(0, 100, 0, 100));
    playersGrid.setAlignment(Pos.CENTER);

    List<Player> players = new ArrayList<Player>();
    Player player1 = new Player("Willow", 5);
    Player player2 = new Player("Buoy", 5);
    Player player3 = new Player("Crabber", 5);
    player1.setShrimpCaught(30);
    player2.setShrimpCaught(50);
    player3.setShrimpCaught(75);
    players.add(player1);
    players.add(player2);
    players.add(player3);

    int tickUnit = 10;
    int maxShrimpCaught = 80;

    for (int i = 0; i < players.size(); i++) {
      Player player = players.get(i);
      String playerName = player.getName();
      int shrimpCaught = player.getShrimpCaught();

      VBox playerBox = new VBox();
      playerBox.setAlignment(Pos.CENTER);
      playerBox.setSpacing(10);

      Image playerImage = new Image(
          shrimpGameApp.getClass().getResourceAsStream("/images/player" + (i + 1) + ".png"));
      ImageView playerImageView = new ImageView(playerImage);
      playerImageView.setFitWidth(100);
      playerImageView.setFitHeight(100);

      Label playerNameLbl = new Label(playerName);
      playerNameLbl.getStyleClass().add("name-label");
      playerNameLbl.setPadding(new Insets(20, 0, 0, 0));

      CategoryAxis xAxis = new CategoryAxis();
      xAxis.setTickLabelFont(Font.font("Helvetica", FontWeight.BOLD, 20));
      xAxis.setTickLabelFill(Color.BLACK);
      NumberAxis yAxis = new NumberAxis(0, maxShrimpCaught, tickUnit);
      yAxis.setTickLabelFont(Font.font("Helvetica"));
      yAxis.setTickLabelFill(Color.BLACK);
      BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
      if (i == 0) {
        chart.getStyleClass().add("blue-bar-chart");
      }
      else if (i == 1) {
        chart.getStyleClass().add("green-bar-chart");
      }
      else if (i == 2) {
        chart.getStyleClass().add("red-bar-chart");
      }
      chart.setLegendVisible(false);
      chart.setPrefWidth(200);
      chart.setPadding(new Insets(20));
      chart.setVerticalGridLinesVisible(false);
      chart.setHorizontalGridLinesVisible(false);

      XYChart.Series<String, Number> series = new XYChart.Series<>();
      series.getData().add(new XYChart.Data<>(shrimpCaught + "kg", shrimpCaught));
      chart.getData().add(series);

      playerBox.getChildren().addAll(playerNameLbl, playerImageView, chart);

      playersGrid.add(playerBox, i, 0);
    }

    // Add continue button
    Button continueBtn = new Button("CONTINUE");
    VBox buttonContainer = new VBox();
    continueBtn.setPrefWidth(320);
    continueBtn.setPrefHeight(80);
    buttonContainer.getChildren().add(continueBtn);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(0, 0, 20, 0));
    continueBtn.setOnAction(e -> shrimpGameApp.setScene(shrimpGameApp.getMainScreen()));

    root.getChildren().addAll(titleLbl, gridContainer, buttonContainer);

    // Set the background image
    Image backgroundImage = new Image(
        shrimpGameApp.getClass().getResource("/images/market.jpg").toExternalForm());
    BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                     BackgroundRepeat.NO_REPEAT,
                                                     BackgroundPosition.CENTER, backgroundSize);
    root.setBackground(new Background(background));

    return scene;
  }
}