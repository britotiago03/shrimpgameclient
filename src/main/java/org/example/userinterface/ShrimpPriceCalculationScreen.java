package org.example.userinterface;

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

/**
 * This class represents the shrimp price calculation screen of the Shrimp Game application.
 */
public abstract class ShrimpPriceCalculationScreen {
  private static int currentStep = 0;
  private static boolean allLabelsVisible = false;

  /**
   * Sets the visibility state of all the labels.
   * 
   * @param allLabelsVisible a {@code boolean} value describing the visibility of all the labels.
   */
  public static void setAllLabelsVisible(boolean allLabelsVisible)
  {
    ShrimpPriceCalculationScreen.allLabelsVisible = allLabelsVisible;
  }

  /**
   * Returns a {@link javafx.scene.Scene Scene} object representing the shrimp price calculation 
   * screen of the Shrimp Game application.
   *
   * @param shrimpGameApp the {@link ShrimpGameApp} object used to get the game information.
   * @return a {@code Scene} object representing the shrimp price calculation screen.
   */
  public static Scene getShrimpPriceCalculationScreen(ShrimpGameApp shrimpGameApp) {
    ShrimpPriceCalculationScreen.currentStep = 0;
    VBox root = new VBox();
    root.setSpacing(20);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(
        shrimpGameApp.getClass().getResource("/css/shrimpPriceCalculation.css").toExternalForm());

    int totalAmountOfShrimp = shrimpGameApp.getGame().getRounds().get(
        shrimpGameApp.getGame().getCurrentRoundNum() - 1).getTotalAmountOfShrimp();
    // Add title
    Label titleLbl = new Label("Shrimp Price Calculation");
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
    Label[] stepLabels = new Label[8];
    stepLabels[0] = new Label(
        "To calculate the price of shrimp this round, we follow these steps:");
    stepLabels[0].setVisible(true);
    stepLabels[1] = new Label("Step 1: We start with a base price of $45.");
    stepLabels[2] = new Label(
        "Step 2: We calculate the total amount of shrimp caught by all players this round.");
    stepLabels[3] = new Label(
        "The total amount of shrimp caught by all players this round is " + totalAmountOfShrimp
        + "kg.");
    stepLabels[4] = new Label(
        "Step 3: We multiply " + totalAmountOfShrimp + " by 0.2 to get " + (int) (0.2
                                                                                  * totalAmountOfShrimp)
        + ".");
    stepLabels[5] = new Label(
        "Step 4: We subtract " + (int) (0.2 * totalAmountOfShrimp) + " from $45 to get $" + (45 - (
            0.2 * totalAmountOfShrimp)) + ".");
    stepLabels[6] = new Label("Step 5: We round down $" + (45 - (0.2 * totalAmountOfShrimp))
                              + " to the nearest dollar to ensure a whole number price for"
                              + " the shrimp.");
    stepLabels[7] = new Label(
        "The price of shrimp this round based on the total shrimp caught by all players is $" + (45
                                                                                                 - (int) (
            0.2 * totalAmountOfShrimp)) + ".");
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

    // Add the man image and the scroll pane to the GridPane
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
                              if (ShrimpPriceCalculationScreen.allLabelsVisible) {
                                ShrimpPriceCalculationScreen.setAllLabelsVisible(false);
                                shrimpGameApp.setScene(
                                    shrimpGameApp.getRoundProfitMoneyCalculationScreen());
                              }
                              else {
                                currentStep++;
                                if (currentStep > stepLabels.length) {
                                  shrimpGameApp.setScene(
                                      shrimpGameApp.getRoundProfitMoneyCalculationScreen());
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
                          if (ShrimpPriceCalculationScreen.allLabelsVisible) {
                            ShrimpPriceCalculationScreen.setAllLabelsVisible(false);
                            shrimpGameApp.setScene(
                                shrimpGameApp.getRoundProfitMoneyCalculationScreen());
                          }
                          else {
                            for (Label stepLabel : stepLabels) {
                              stepLabel.setVisible(true);
                            }
                             ShrimpPriceCalculationScreen.setAllLabelsVisible(true);
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
