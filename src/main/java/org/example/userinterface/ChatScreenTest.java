package org.example.userinterface;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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
import javafx.stage.Screen;
import org.example.ShrimpGameApp;

public abstract class ChatScreenTest {
  public static Scene getChatScreenTest(ShrimpGameApp shrimpGameApp) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(50));
    Scene chatScene = new Scene(root, 800, 600);
    chatScene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/game.css").toExternalForm());
    Label titleLbl = new Label("CHAT");
    titleLbl.setAlignment(Pos.CENTER);
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 24));
    titleLbl.getStyleClass().add("title-label");

    String[] communicationRounds = "1,2,3".split(",");
    List<Integer> commRoundNums = new ArrayList<Integer>();
    for (String communicationRound : communicationRounds) {
      commRoundNums.add(Integer.parseInt(communicationRound));
    }
    if (commRoundNums.contains(1)) {
      VBox chatBox = new VBox();
      TextArea messageArea = new TextArea();
      messageArea.setWrapText(true);
      messageArea.setPromptText("Type a message");
      messageArea.setPrefHeight(300);
      ScrollPane messageScrollPane = new ScrollPane(messageArea);
      messageScrollPane.setFitToWidth(true);

      Label errorLbl = new Label();
      errorLbl.getStyleClass().add("error-label");
      errorLbl.setTextFill(Color.RED);
      errorLbl.setVisible(false);

      Button sendBtn = new Button("SEND");
      sendBtn.getStyleClass().add("button3");
      sendBtn.setPrefWidth(100);
      sendBtn.setPrefHeight(100);
      sendBtn.setOnAction(
          event -> shrimpGameApp.getChatScreenController().handleSendButton(messageArea, errorLbl));

      HBox inputBox = new HBox(messageScrollPane, sendBtn);
      inputBox.setSpacing(20);
      inputBox.setPadding(new Insets(20, 0, 0, 0));
      inputBox.setAlignment(Pos.CENTER);

      GridPane chatGrid = shrimpGameApp.getChatMessageGrid();
      chatGrid.setPadding(new Insets(20));
      chatGrid.setHgap(10);
      chatGrid.setVgap(10);


      ScrollPane chatScrollPane = new ScrollPane(chatGrid);
      chatScrollPane.setPrefHeight(600);

      List<String> messages = new ArrayList<>();
      messages.add("Shrimper.Hello guys");
      messages.add("Dragoon.Bye stupid");
      int row = messages.size() - 1;
      for (int i = row; i >= 0; i--) {
        String message = messages.get(i);
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
        row--;
      }

      chatBox.getChildren().addAll(chatScrollPane, inputBox);

      root.getChildren().addAll(titleLbl, chatBox, errorLbl);
      Image backgroundImage = new Image(
          shrimpGameApp.getClass().getResource("/images/chat.jpg").toExternalForm());
      BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
      BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                                       BackgroundRepeat.NO_REPEAT,
                                                       BackgroundPosition.CENTER, backgroundSize);
      root.setBackground(new Background(background));
    }
    return chatScene;
  }
}
