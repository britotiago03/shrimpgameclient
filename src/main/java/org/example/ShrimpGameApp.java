package org.example;

import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.controllers.CatchShrimpScreenController;
import org.example.controllers.CreateGameScreenController;
import org.example.controllers.JoinGameScreenController;
import org.example.controllers.MainMenuScreenController;
import org.example.logic.Game;
import org.example.logic.Lobby;
import org.example.network.ServerConnection;
import org.example.network.ServerUpdateListener;
import org.example.userinterface.CatchShrimpScreen;
import org.example.userinterface.CreateGameScreen;
import org.example.userinterface.GameScreen;
import org.example.userinterface.GameStartedScreen;
import org.example.userinterface.GameTutorialScreen;
import org.example.userinterface.JoinGameScreen;
import org.example.userinterface.JoinedGameScreen;
import org.example.userinterface.MainAdminScreen;
import org.example.userinterface.MainScreen;
import org.example.userinterface.ShrimpCaughtSummaryScreen;

/**
 * The {@code ShrimpGameApp} class is the main class for the Shrimp Game application. It extends
 * the JavaFX {@code Application} class and provides the main method for launching the
 * application. The class also manages the different scenes and controllers used in the
 * application, as well as the server connection and executor service.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */

public class ShrimpGameApp extends Application {
  private Stage primaryStage;
  private Scene mainScreen;
  private Scene mainAdminScreen;
  private Scene createGameScreen;
  private Scene joinGameScreen;
  private Scene joinedGameScreen;
  private Scene gameTutorialScreen;
  private Scene gameStartedScreen;
  private Scene gameScreen;
  private Scene gameCaughtShrimpScreen;
  private Scene catchShrimpScreen;
  private Scene summaryScreen;
  private User user;
  private ServerConnection serverConnection;
  private MainMenuScreenController mainMenuScreenController;
  private CreateGameScreenController createGameScreenController;
  private JoinGameScreenController joinGameScreenController;
  private CatchShrimpScreenController catchShrimpScreenController;
  private TableView<Lobby> joinGameLobbyTableView;
  private TableView<Lobby> joinedGameLobbyTableView;
  private Game game;
  private List<Lobby> lobbies;
  private boolean gameStarted;
  public static final String VERSION = "1.6.0";

  /**
   * The {@code start} method is called when the application is launched. It initializes the main
   * scenes, controllers, and server connection, and sets the initial scene.
   *
   * @param stage the primary stage for the application
   */
  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    this.joinGameLobbyTableView = new TableView<Lobby>();
    this.joinedGameLobbyTableView = new TableView<Lobby>();
    this.createUser();
    this.gameStarted = false;
    this.mainMenuScreenController = new MainMenuScreenController(this);
    this.joinGameScreenController = new JoinGameScreenController(this);
    this.createGameScreenController = new CreateGameScreenController(this);
    this.catchShrimpScreenController = new CatchShrimpScreenController(this);
    this.mainScreen = MainScreen.getMainScreen(this);
    this.mainAdminScreen = MainAdminScreen.getMainAdminScreen(this);
    this.createGameScreen = CreateGameScreen.getCreateGameScreen(this);
    this.joinGameScreen = JoinGameScreen.getJoinGameScreen(this);
    this.joinedGameScreen = JoinedGameScreen.getJoinedGameScreen(this);
    this.gameTutorialScreen = GameTutorialScreen.getGameTutorialScreen(this);
    this.summaryScreen = ShrimpCaughtSummaryScreen.getShrimpCaughtSummaryScreen(this);
    this.setScene(this.getSummaryScreen());
  }

  /**
   * The primary method that launches the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Adds the Shrimp Game icon to the given dialog.
   *
   * @param dialog the dialog to add the icon to
   */
  public void addIconToDialog(Dialog dialog) {
    Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
    dialogStage.getIcons().add(
        new Image(this.getClass().getResource("/images/shrimp_logo.png").toExternalForm()));
  }

  /**
   * Returns the ServerConnection object that is used to communicate with the server.
   *
   * @return the ServerConnection object
   */
  public ServerConnection getServerConnection() {
    return serverConnection;
  }

  /**
   * Returns the TableView of the JoinGameScene that displays the lobbies available to join.
   *
   * @return the TableView
   */
  public TableView<Lobby> getJoinGameLobbyTableView() {
    return this.joinGameLobbyTableView;
  }

  /**
   * Returns the TableView of the JoinedGameScene that displays the lobbies available to join.
   *
   * @return the TableView
   */
  public TableView<Lobby> getJoinedGameLobbyTableView() {
    return this.joinedGameLobbyTableView;
  }

  /**
   * Returns the MainMenuScreenController object that manages the main menu screen.
   *
   * @return the MainMenuScreenController object
   */
  public MainMenuScreenController getMainMenuScreenController() {
    return this.mainMenuScreenController;
  }

  /**
   * Returns the CreateGameScreenController object that manages the create game screen.
   *
   * @return the CreateGameScreenController object
   */
  public CreateGameScreenController getCreateGameScreenController() {
    return this.createGameScreenController;
  }

  /**
   * Returns the JoinGameScreenController object that manages the join game screen.
   *
   * @return the JoinGameScreenController object
   */
  public JoinGameScreenController getJoinGameScreenController() {
    return this.joinGameScreenController;
  }

  public CatchShrimpScreenController getCatchShrimpScreenController() {
    return this.catchShrimpScreenController;
  }

  public boolean isGameStarted() {
    return this.gameStarted;
  }

  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  /**
   * Sets the primary stage to display the given scene.
   *
   * @param scene the scene to display
   */
  public void setScene(Scene scene) {
    Platform.runLater(() ->
                      {
                        this.primaryStage.hide();
                        Image icon = new Image(
                            this.getClass().getResource("/images/shrimp_logo.png")
                                .toExternalForm());
                        this.primaryStage.getIcons().add(icon);
                        this.primaryStage.setScene(scene);
                        this.primaryStage.setTitle("Shrimp Game");
                        this.primaryStage.setMinHeight(600);
                        this.primaryStage.setMinWidth(700);
                        this.primaryStage.show();
                      });

  }

  public void createUser() {
    try {
      String[] input = this.initServerConnection();
      this.user = new User(input[0], Boolean.parseBoolean(input[1]));
    }
    catch (RuntimeException exception) {
      this.user = new User("Player", false);
    }
  }

  public User getUser() {
    return this.user;
  }

  public Game getGame() {

    return this.game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public List<Lobby> getLobbies() {
    return this.lobbies;
  }

  public void setLobbies(List<Lobby> lobbies) {
    this.lobbies = lobbies;
  }

  /**
   * Returns the main scene, which is either the admin main scene or the regular main scene,
   * depending on whether the user is an admin or not.
   *
   * @return the main scene
   */
  public Scene getMainScreen() {
    Scene mainScene = null;
    if (this.user.isAdmin()) {
      mainScene = this.mainAdminScreen;
    }
    else {
      mainScene = this.mainScreen;
    }
    return mainScene;
  }

  /**
   * Returns the join game scene.
   *
   * @return the join game scene
   */
  public Scene getJoinGameScreen() {
    return this.joinGameScreen;
  }

  /**
   * Returns the create game scene.
   *
   * @return the create game scene
   */
  public Scene getCreateGameScreen() {
    return this.createGameScreen;
  }

  /**
   * Returns the joined game scene.
   *
   * @return the joined game scene
   */
  public Scene getJoinedGameScreen() {
    return this.joinedGameScreen;
  }

  /**
   * Returns the game tutorial scene.
   *
   * @return the game tutorial scene
   */
  public Scene getGameTutorialScreen() {
    return this.gameTutorialScreen;
  }

  /**
   * Returns the game started scene.
   *
   * @return the game tutorial scene.
   */
  public Scene getGameStartedScreen() {
    return this.gameStartedScreen;
  }

  public Scene getGameScreen() {
    return this.gameScreen;
  }

  public Scene getGameCaughtShrimpScreen() {
    return this.gameCaughtShrimpScreen;
  }

  public Scene getCatchShrimpScreen() {
    return this.catchShrimpScreen;
  }

  public Scene getSummaryScreen()
  {
    return this.summaryScreen;
  }


  /**
   * Initializes the server connection to the game server.
   * Returns an array containing the username and whether the user is an admin or not.
   * If the initialization fails, a runtime exception is thrown.
   *
   * @return an array containing the username and whether the user is an admin or not
   * @throws RuntimeException if the server connection fails to initialize
   */
  private String[] initServerConnection() {
    String[] input;
    this.serverConnection = new ServerConnection("16.170.166.39", 8080);

    try {
      this.serverConnection.connect();
      // Create Update Listener
      Thread updateListener = new Thread(new ServerUpdateListener(this));
      updateListener.start();
      input = this.serverConnection.sendUsernameRequest();
    }
    catch (RuntimeException exception) {
      throw new RuntimeException("Failed to initialize the server connection.");
    }
    return input;
  }

  /**
   * Sets the table view for the lobby. The table view contains two columns: one for the lobby name,
   * and one for the number of players currently in the lobby.
   *
   * @param lobbyTableView the table view for the lobby
   */
  public void setLobbyTableView(TableView<Lobby> lobbyTableView) {
    TableColumn<Lobby, String> lobbyNameCol = new TableColumn<>("Lobby");
    lobbyTableView.setPlaceholder(new Label("There are no created lobbies yet"));
    lobbyNameCol.setCellValueFactory(new PropertyValueFactory<>("lobbyName"));
    lobbyNameCol.setResizable(false);
    lobbyNameCol.setReorderable(false);
    lobbyNameCol.prefWidthProperty().bind(lobbyTableView.widthProperty().multiply(0.5));

    TableColumn<Lobby, Integer> playerCountCol = new TableColumn<>("Players");
    playerCountCol.setCellValueFactory(new PropertyValueFactory<>("numPlayers"));
    playerCountCol.setResizable(false);
    playerCountCol.setReorderable(false);
    playerCountCol.prefWidthProperty().bind(lobbyTableView.widthProperty().multiply(0.5));

    lobbyTableView.getColumns().addAll(lobbyNameCol, playerCountCol);
    lobbyNameCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item);
        setStyle("-fx-alignment: CENTER;");
      }
    });

    playerCountCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("");
        }
        else {
          Lobby lobby = getTableView().getItems().get(getIndex());
          int maxPlayers = lobby.getMaxPlayers();
          String text = String.format("%d/%d", item, maxPlayers);
          setText(text);
        }
      }
    });

    List<Lobby> lobbies = this.getServerConnection().getExistingLobbies();
    ObservableList<Lobby> observableLobbies = FXCollections.observableArrayList(lobbies);
    lobbyTableView.setItems(observableLobbies);

  }

  public void updateLobbyTable(List<Lobby> lobbies) {
    ObservableList<Lobby> observableLobbies = FXCollections.observableArrayList(lobbies);
    this.joinGameLobbyTableView.setItems(observableLobbies);
    this.joinedGameLobbyTableView.setItems(observableLobbies);
  }

  public void initGameScreens() {
    this.gameStartedScreen = GameStartedScreen.getGameStartedScene(this);
    this.gameScreen = GameScreen.getMainScene(this, false);
    this.gameCaughtShrimpScreen = GameScreen.getMainScene(this, true);
    this.catchShrimpScreen = CatchShrimpScreen.getCatchShrimpScene(this);
  }


}
