package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.model.MessageComparator;
import org.example.ui.controllers.CatchShrimpScreenController;
import org.example.ui.controllers.ChatScreenController;
import org.example.ui.controllers.CreateGameScreenController;
import org.example.ui.controllers.GameOverScreenController;
import org.example.ui.controllers.JoinGameScreenController;
import org.example.ui.controllers.MainMenuScreenController;
import org.example.model.Game;
import org.example.model.Lobby;
import org.example.model.Player;
import org.example.model.Round;
import org.example.model.User;
import org.example.network.ServerConnection;
import org.example.network.ServerUpdateListener;
import org.example.ui.view.CatchShrimpScreen;
import org.example.ui.view.CreateGameScreen;
import org.example.ui.view.GameOverViewChatScreen;
import org.example.ui.view.GameOverViewScoreboardScreen;
import org.example.ui.view.GameScreen;
import org.example.ui.view.GameStartedScreen;
import org.example.ui.view.GameTutorialScreen;
import org.example.ui.view.JoinGameScreen;
import org.example.ui.view.JoinedGameScreen;
import org.example.ui.view.MainAdminScreen;
import org.example.ui.view.MainScreen;
import org.example.ui.view.RoundProfitMoneyCalculationScreen;
import org.example.ui.view.ShrimpCaughtSummaryScreen;
import org.example.ui.view.ShrimpPriceCalculationScreen;

/**
 * The {@code ShrimpGameApp} class is the main class for the Shrimp Game application. It extends
 * the JavaFX {@link javafx.application.Application Application} class and provides the main method
 * for launching the application. The class also manages the different scenes and controllers
 * used in the
 * application, as well as the server connection and executor service.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public class ShrimpGameApp extends Application {
  private Stage primaryStage;
  public static final String VERSION = "1.7.2";
  private static final String HOSTNAME = "34.88.118.44";
  private static final int PORT = 8080;
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
  private Scene shrimpCaughtSummaryScreen;
  private Scene shrimpPriceCalculationScreen;
  private Scene roundProfitMoneyCalculationScreen;
  private Scene gameOverViewScoreboardScreen;
  private Scene gameOverViewChatScreen;
  private Lobby selectedLobby;
  private User user;
  private ServerConnection serverConnection;
  private MainMenuScreenController mainMenuScreenController;
  private CreateGameScreenController createGameScreenController;
  private JoinGameScreenController joinGameScreenController;
  private CatchShrimpScreenController catchShrimpScreenController;
  private GameOverScreenController gameOverScreenController;
  private ChatScreenController chatScreenController;
  private TableView<Lobby> joinGameLobbyTableView;
  private TableView<Lobby> joinedGameLobbyTableView;
  private TableView<Round> scoreboardTableview;
  private TableView<Round> gameOverScoreboardTableview;
  private GridPane chatMessageGrid;
  private boolean scoreboardTableViewInitialized;
  private boolean gameOverScoreboardTableviewInitialized;
  private Game game;
  private List<Lobby> lobbies;
  private boolean gameStarted;
  private boolean allPlayersCaughtShrimp;
  private List<Label> roundTimerLabels;
  private Thread serverUpdateListener;

  /**
   * The {@code start} method is called when the application is launched. It initializes the main
   * scenes, controllers, and server connection, and sets the initial scene.
   *
   * @param stage the primary stage for the application.
   */
  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    this.primaryStage.setOnCloseRequest(event ->
                                        {
                                          event.consume(); // prevent default close behavior
                                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                          this.addIconToDialog(alert);
                                          alert.setTitle("Confirm Exit");
                                          alert.setHeaderText("Are you sure you want to exit?");
                                          alert.setContentText("Any unsaved changes will be lost.");
                                          Optional<ButtonType> result = alert.showAndWait();
                                          if (result.get() == ButtonType.OK) {
                                            System.exit(0);
                                            Platform.exit();
                                          }
                                        });
    this.joinGameLobbyTableView = new TableView<Lobby>();
    this.joinedGameLobbyTableView = new TableView<Lobby>();
    this.scoreboardTableview = new TableView<Round>();
    this.gameOverScoreboardTableview = new TableView<Round>();
    this.scoreboardTableViewInitialized = false;
    this.gameOverScoreboardTableviewInitialized = false;
    this.chatMessageGrid = new GridPane();
    this.roundTimerLabels = new ArrayList<Label>();
    this.createUser();
    this.gameStarted = false;
    this.mainMenuScreenController = new MainMenuScreenController(this);
    this.joinGameScreenController = new JoinGameScreenController(this);
    this.createGameScreenController = new CreateGameScreenController(this);
    this.catchShrimpScreenController = new CatchShrimpScreenController(this);
    this.chatScreenController = new ChatScreenController(this);
    this.gameOverScreenController = new GameOverScreenController(this);
    this.mainScreen = MainScreen.getMainScreen(this);
    this.mainAdminScreen = MainAdminScreen.getMainAdminScreen(this);
    this.createGameScreen = CreateGameScreen.getCreateGameScreen(this);
    this.joinGameScreen = JoinGameScreen.getJoinGameScreen(this);
    this.joinedGameScreen = JoinedGameScreen.getJoinedGameScreen(this);
    this.gameTutorialScreen = GameTutorialScreen.getGameTutorialScreen(this);
    this.setScene(this.getGameTutorialScreen());
    GameScreen.initOverviewBackgroundImage();
  }

  /**
   * The primary method that launches the application.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Adds the Shrimp Game icon to the given dialog.
   *
   * @param dialog the dialog to add the icon to.
   */
  public void addIconToDialog(Dialog dialog) {
    Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
    dialogStage.getIcons().add(
        new Image(this.getClass().getResource("/images/shrimp_logo.png").toExternalForm()));
  }

  /**
   * Returns the ServerConnection object that is used to communicate with the server.
   *
   * @return the ServerConnection object.
   */
  public ServerConnection getServerConnection() {
    return this.serverConnection;
  }

  /**
   * Returns the TableView of the JoinGameScene that displays the lobbies available to join.
   *
   * @return the TableView that displays the lobbies available to join.
   */
  public TableView<Lobby> getJoinGameLobbyTableView() {
    return this.joinGameLobbyTableView;
  }

  /**
   * Returns the TableView of the JoinedGameScene that displays the lobbies available to join.
   *
   * @return the TableView that displays the lobbies available to join.
   */
  public TableView<Lobby> getJoinedGameLobbyTableView() {
    return this.joinedGameLobbyTableView;
  }

  /**
   * Returns the TableView of the scoreboard.
   *
   * @return the TableView of the scoreboard.
   */
  public TableView<Round> getScoreboardTableview() {
    return this.scoreboardTableview;
  }

  /**
   * Returns the TableView of the game over scoreboard.
   *
   * @return the TableView of the game over scoreboard.
   */
  public TableView<Round> getGameOverScoreboardTableview() {
    return this.gameOverScoreboardTableview;
  }

  /**
   * Returns the {@code MainMenuScreenController} object that manages the main menu screen.
   *
   * @return the {@code MainMenuScreenController} object.
   */
  public MainMenuScreenController getMainMenuScreenController() {
    return this.mainMenuScreenController;
  }

  /**
   * Returns the {@code CreateGameScreenController} object that manages the create game screen.
   *
   * @return the {@code CreateGameScreenController} object.
   */
  public CreateGameScreenController getCreateGameScreenController() {
    return this.createGameScreenController;
  }

  /**
   * Returns the {@code JoinGameScreenController} object that manages the join game screen.
   *
   * @return the {@code JoinGameScreenController} object.
   */
  public JoinGameScreenController getJoinGameScreenController() {
    return this.joinGameScreenController;
  }

  /**
   * Returns the {@code CatchShrimpScreenController} object that manages the catch shrimp screen.
   *
   * @return the {@code CatchShrimpScreenController} object.
   */
  public CatchShrimpScreenController getCatchShrimpScreenController() {
    return this.catchShrimpScreenController;
  }

  /**
   * Returns the {@code ChatScreenController} object that manages the chat screen.
   *
   * @return the {@code ChatScreenController} object.
   */
  public ChatScreenController getChatScreenController() {
    return this.chatScreenController;
  }

  /**
   * Returns the {@code GameOverScreenController} object that manages the game over screen.
   *
   * @return the {@code GameOverScreenController} object.
   */
  public GameOverScreenController getGameOverScreenController() {
    return this.gameOverScreenController;
  }

  /**
   * Returns a {@code boolean} value based on whether the game has started or not.
   *
   * @return {@code true} if the game has started, or {@code false} if it has not.
   */
  public boolean isGameStarted() {
    return this.gameStarted;
  }

  /**
   * Sets a {@code boolean} value based on whether the game has started or not.
   *
   * @param gameStarted a {@code boolean} value that is either {@code true} if the
   *                    game has started, or {@code false} if it has not.
   */
  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  /**
   * Returns a {@code boolean} value based on whether all players have caught shrimp or not.
   *
   * @return {@code true} if all players have caught shrimp, or {@code false} if they have not.
   */
  public boolean allPlayersCaughtShrimp() {
    return this.allPlayersCaughtShrimp;
  }

  /**
   * Sets a {@code boolean} value based on whether all players have caught shrimp or not.
   *
   * @param allPlayersCaughtShrimp a {@code boolean} value that is either {@code true} if all
   *                               players have caught shrimp, or {@code false} if they have not.
   */
  public void setAllPlayersCaughtShrimp(boolean allPlayersCaughtShrimp) {
    this.allPlayersCaughtShrimp = allPlayersCaughtShrimp;
  }

  /**
   * Returns a {@code boolean} value based on whether the scoreboard table view is initialized or
   * not.
   *
   * @return {@code true} if the scoreboard table view is initialized, or {@code false} if it is
   * not.
   */
  public boolean isScoreboardTableViewInitialized() {
    return this.scoreboardTableViewInitialized;
  }

  /**
   * Sets a {@code boolean} value based on whether the scoreboard table view is initialized or not.
   *
   * @param scoreboardTableViewInitialized a {@code boolean} value that is either {@code true} if
   *                                       the scoreboard table view is initialized, or {@code
   *                                       false} if it is not.
   */
  public void setScoreboardTableViewInitialized(boolean scoreboardTableViewInitialized) {
    this.scoreboardTableViewInitialized = scoreboardTableViewInitialized;
  }

  /**
   * Returns a {@code boolean} value based on whether the game over scoreboard table
   * view is initialized or not.
   *
   * @return {@code true} if the game over scoreboard table view is initialized,
   * or {@code false} if it is not.
   */
  public boolean isGameOverScoreboardTableviewInitialized() {
    return this.gameOverScoreboardTableviewInitialized;
  }

  /**
   * Sets a {@code boolean} value based on whether the game over scoreboard table
   * view is initialized or not.
   *
   * @param gameOverScoreboardTableviewInitialized a {@code boolean} value that is either
   *                                               {@code true} if the game over scoreboard table
   *                                               view is initialized, or {@code false} if it is
   *                                               not.
   */
  public void setGameOverScoreboardTableviewInitialized(
      boolean gameOverScoreboardTableviewInitialized) {
    this.gameOverScoreboardTableviewInitialized = gameOverScoreboardTableviewInitialized;
  }

  /**
   * Gets the chat message {@code GridPane}.
   *
   * @return the chat message {@code GridPane}.
   */
  public GridPane getChatMessageGrid() {
    return this.chatMessageGrid;
  }

  /**
   * Gets the labels that displays timer.
   *
   * @return a {@code List} of labels for displaying time.
   */
  public List<Label> getRoundTimerLabels() {
    return this.roundTimerLabels;
  }

  /**
   * Gets the selected {@code Lobby}.
   *
   * @return the selected {@code Lobby}.
   */
  public Lobby getSelectedLobby() {
    return this.selectedLobby;
  }

  /**
   * Sets the selected {@code Lobby}.
   *
   * @param selectedLobby the selected {@code Lobby}.
   */
  public void setSelectedLobby(Lobby selectedLobby) {
    this.selectedLobby = selectedLobby;
  }

  public Thread getServerUpdateListener() {
    return this.serverUpdateListener;
  }

  /**
   * Sets the primary stage to display a specified scene.
   *
   * @param scene the scene to display.
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

  /**
   * Creates a user.
   */
  public void createUser() {
    try {
      String[] input = this.initServerConnection();
      this.user = new User(input[0], Boolean.parseBoolean(input[1]));
    }
    catch (RuntimeException exception) {
      Alert noConnectionDialog = new Alert(Alert.AlertType.INFORMATION);
      noConnectionDialog.setTitle("Failed to connect to the server");
      noConnectionDialog.setHeaderText(null);
      noConnectionDialog.setContentText("You may not have internet connection or you are using an "
                                        + "outdated version of Shrimp Game.\nThe latest version "
                                        + "is v" + VERSION + "\nIt could " + "also be " + "that the"
                                        + " server is not running.");
      this.addIconToDialog(noConnectionDialog);
      noConnectionDialog.showAndWait();
      this.user = new User("Player", false);
    }
  }

  /**
   * Gets the user.
   *
   * @return the user.
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Gets the game.
   *
   * @return the game.
   */
  public Game getGame() {
    return this.game;
  }

  /**
   * Sets the game.
   *
   * @param game the game to set.
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * Gets the {@link List} of lobbies.
   *
   * @return a {@code List} of lobbies.
   */
  public List<Lobby> getLobbies() {
    return this.lobbies;
  }

  /**
   * Sets the {@link List} of lobbies.
   *
   * @param lobbies the {@code List} of lobbies to set.
   */
  public void setLobbies(List<Lobby> lobbies) {
    this.lobbies = lobbies;
  }

  /**
   * Returns the main scene, which is either the admin main scene or the regular main scene,
   * depending on whether the user is an admin or not.
   *
   * @return the main scene.
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
   * @return the join game scene.
   */
  public Scene getJoinGameScreen() {
    return this.joinGameScreen;
  }

  /**
   * Returns the create game scene.
   *
   * @return the create game scene.
   */
  public Scene getCreateGameScreen() {
    return this.createGameScreen;
  }

  /**
   * Returns the joined game scene.
   *
   * @return the joined game scene.
   */
  public Scene getJoinedGameScreen() {
    return this.joinedGameScreen;
  }

  /**
   * Returns the game tutorial scene.
   *
   * @return the game tutorial scene.
   */
  public Scene getGameTutorialScreen() {
    return this.gameTutorialScreen;
  }

  /**
   * Returns the game started scene.
   *
   * @return the game started scene.
   */
  public Scene getGameStartedScreen() {
    return this.gameStartedScreen;
  }

  /**
   * Returns the game scene.
   *
   * @return the game scene.
   */
  public Scene getGameScreen() {
    return this.gameScreen;
  }

  /**
   * Returns the game caught shrimp scene.
   *
   * @return the game caught shrimp scene.
   */
  public Scene getGameCaughtShrimpScreen() {
    return this.gameCaughtShrimpScreen;
  }

  /**
   * Returns the catch shrimp scene.
   *
   * @return the catch shrimp scene.
   */
  public Scene getCatchShrimpScreen() {
    return this.catchShrimpScreen;
  }

  /**
   * Returns the shrimp caught summary scene.
   *
   * @return the shrimp caught summary scene.
   */
  public Scene getShrimpCaughtSummaryScreen() {
    return this.shrimpCaughtSummaryScreen;
  }

  /**
   * Returns the shrimp price calculation scene.
   *
   * @return the shrimp price calculation scene.
   */
  public Scene getShrimpPriceCalculationScreen() {
    return this.shrimpPriceCalculationScreen;
  }

  /**
   * Returns the round profit money calculation scene.
   *
   * @return the round profit money calculation scene.
   */
  public Scene getRoundProfitMoneyCalculationScreen() {
    return this.roundProfitMoneyCalculationScreen;
  }

  /**
   * Returns the game over scene.
   *
   * @return the game over scene.
   */
  public Scene getGameOverViewScoreboardScreen() {
    return this.gameOverViewScoreboardScreen;
  }

  public Scene getGameOverViewChatScreen() {
    return this.gameOverViewChatScreen;
  }

  /**
   * Initializes the server connection to the game server.
   * Returns a primitive String array containing the username and whether the user is an admin or
   * not.
   * If the initialization fails, a {@link RuntimeException} is thrown.
   *
   * @return a primitive array containing the username and whether the user is an admin or not.
   * @throws RuntimeException if the server connection fails to initialize.
   */
  private String[] initServerConnection() {
    String[] input;
    this.serverConnection = new ServerConnection(HOSTNAME, PORT);

    try {
      this.serverConnection.connect();
      // Create Update Listener
      this.serverUpdateListener = new Thread(new ServerUpdateListener(this));
      this.serverUpdateListener.start();
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
   * @param lobbyTableView the table view for the lobby.
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

    List<Lobby> lobbies;
    lobbies = this.getServerConnection().getExistingLobbies();
    ObservableList<Lobby> observableLobbies = FXCollections.observableArrayList(lobbies);
    lobbyTableView.setItems(observableLobbies);
  }

  /**
   * Updates the lobby table.
   *
   * @param lobbies the lobbies to update the table with.
   */
  public void updateLobbyTable(List<Lobby> lobbies) {
    ObservableList<Lobby> observableLobbies = FXCollections.observableArrayList(lobbies);
    this.joinGameLobbyTableView.setItems(observableLobbies);
    this.joinedGameLobbyTableView.setItems(observableLobbies);
  }

  /**
   * Sets the scoreboard table view.
   *
   * @param scoreboardTableView the scoreboard to set to the table.
   */
  public void setScoreboardTableView(TableView<Round> scoreboardTableView) {
    TableColumn<Round, String> roundNumberCol = new TableColumn<>("Rounds");
    roundNumberCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    roundNumberCol.setResizable(false);
    roundNumberCol.setReorderable(false);
    roundNumberCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> player1ShrimpCol = new TableColumn<>(this.getUser().getName());
    player1ShrimpCol.setCellValueFactory(cellData ->
                                         {
                                           Map<Player, Integer> playerShrimpCaughtMap =
                                               cellData.getValue().getPlayerShrimpCaughtMap();
                                           Map<String, Player> players =
                                               cellData.getValue().getPlayers();
                                           Integer shrimpCaught = playerShrimpCaughtMap.get(
                                               players.get(this.getUser().getName()));
                                           return new SimpleIntegerProperty(
                                               shrimpCaught == null ? 0 : shrimpCaught).asObject();
                                         });
    player1ShrimpCol.setResizable(false);
    player1ShrimpCol.setReorderable(false);
    player1ShrimpCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    List<Player> otherPlayers = new ArrayList<>(this.getGame().getPlayers().values());
    otherPlayers.remove(this.getGame().getPlayers().get(this.getUser().getName()));

    TableColumn<Round, Integer> player2ShrimpCol = new TableColumn<>(otherPlayers.get(0).getName());
    player2ShrimpCol.setCellValueFactory(cellData ->
                                         {
                                           Map<Player, Integer> playerShrimpCaughtMap =
                                               cellData.getValue().getPlayerShrimpCaughtMap();
                                           Map<String, Player> players =
                                               cellData.getValue().getPlayers();
                                           Integer shrimpCaught = playerShrimpCaughtMap.get(
                                               players.get(otherPlayers.get(0).getName()));
                                           return new SimpleIntegerProperty(
                                               shrimpCaught == null ? 0 : shrimpCaught).asObject();
                                         });
    player2ShrimpCol.setResizable(false);
    player2ShrimpCol.setReorderable(false);
    player2ShrimpCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> player3ShrimpCol = new TableColumn<>(otherPlayers.get(1).getName());
    player3ShrimpCol.setCellValueFactory(cellData ->
                                         {
                                           Map<Player, Integer> playerShrimpCaughtMap =
                                               cellData.getValue().getPlayerShrimpCaughtMap();
                                           Map<String, Player> players =
                                               cellData.getValue().getPlayers();
                                           Integer shrimpCaught = playerShrimpCaughtMap.get(
                                               players.get(otherPlayers.get(1).getName()));
                                           return new SimpleIntegerProperty(
                                               shrimpCaught == null ? 0 : shrimpCaught).asObject();
                                         });
    player3ShrimpCol.setResizable(false);
    player3ShrimpCol.setReorderable(false);
    player3ShrimpCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> totalShrimpCol = new TableColumn<>("Total Shrimp");
    totalShrimpCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
        cellData.getValue().getTotalAmountOfShrimp()).asObject());
    totalShrimpCol.setResizable(false);
    totalShrimpCol.setReorderable(false);
    totalShrimpCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> shrimpPriceCol = new TableColumn<>("Shrimp Price");
    shrimpPriceCol.setCellValueFactory(new PropertyValueFactory<>("shrimpPrice"));
    shrimpPriceCol.setResizable(false);
    shrimpPriceCol.setReorderable(false);
    shrimpPriceCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> profitPerKgCol = new TableColumn<>("Profit / Shrimp kg");
    profitPerKgCol.setCellValueFactory(cellData ->
                                       {
                                         Integer shrimpPrice = cellData.getValue().getShrimpPrice();
                                         Integer profitPerKg =
                                             shrimpPrice - this.getGame().getPlayers().get(
                                                 this.getUser().getName()).getExpenses();
                                         return new SimpleIntegerProperty(
                                             profitPerKg == null ? 0 : profitPerKg).asObject();
                                       });
    profitPerKgCol.setResizable(false);
    profitPerKgCol.setReorderable(false);
    profitPerKgCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.2));

    TableColumn<Round, Integer> roundProfitCol = new TableColumn<>("Round Profit");
    roundProfitCol.setCellValueFactory(cellData ->
                                       {
                                         Map<String, Player> players =
                                             cellData.getValue().getPlayers();
                                         Integer roundProfit = players.get(this.getUser().getName())
                                                                      .getRoundProfit();
                                         return new SimpleIntegerProperty(
                                             roundProfit == null ? 0 : roundProfit).asObject();
                                       });
    roundProfitCol.setResizable(false);
    roundProfitCol.setReorderable(false);
    roundProfitCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));

    TableColumn<Round, Integer> totalMoneyCol = new TableColumn<>("Total Money");
    totalMoneyCol.setCellValueFactory(cellData ->
                                      {
                                        Map<String, Player> players =
                                            cellData.getValue().getPlayers();
                                        Integer totalMoney = players.get(this.getUser().getName())
                                                                    .getCurrentTotalMoney();
                                        return new SimpleIntegerProperty(
                                            totalMoney == null ? 0 : totalMoney).asObject();
                                      });
    totalMoneyCol.setResizable(false);
    totalMoneyCol.setReorderable(false);
    totalMoneyCol.prefWidthProperty().bind(scoreboardTableView.widthProperty().multiply(0.1));


    scoreboardTableView.getColumns().addAll(roundNumberCol, player1ShrimpCol, player2ShrimpCol,
                                            player3ShrimpCol, totalShrimpCol, shrimpPriceCol,
                                            profitPerKgCol, roundProfitCol, totalMoneyCol);
    scoreboardTableView.setPlaceholder(new Label("There are no round results yet"));

    roundNumberCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item);
        setStyle("-fx-alignment: CENTER;");
      }
    });

    player1ShrimpCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item + "kg");
        setStyle("-fx-alignment: CENTER;");
      }
    });

    player2ShrimpCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item + "kg");
        setStyle("-fx-alignment: CENTER;");
      }
    });

    player3ShrimpCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item + "kg");
        setStyle("-fx-alignment: CENTER;");
      }
    });

    totalShrimpCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : item + "kg");
        setStyle("-fx-alignment: CENTER;");
      }
    });

    shrimpPriceCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : "$" + item);
        setStyle("-fx-alignment: CENTER;");
      }
    });

    profitPerKgCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : "$" + item);
        setStyle("-fx-alignment: CENTER;");
      }
    });

    roundProfitCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : "$" + item);
        setStyle("-fx-alignment: CENTER;");
      }
    });

    totalMoneyCol.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? "" : "$" + item);
        setStyle("-fx-alignment: CENTER;");
      }
    });
  }

  /**
   * Updates the scoreboard table.
   *
   * @param rounds the rounds to update the table with.
   */
  public void updateScoreboardTable(List<Round> rounds) {
    ObservableList<Round> observableRounds = FXCollections.observableArrayList(rounds);
    this.scoreboardTableview.setItems(observableRounds);
    this.gameOverScoreboardTableview.setItems(observableRounds);
  }

  /**
   * Resets the scoreboard and game over scoreboard tables.
   */
  public void resetScoreboardTables() {
    this.scoreboardTableview = new TableView<Round>();
    this.gameOverScoreboardTableview = new TableView<Round>();
    this.setScoreboardTableViewInitialized(false);
    this.setGameOverScoreboardTableviewInitialized(false);
  }

  /**
   * Updates the chat message grid.
   *
   * @param messages a {@link List} of messages to update the chat message grid with.
   */
  public void updateChatMessageGrid(List<String> messages) {
    this.chatMessageGrid.getChildren().clear();
    Collections.sort(messages, new MessageComparator());
    int row = 0;
    for (String message : messages) {
      String[] messageParts = message.split("\\.");
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
      dateLbl.getStyleClass().add("date-label");

      this.chatMessageGrid.add(usernameLbl, 0, row);
      this.chatMessageGrid.add(messageTextArea, 1, row);
      this.chatMessageGrid.add(dateLbl, 2, row);
      row++;
    }
  }

  /**
   * Initializes the game screens.
   */
  public void initGameScreens() {
    this.gameStartedScreen = GameStartedScreen.getGameStartedScene(this);
    this.catchShrimpScreen = CatchShrimpScreen.getCatchShrimpScene(this);
    this.gameScreen = GameScreen.getMainScene(this, false);
    this.gameCaughtShrimpScreen = GameScreen.getMainScene(this, true);
    this.gameOverViewScoreboardScreen =
        GameOverViewScoreboardScreen.getGameOverViewScoreboardScreen(this);
    this.gameOverViewChatScreen = GameOverViewChatScreen.getGameOverViewChatScreen(this);
  }

  /**
   * Initializes round results screens.
   */
  public void initRoundResultsScreens() {
    this.shrimpCaughtSummaryScreen = ShrimpCaughtSummaryScreen.getShrimpCaughtSummaryScreen(this);
    this.shrimpPriceCalculationScreen =
        ShrimpPriceCalculationScreen.getShrimpPriceCalculationScreen(this);
    this.roundProfitMoneyCalculationScreen =
        RoundProfitMoneyCalculationScreen.getRoundProfitMoneyCalculationScreen(this);
  }
}
