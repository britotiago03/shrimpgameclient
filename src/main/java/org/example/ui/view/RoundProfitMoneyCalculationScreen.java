package org.example.ui.view;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.ShrimpGameApp;
import org.example.model.Player;

/**
 * This class represents the round profit money calculation screen of the Shrimp Game application.
 */
public abstract class RoundProfitMoneyCalculationScreen {
  private static int currentStep = 0;
  private static boolean allLabelsVisible = false;

  /**
   * Sets the visibility state of all the labels.
   * 
   * @param allLabelsVisible a {@code boolean} value describing the visibility of all the labels.
   */
  public static void setAllLabelsVisible(boolean allLabelsVisible)
  {
    RoundProfitMoneyCalculationScreen.allLabelsVisible = allLabelsVisible;
  }

  /**
   * Returns a {@link javafx.scene.Scene Scene} object representing the round profit money calculation screen 
   * of the Shrimp Game application.
   *
   * @param shrimpGameApp the {@link ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the round profit money calculation screen.
   */
  public static Scene getRoundProfitMoneyCalculationScreen(ShrimpGameApp shrimpGameApp) {
    RoundProfitMoneyCalculationScreen.currentStep = 0;
    VBox root = new VBox();
    root.setSpacing(20);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/roundProfitMoneyCalculation.css")
                     .toExternalForm());

    int shrimpPricePerKg = shrimpGameApp.getGame().getRounds().get(
        shrimpGameApp.getGame().getCurrentRoundNum() - 1).getShrimpPrice();
    Player player = shrimpGameApp.getGame().getPlayers().get(shrimpGameApp.getUser().getName());
    int expensesPerKg = player.getExpenses();
    int shrimpCaught = player.getShrimpCaught();
    int previousTotalMoney = player.getPreviousTotalMoney();
    int currentTotalMoney = player.getCurrentTotalMoney();

    // Add title
    Label titleLbl = new Label("Round Profit and Total Profit Calculation");
    titleLbl.setFont(Font.loadFont("file:/fonts/Helvetica.ttf", 32));
    titleLbl.getStyleClass().add("title-label");
    titleLbl.setPadding(new Insets(20, 0, 0, 0));

    // Create a GridPane with 2 columns
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setPadding(new Insets(20));

    // Add the mayor image to the left of the text
    Image mayorImage = new Image(shrimpGameApp.getClass().getResourceAsStream("/images/mayor.png"));
    ImageView mayorImageView = new ImageView(mayorImage);
    mayorImageView.setFitHeight(250);
    mayorImageView.setPreserveRatio(true);
    mayorImageView.setSmooth(true);

    VBox stepsLblContainer = new VBox();
    // Create the step labels
    Label[] stepLabels = new Label[6];
    stepLabels[0] = new Label(
        "To calculate your round profit and money this round, we follow " + "these steps:");
    stepLabels[1] = new Label(
        "Step 1: We calculate your profit per kilogram by subtracting your expenses per "
        + "kilogram from the shrimp price per kilogram.");
    stepLabels[2] = new Label("The shrimp price per kilogram this round is $" + shrimpPricePerKg
                              + " and your expenses per kilogram is $" + expensesPerKg
                              + ". This makes your profit per kilogram $" + (shrimpPricePerKg
                                                                             - expensesPerKg)
                              + ".");
    stepLabels[3] = new Label(
        "Step 2: We calculate your profit by multiplying your profit per kilogram by the "
        + "amount of shrimp you caught this round.");
    stepLabels[4] = new Label(
        "Your profit per kilogram is $" + (shrimpPricePerKg - expensesPerKg) + " and the "
        + "amount of shrimp you caught this round is " + shrimpCaught + "kg. This makes your "
        + "profit $" + (shrimpPricePerKg - expensesPerKg) * shrimpCaught + ".");
    int playerProfit = (shrimpPricePerKg - expensesPerKg) * shrimpCaught;
    stepLabels[5] = new Label(
        "Your profit this round is $" + playerProfit + ". Your previous total money was $"
        + previousTotalMoney + ", and your current total money is now $" + currentTotalMoney + ".");
    for (Label stepLabel : stepLabels) {
      stepLabel.setFont(Font.font("Helvetica", 20));
      stepLabel.setPadding(new Insets(10));
      stepLabel.getStyleClass().add("text");
      stepLabel.setVisible(false);
      stepLabel.setWrapText(true);
      stepsLblContainer.getChildren().add(stepLabel);
    }

    // Create the scroll pane and add the label to it
    ScrollPane stepsLblScrollPane = new ScrollPane();
    stepsLblScrollPane.setContent(stepsLblContainer);
    stepsLblScrollPane.setFitToWidth(true);
    stepsLblScrollPane.getStyleClass().add("scroll-pane");

    // Add the shrimp image and the scroll pane to the GridPane
    grid.add(mayorImageView, 0, 0);
    grid.add(stepsLblScrollPane, 1, 0);

    // Create the continue button
    Button continueBtn = new Button("CONTINUE");
    continueBtn.setPrefWidth(250);
    continueBtn.setPrefHeight(80);
    // Create the back button
    Button backBtn = new Button("BACK");
    backBtn.setPrefWidth(250);
    backBtn.setPrefHeight(80);
    backBtn.setVisible(false);

    Button skipBtn = new Button("SKIP");
    skipBtn.setPrefWidth(250);
    skipBtn.setPrefHeight(80);

    // Set the event handlers for the buttons
    continueBtn.setOnAction(event ->
                            {
                              if (RoundProfitMoneyCalculationScreen.allLabelsVisible)
                              {
                                RoundProfitMoneyCalculationScreen.setAllLabelsVisible(false);
                                shrimpGameApp.setAllPlayersCaughtShrimp(false);
                                String[] communicationRounds =
                                    shrimpGameApp.getGame().getSettings().getCommunicationRounds().split(",");
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
                                else {
                                  shrimpGameApp.setScene(shrimpGameApp.getGameOverScreen());
                                }
                              }
                              else {
                                currentStep++;
                                if (currentStep > stepLabels.length) {
                                  shrimpGameApp.setAllPlayersCaughtShrimp(false);
                                  String[] communicationRounds =
                                      shrimpGameApp.getGame().getSettings().getCommunicationRounds().split(",");
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
                                  else {
                                    shrimpGameApp.setScene(shrimpGameApp.getGameOverScreen());
                                  }

                                }
                                else {
                                  stepLabels[currentStep - 1].setVisible(true);
                                  backBtn.setVisible(true);
                                }
                              }

                            });
    currentStep++;
    stepLabels[currentStep - 1].setVisible(true);
    backBtn.setVisible(true);

    backBtn.setOnAction(event ->
                        {
                          stepLabels[currentStep - 1].setVisible(false);
                          currentStep--;
                          if (currentStep == 0) {
                            backBtn.setVisible(false);
                          }
                        });

    skipBtn.setOnAction(event ->
                        {
                          if (RoundProfitMoneyCalculationScreen.allLabelsVisible)
                          {
                            RoundProfitMoneyCalculationScreen.setAllLabelsVisible(false);
                            shrimpGameApp.setAllPlayersCaughtShrimp(false);
                            String[] communicationRounds =
                                shrimpGameApp.getGame().getSettings().getCommunicationRounds().split(",");
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
                            else {
                              shrimpGameApp.setScene(shrimpGameApp.getGameOverScreen());
                            }
                          }
                          else {
                            for (Label stepLabel : stepLabels) {
                              stepLabel.setVisible(true);
                            }
                            RoundProfitMoneyCalculationScreen.setAllLabelsVisible(true);
                          }
                        });

    // Add the buttons to an HBox
    HBox buttonsBox = new HBox();
    buttonsBox.setAlignment(Pos.CENTER);
    buttonsBox.setSpacing(20);
    buttonsBox.setPadding(new Insets(0, 0, 20, 0));
    buttonsBox.getChildren().addAll(backBtn, continueBtn, skipBtn);

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

    return scene;
  }
}
