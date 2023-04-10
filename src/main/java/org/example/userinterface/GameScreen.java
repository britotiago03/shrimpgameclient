package org.example.userinterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.logic.Player;
import org.example.logic.Round;

public abstract class GameScreen {
  public static Scene getMainScene(ShrimpGameApp shrimpGameApp, boolean hasCaughtShrimp) {
    BorderPane root = new BorderPane();
    Scene gameScene = new Scene(root, 800, 600);
    gameScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/game.css").toExternalForm());


    // Create the menu on the left
    VBox menuBox = new VBox();
    menuBox.setPadding(new Insets(10));
    menuBox.setAlignment(Pos.CENTER);
    menuBox.setSpacing(10);
    menuBox.getStyleClass().add("menu-box");

    Image overviewBtnImage = new Image(
        shrimpGameApp.getClass().getResourceAsStream("/images/overview_icon.png"));
    Label overviewBtnLabel = new Label("OVERVIEW");
    overviewBtnLabel.getStyleClass().add("menu-label");
    VBox overviewBtnBox = new VBox();
    overviewBtnBox.setSpacing(10);
    overviewBtnBox.setPrefWidth(100);
    overviewBtnBox.setPrefHeight(100);
    overviewBtnBox.setAlignment(Pos.CENTER);
    overviewBtnBox.getChildren().addAll(new ImageView(overviewBtnImage), overviewBtnLabel);
    Button overviewBtn = new Button();
    overviewBtn.setGraphic(overviewBtnBox);
    overviewBtn.getStyleClass().add("button1");
    overviewBtn.setOnAction(
        e -> root.setCenter(GameScreen.getContentPane(shrimpGameApp, "Overview", hasCaughtShrimp)));


    Image scoreboardBtnImage = new Image(
        shrimpGameApp.getClass().getResourceAsStream("/images/scoreboard_icon.png"));
    Label scoreboardBtnLabel = new Label("SCOREBOARD");
    scoreboardBtnLabel.getStyleClass().add("menu-label");
    VBox scoreboardBtnBox = new VBox();
    scoreboardBtnBox.setPrefWidth(100);
    scoreboardBtnBox.setPrefHeight(100);
    scoreboardBtnBox.setSpacing(10);
    scoreboardBtnBox.setAlignment(Pos.CENTER);
    scoreboardBtnBox.getChildren().addAll(new ImageView(scoreboardBtnImage), scoreboardBtnLabel);
    Button scoreboardBtn = new Button();
    scoreboardBtn.setGraphic(scoreboardBtnBox);
    scoreboardBtn.getStyleClass().add("button1");
    scoreboardBtn.setOnAction(e -> root.setCenter(
        GameScreen.getContentPane(shrimpGameApp, "Scoreboard", hasCaughtShrimp)));

    Image rulesBtnImage = new Image(
        shrimpGameApp.getClass().getResourceAsStream("/images/rules_icon.png"));
    Label rulesBtnLabel = new Label("RULES");
    rulesBtnLabel.getStyleClass().add("menu-label");
    VBox rulesBtnBox = new VBox();
    rulesBtnBox.setPrefWidth(100);
    rulesBtnBox.setPrefHeight(100);
    rulesBtnBox.setSpacing(10);
    rulesBtnBox.setAlignment(Pos.CENTER);
    rulesBtnBox.getChildren().addAll(new ImageView(rulesBtnImage), rulesBtnLabel);
    Button rulesBtn = new Button();
    rulesBtn.setGraphic(rulesBtnBox);
    rulesBtn.getStyleClass().add("button1");
    rulesBtn.setOnAction(
        e -> root.setCenter(GameScreen.getContentPane(shrimpGameApp, "Rules", hasCaughtShrimp)));

    Image chatBtnImage = new Image(
        shrimpGameApp.getClass().getResourceAsStream("/images/chat_icon.png"));
    Label chatBtnLabel = new Label("CHAT");
    chatBtnLabel.getStyleClass().add("menu-label");
    VBox chatBtnBox = new VBox();
    chatBtnBox.setPrefWidth(100);
    chatBtnBox.setPrefHeight(100);
    chatBtnBox.setSpacing(10);
    chatBtnBox.setAlignment(Pos.CENTER);
    chatBtnBox.getChildren().addAll(new ImageView(chatBtnImage), chatBtnLabel);
    Button chatBtn = new Button();
    chatBtn.setGraphic(chatBtnBox);
    chatBtn.getStyleClass().add("button1");
    chatBtn.setOnAction(
        e -> root.setCenter(GameScreen.getContentPane(shrimpGameApp, "Chat", hasCaughtShrimp)));


    menuBox.getChildren().addAll(overviewBtn, scoreboardBtn, rulesBtn, chatBtn);

    // Create the content on the right
    VBox contentPane = GameScreen.getContentPane(shrimpGameApp, "Overview", hasCaughtShrimp);

    // Add the menu and content to the root pane
    root.setLeft(menuBox);
    root.setCenter(contentPane);


    return gameScene;
  }

  private static VBox getContentPane(ShrimpGameApp shrimpGameApp, String option,
                                     boolean hasCaughtShrimp) {
    VBox content = new VBox();
    content.setPrefSize(600, 600);
    content.setAlignment(Pos.CENTER);
    List<Player> otherPlayers = new ArrayList<>(shrimpGameApp.getGame().getPlayers().values());
    Player player = shrimpGameApp.getGame().getPlayers().get(shrimpGameApp.getUser().getName());
    otherPlayers.remove(player);
    Iterator<Player> iterator = otherPlayers.iterator();

    // Create the content for each menu item
    if (option.equals("Overview")) {

      Label roundLbl = new Label("Round " + shrimpGameApp.getGame().getCurrentRoundNum());
      roundLbl.getStyleClass().add("title-label");
      roundLbl.setPadding(new Insets(20));


      GridPane roundInfo = new GridPane();
      roundInfo.setHgap(10);
      roundInfo.setStyle("-fx-background-color: white;");
      roundInfo.setAlignment(Pos.CENTER);
      roundInfo.setPadding(new Insets(10));
      Label roundTimeLbl = new Label("Time left:");
      roundTimeLbl.getStyleClass().add("info-label");
      roundInfo.add(roundTimeLbl, 0, 0);

      Label timeLeftLbl = new Label("01:57");
      timeLeftLbl.getStyleClass().add("info-label");
      roundInfo.add(timeLeftLbl, 1, 0);


      Label roundStatusLbl = new Label("Waiting for the other players...");
      roundStatusLbl.getStyleClass().add("status-label");


      GridPane playerStats = new GridPane();
      playerStats.setHgap(10);
      playerStats.setPadding(new Insets(10));
      playerStats.setAlignment(Pos.CENTER);
      playerStats.setStyle("-fx-background-color: white;");

      Label shrimpCaughtLbl = new Label("Amount of Shrimp Caught:");
      shrimpCaughtLbl.getStyleClass().add("info-label");
      playerStats.add(shrimpCaughtLbl, 0, 0);

      Label shrimpCaughtValueLbl = new Label(player.getShrimpCaught() + "kg");
      shrimpCaughtValueLbl.getStyleClass().add("info-label");
      playerStats.add(shrimpCaughtValueLbl, 1, 0);

      Label totalMoneyLbl = new Label("Total money:");
      totalMoneyLbl.getStyleClass().add("info-label");
      playerStats.add(totalMoneyLbl, 0, 1);

      Label totalMoneyValueLbl = new Label("$" + player.getPreviousTotalMoney());
      totalMoneyValueLbl.getStyleClass().add("info-label");
      playerStats.add(totalMoneyValueLbl, 1, 1);

      Image catchShrimpBtnImage = new Image(
          shrimpGameApp.getClass().getResourceAsStream("/images/catch_shrimp.png"));
      Label catchShrimpBtnLbl = new Label("CATCH SHRIMP");
      catchShrimpBtnLbl.getStyleClass().add("catch-shrimp");
      VBox catchShrimpBtnBox = new VBox();
      catchShrimpBtnBox.setPrefWidth(150);
      catchShrimpBtnBox.setPrefHeight(100);
      catchShrimpBtnBox.setSpacing(10);
      catchShrimpBtnBox.setAlignment(Pos.CENTER);
      catchShrimpBtnBox.getChildren().addAll(new ImageView(catchShrimpBtnImage), catchShrimpBtnLbl);
      Button catchShrimpBtn = new Button();
      catchShrimpBtn.setGraphic(catchShrimpBtnBox);
      catchShrimpBtn.getStyleClass().add("button2");
      catchShrimpBtn.setOnAction(e -> shrimpGameApp.setScene(shrimpGameApp.getCatchShrimpScreen()));

      HBox infoBox = new HBox();
      infoBox.setSpacing(10);
      infoBox.setPadding(new Insets(20, 0, 20, 0));
      infoBox.setAlignment(Pos.CENTER);
      if (!hasCaughtShrimp) {
        infoBox.getChildren().addAll(roundInfo, playerStats, catchShrimpBtn);
      }
      else {
        infoBox.getChildren().addAll(roundInfo, playerStats);
      }


      GridPane grid = new GridPane();
      grid.setVgap(10);
      grid.setHgap(10);

      Label player1Lbl = new Label(player.getName());
      player1Lbl.getStyleClass().add("name-label");
      player1Lbl.setPadding(new Insets(0, 10, 0, 10));
      Image player1Image = new Image(
          shrimpGameApp.getClass().getResourceAsStream("/images/player1.png"));
      VBox player1Box = new VBox();
      player1Box.setPrefWidth(200);
      player1Box.setPrefHeight(200);
      player1Box.setSpacing(10);
      player1Box.setAlignment(Pos.CENTER);
      player1Box.getChildren().addAll(player1Lbl, new ImageView(player1Image));

      Label player2Lbl = new Label(iterator.next().getName());
      player2Lbl.getStyleClass().add("name-label");
      player2Lbl.setPadding(new Insets(0, 10, 0, 10));
      Image player2Image = new Image(
          shrimpGameApp.getClass().getResourceAsStream("/images/player2.png"));
      VBox player2Box = new VBox();
      player2Box.setPrefWidth(200);
      player2Box.setPrefHeight(200);
      player2Box.setSpacing(10);
      player2Box.setAlignment(Pos.CENTER);
      player2Box.getChildren().addAll(player2Lbl, new ImageView(player2Image));

      Label player3Lbl = new Label(iterator.next().getName());
      player3Lbl.getStyleClass().add("name-label");
      player3Lbl.setPadding(new Insets(0, 10, 0, 10));
      Image player3Image = new Image(
          shrimpGameApp.getClass().getResourceAsStream("/images/player3.png"));
      VBox player3Box = new VBox();
      player3Box.setPrefWidth(200);
      player3Box.setPrefHeight(200);
      player3Box.setSpacing(10);
      player3Box.setAlignment(Pos.CENTER);
      player3Box.getChildren().addAll(player3Lbl, new ImageView(player3Image));

      grid.add(player1Box, 0, 0);
      grid.add(player2Box, 1, 0);
      grid.add(player3Box, 2, 0);
      grid.setAlignment(Pos.CENTER);

      Label roundsLeftLbl = new Label(
          "Number of rounds left: " + (shrimpGameApp.getGame().getSettings().getNumberOfRounds()
                                       - shrimpGameApp.getGame().getCurrentRoundNum()));
      roundsLeftLbl.getStyleClass().add("rounds-left-label");
      roundsLeftLbl.setPadding(new Insets(20));

      content.getChildren().addAll(roundLbl, grid, infoBox, roundStatusLbl, roundsLeftLbl);
      Image backgroundImage = new Image(
          shrimpGameApp.getClass().getResource("/images/overview.jpg").toExternalForm());
      BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
      BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                       BackgroundRepeat.NO_REPEAT,
                                                       BackgroundPosition.CENTER, backgroundSize);
      content.setBackground(new Background(background));

    }
    else if (option.equals("Scoreboard")) {
      Label titleLbl = new Label("Scoreboard");
      titleLbl.setPadding(new Insets(0, 0, 20, 0));
      titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
      titleLbl.getStyleClass().add("title-label");

      TableView<Round> scoreboardTableview = shrimpGameApp.getScoreboardTableview();
      if (!shrimpGameApp.isScoreboardTableViewInitialized()) {
        shrimpGameApp.setScoreboardTableView(scoreboardTableview);
        shrimpGameApp.setScoreboardTableViewInitialized(true);
      }
      content.getChildren().addAll(titleLbl, scoreboardTableview);
      Image backgroundImage = new Image(
          shrimpGameApp.getClass().getResource("/images/create_game.jpg").toExternalForm());
      BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
      BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                       BackgroundRepeat.NO_REPEAT,
                                                       BackgroundPosition.CENTER, backgroundSize);
      content.setBackground(new Background(background));

    }
    else if (option.equals("Rules")) {
      Label titleLbl = new Label("COMING SOON");
      titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
      titleLbl.getStyleClass().add("title-label");

      content.getChildren().add(titleLbl);
      Image backgroundImage = new Image(
          shrimpGameApp.getClass().getResource("/images/rules.jpg").toExternalForm());
      BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
      BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                       BackgroundRepeat.NO_REPEAT,
                                                       BackgroundPosition.CENTER, backgroundSize);
      content.setBackground(new Background(background));
    }
    else if (option.equals("Chat")) {
      Label titleLbl = new Label("CHAT");
      titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
      titleLbl.getStyleClass().add("title-label");

      String[] communicationRounds =
          shrimpGameApp.getGame().getSettings().getCommunicationRounds().split(",");
      List<Integer> commRoundNums = new ArrayList<Integer>();
      for (String communicationRound : communicationRounds) {
        commRoundNums.add(Integer.parseInt(communicationRound));
      }
      if (commRoundNums.contains(shrimpGameApp.getGame().getCurrentRoundNum())) {
        VBox chatBox = new VBox();
        chatBox.setPadding(new Insets(30));

        TextArea messageArea = new TextArea();
        messageArea.setWrapText(true);
        messageArea.setPromptText("Type a message");
        messageArea.setPrefHeight(150);
        ScrollPane messageScrollPane = new ScrollPane(messageArea);
        messageScrollPane.setFitToWidth(true);

        Label errorLbl = new Label();
        errorLbl.getStyleClass().add("error-label");
        errorLbl.setTextFill(Color.RED);
        errorLbl.setVisible(false);

        Button sendBtn = new Button("SEND");
        sendBtn.getStyleClass().add("button3");
        sendBtn.setPrefWidth(150);
        sendBtn.setPrefHeight(50);
        sendBtn.setOnAction(event -> shrimpGameApp.getChatScreenController()
                                                  .handleSendButton(messageArea, errorLbl));

        HBox inputBox = new HBox(messageScrollPane, sendBtn);
        inputBox.setSpacing(10);
        inputBox.setPadding(new Insets(20, 0, 0, 0));
        inputBox.setAlignment(Pos.CENTER);

        GridPane chatGrid = shrimpGameApp.getChatMessageGrid();
        chatGrid.setPadding(new Insets(20));
        chatGrid.setHgap(10);
        chatGrid.setVgap(10);


        ScrollPane chatScrollPane = new ScrollPane(chatGrid);
        chatScrollPane.setPrefHeight(200);

        List<String> messages = shrimpGameApp.getGame().getMessages();
        int row = 0;
        for (String message : messages) {
          String[] messageParts = message.split("\\.");
          String usernamePart = messageParts[0];
          String messagePart = messageParts[1];
          Label usernameLbl = new Label(usernamePart);
          usernameLbl.getStyleClass().add("username-label");

          TextArea messageTextArea = new TextArea(messagePart);
          messageTextArea.getStyleClass().add("message-textarea");
          messageTextArea.setEditable(false);
          messageTextArea.setWrapText(true);

          chatGrid.add(usernameLbl, 0, row);
          chatGrid.add(messageTextArea, 1, row);
          row++;
        }

        chatBox.getChildren().addAll(chatScrollPane, inputBox);

        content.getChildren().addAll(titleLbl, chatBox, errorLbl);
      }
      else {
        // Create a GridPane with 2 columns
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));

        // Add the mayor image to the left of the text
        Image mayorImage = new Image(
            shrimpGameApp.getClass().getResourceAsStream("/images/mayor.png"));
        ImageView mayorImageView = new ImageView(mayorImage);
        mayorImageView.setFitHeight(250);
        mayorImageView.setPreserveRatio(true);
        mayorImageView.setSmooth(true);

        String info =
            "Communication is not allowed in round " + shrimpGameApp.getGame().getCurrentRoundNum()
            + ".\n\nCommunication is only allowed in rounds: " + shrimpGameApp.getGame()
                                                                              .getSettings()
                                                                              .getCommunicationRounds();
        Label infoLbl = new Label(info);
        infoLbl.setWrapText(true);
        infoLbl.setFont(Font.font("Helvetica", 20));
        infoLbl.setPadding(new Insets(10));
        infoLbl.getStyleClass().add("text");

        // Create the scroll pane and add the label to it
        ScrollPane infoLblScrollPane = new ScrollPane();
        infoLblScrollPane.setContent(infoLbl);
        infoLblScrollPane.setFitToWidth(true);
        infoLblScrollPane.getStyleClass().add("scroll-pane");

        // Add the man image and the scroll pane to the GridPane
        grid.add(mayorImageView, 0, 0);
        grid.add(infoLblScrollPane, 1, 0);

        content.getChildren().addAll(titleLbl, grid);
      }


      Image backgroundImage = new Image(
          shrimpGameApp.getClass().getResource("/images/chat.jpg").toExternalForm());
      BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
      BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                       BackgroundRepeat.NO_REPEAT,
                                                       BackgroundPosition.CENTER, backgroundSize);
      content.setBackground(new Background(background));
    }

    return content;
  }


}
