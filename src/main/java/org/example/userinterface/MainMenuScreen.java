package org.example.userinterface;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.controllers.MainMenuScreenController;

/**
 * The MainMenuScreen class represents the main menu screen of the Shrimp Game application.
 * <p>
 * It contains fields and methods for displaying the main menu screen and handling user input.
 * <p>
 * The class uses a GameController object to manage the game logic and user interface and a
 * ServerConnection
 * <p>
 * object to send and receive messages to and from the server. It also contains a reference to
 * the primary
 * <p>
 * stage of the JavaFX application.
 */
public class MainMenuScreen
{
    private Stage primaryStage;
    private String username;

    /**
     * Initializes the MainMenuScreen and its components.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    public MainMenuScreen(Stage primaryStage, String username)
    {
        this.setPrimaryStage(primaryStage);
        this.username = username;
    }

    /**
     * Initializes the MainMenuScreen and its components.
     */
    public void init(MainMenuScreenController controller)
    {
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(150, 0, 0, 0));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:./src/main/resources/css/styles.css");
        Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

        Label welcomeLbl = new Label("Welcome to Shrimp Game " + this.username);
        welcomeLbl.getStyleClass().add("welcome-label");

        Button joinGameBtn = new Button("JOIN GAME");
        joinGameBtn.getStyleClass().add("button");
        joinGameBtn.setOnAction(event -> controller.handleJoinGameButton());
        joinGameBtn.setPrefWidth(320);
        joinGameBtn.setPrefHeight(80);

        Button becomeAdminBtn = new Button("BECOME ADMIN");
        becomeAdminBtn.getStyleClass().add("button");
        becomeAdminBtn.setOnAction(event -> controller.handleBecomeAdminButton());
        becomeAdminBtn.setPrefWidth(320);
        becomeAdminBtn.setPrefHeight(80);

        Button quitBtn = new Button("QUIT");
        quitBtn.getStyleClass().add("button");
        quitBtn.setOnAction(event -> controller.handleExitButton());
        quitBtn.setPrefWidth(320);
        quitBtn.setPrefHeight(80);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        VBox.setVgrow(spacer3, Priority.ALWAYS);


        root.getChildren().addAll(spacer1, welcomeLbl, joinGameBtn, becomeAdminBtn, quitBtn,
                                  spacer2);
        root.setAlignment(Pos.CENTER);

        String path = "file:./src/main/resources/images/test4.jpg";

        Image backgroundImage = new Image(path);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage,
                                                         BackgroundRepeat.NO_REPEAT,
                                                         BackgroundRepeat.NO_REPEAT,
                                                         BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(background));


        Image icon = new Image("file:./src/main/resources/Images/shrimp_logo.png");
        this.primaryStage.getIcons().add(icon);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Shrimp Game");
        this.primaryStage.setMinHeight(600);
        this.primaryStage.setMinWidth(700);
        this.primaryStage.show();
    }

    public void initAsAdmin(MainMenuScreenController controller)
    {
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(150, 0, 0, 0));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:./src/main/resources/css/styles.css");
        Font helvetica = Font.loadFont("file:/fonts/Helvetica.ttf", 24);

        Label welcomeLbl = new Label("What would you like to do " + this.username + "?");
        welcomeLbl.getStyleClass().add("welcome-label");

        Button createGameBtn = new Button("CREATE GAME");
        createGameBtn.getStyleClass().add("button");
        createGameBtn.setOnAction(event -> controller.handleCreateGameButton());
        createGameBtn.setPrefWidth(320);
        createGameBtn.setPrefHeight(80);

        Button joinGameBtn = new Button("JOIN GAME");
        joinGameBtn.getStyleClass().add("button");
        joinGameBtn.setOnAction(event -> controller.handleJoinGameButton());
        joinGameBtn.setPrefWidth(320);
        joinGameBtn.setPrefHeight(80);

        Button quitBtn = new Button("QUIT");
        quitBtn.getStyleClass().add("button");
        quitBtn.setOnAction(event -> controller.handleExitButton());
        quitBtn.setPrefWidth(320);
        quitBtn.setPrefHeight(80);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        VBox.setVgrow(spacer3, Priority.ALWAYS);


        root.getChildren().addAll(spacer1, welcomeLbl, createGameBtn, joinGameBtn, quitBtn,
                                  spacer2);
        root.setAlignment(Pos.CENTER);

        String path = "file:./src/main/resources/images/test4.jpg";

        Image backgroundImage = new Image(path);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage,
                                                         BackgroundRepeat.NO_REPEAT,
                                                         BackgroundRepeat.NO_REPEAT,
                                                         BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(background));


        Image icon = new Image("file:./src/main/resources/Images/shrimp_logo.png");
        this.primaryStage.getIcons().add(icon);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Shrimp Game");
        this.primaryStage.setMinHeight(600);
        this.primaryStage.setMinWidth(700);
        this.primaryStage.show();
    }

    /**
     * Displays the main menu screen.
     */
    public void display(MainMenuScreenController controller, boolean isAdmin)
    {
        if (isAdmin)
        {
            this.initAsAdmin(controller);
        }
        else
        {
            this.init(controller);
        }
    }

    /**
     * Sets the primary stage of the JavaFX application.
     *
     * @param primaryStage a Stage object
     */
    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }
}