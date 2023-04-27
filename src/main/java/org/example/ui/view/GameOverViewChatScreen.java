package org.example.ui.view;

import java.util.Collections;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.model.MessageComparator;

/**
 * This class represents the game over screen that show the chat from the game.
 */
public abstract class GameOverViewChatScreen {
  /**
   * Returns a new {@link javafx.scene.Scene Scene} representing the game over screen showing chat.
   * 
   * @param shrimpGameApp the {@code ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the game over screen that shows the chat.
   */
  public static Scene getGameOverViewChatScreen(ShrimpGameApp shrimpGameApp) {
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

    VBox chatBox = new VBox();
    chatBox.setPadding(new Insets(30));
    chatBox.setAlignment(Pos.CENTER);

    GridPane chatGrid = shrimpGameApp.getChatMessageGrid();
    chatGrid.setPadding(new Insets(20));
    chatGrid.setHgap(10);
    chatGrid.setVgap(10);

    ScrollPane chatScrollPane = new ScrollPane(chatGrid);
    chatScrollPane.setPrefHeight(450);

    List<String> messages = shrimpGameApp.getGame().getMessages();
    Collections.sort(messages, new MessageComparator());
    int row = 0;
    for (String message : messages) {
      String[] messageParts = message.split("☐");
      String usernamePart = messageParts[0];
      String messagePart = messageParts[1];
      String datePart = messageParts[2];

      Label usernameLbl = new Label(usernamePart);
      usernameLbl.getStyleClass().add("username-label");

      TextArea messageTextArea = new TextArea(messagePart);
      messageTextArea.getStyleClass().add("message-textarea");
      messageTextArea.setEditable(false);
      messageTextArea.setWrapText(true);

      Label dateLbl = new Label(datePart);

      chatGrid.add(usernameLbl, 0, row);
      chatGrid.add(messageTextArea, 1, row);
      chatGrid.add(dateLbl, 2, row);
      row++;
    }

    chatBox.getChildren().addAll(chatScrollPane);

    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(20);

    Button viewChatBtn = new Button("VIEW SCORE");
    viewChatBtn.setPrefWidth(320);
    viewChatBtn.setPrefHeight(80);
    viewChatBtn.setOnAction(event ->
                            {
                              shrimpGameApp.getGameOverScreenController().handleViewScoreboardButton();
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

    root.getChildren().addAll(spacer1, headingLbl, chatBox, buttonBox, spacer2);
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
