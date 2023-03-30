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
     * Sets the primary stage of the JavaFX application.
     *
     * @param primaryStage a Stage object
     */
    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }
}