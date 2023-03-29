package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controllers.CreateGameScreenController;
import org.example.controllers.GameController;
import org.example.controllers.MainMenuScreenController;
import org.example.network.ServerConnection;
import org.example.userinterface.CreateGameScreen;
import org.example.userinterface.MainMenuScreen;

/**
 * The ShrimpGameApp class is the entry point of the application and is responsible for launching
 * the JavaFX application and
 * setting up the UI. It contains fields and methods for initializing all the screens and
 * components of the game UI, as well
 * as for accessing the primary stage, server connection, and game controller.
 */
public class ShrimpGameApp extends Application
{
    private Stage primaryStage;
    private ServerConnection serverConnection;
    private GameController gameController;
    private MainMenuScreenController mainMenuScreenController;
    private CreateGameScreenController createGameScreenController;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.primaryStage = stage;
        String username = this.initServerConnection();
        this.initMainMenu(username);
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    private Scene getMainMenuScene()
    {
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(150, 0, 0, 0));
        Scene mainMenu = new Scene(root, 800, 600);
        return mainMenu;
    }

    /**
     * Initializes the main menu screen and its components.
     */
    private void initMainMenu(String username)
    {
        MainMenuScreen mainMenuScreen = new MainMenuScreen(this.primaryStage, username);
        this.mainMenuScreenController = new MainMenuScreenController(this, mainMenuScreen,
                                                                     this.serverConnection);
        this.mainMenuScreenController.showMainMenuScreen();
    }

    public void initCreateGame()
    {
        CreateGameScreen createGameScreen = new CreateGameScreen(this.primaryStage);
        this.createGameScreenController = new CreateGameScreenController(this, createGameScreen,
                                                                         this.serverConnection,
                                                                         this.gameController);
        this.createGameScreenController.showCreateGameScreen();
    }

    /**
     * Initializes the server connection and establishes a socket connection with the server.
     */
    private String initServerConnection()
    {
        String username = "";
        this.serverConnection = new ServerConnection("spill.datakomm.work", 8080);
        try
        {
            this.serverConnection.connect();
            username = this.serverConnection.sendUsernameRequest();
        }
        catch (IOException exception)
        {
            System.err.println("Failed to connect to the server: " + exception);
            username = "Player";
        }
        catch (RuntimeException exception)
        {
            System.err.println(exception.getMessage());
            username = "Player";
        }
        return username;
    }

    /**
     * Initializes the game controller and sets up the protocol for sending and receiving
     * messages between the client and server.
     */
    private void initGameController()
    {
        gameController = new GameController(serverConnection);
    }

    /**
     * Returns the primary stage of the JavaFX application.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    /**
     * Returns the server connection object.
     *
     * @return the server connection object
     */
    public ServerConnection getServerConnection()
    {
        return serverConnection;
    }

    /**
     * Returns the game controller object.
     *
     * @return the game controller object
     */
    public GameController getGameController()
    {
        return gameController;
    }
}
