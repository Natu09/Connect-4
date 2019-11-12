package connect4gui;

import connect4.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.animation.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

/**
 * GUIGame is a graphical user interface that builds upon GameConfig and ComputerPlayer from our Connect4 text version.
 *
 * <p>GUIGame presents the user with a stylized GUI version of our Connect4 game. The user has the choice of playing against another user,
 * or against the computer. There are many features of the user interface that guide the user to playing the game (ie. large buttons and an
 * about menu that has details on how to play).
 *
 * @author T02-1 - Matthew Cox
 * @version 1.1
 */
public class GUIGame extends Application {

  /**
   * config stores an instance of GameConfig to manage the ongoing Connect4 game.
   */
  private GameConfig config = new GameConfig();

  /**
   * ai is an instance of ComputerPlayer to play against the user, a maxDepth is set later by the user.
   */
  private ComputerPlayer ai = new ComputerPlayer();

  /**
   * player stores the current player while the game is ongoing.
   */
  private int player = 1;

  /**
   * columnWidth is a variable to set up the board and token shapes.
   */
  private final int columnWidth = 80;

  /**
   * tokenRoot is a pane that the tokens are displayed in that exists behind the board screen.
   */
  private Pane tokenRoot = new Pane();

  /**
   * p1MoveCount is used to track how many moves have been made by the first player.
   */
  private IntegerProperty p1MoveCount = new SimpleIntegerProperty(0);

  /**
   * p2MoveCount is used to track how many moves have been made by the second player or the computer.
   */
  private IntegerProperty p2MoveCount = new SimpleIntegerProperty(0);

  /**
   * gameOver stores whether or not the game is ongoing or over.
   */
  private boolean gameOver = false;

  /**
   * aiOn lets methods know whether or not the user is playing against the computer.
   */
  private boolean aiOn;

  /**
   * initialized version of primaryStage. Helps transition between the start menu and the game board scenes.
   */
  private Stage primaryStage;

  /**
   * initialized version of player1N. An empty string that will later store the first player's name.
   */
  private String player1N;

  /**
   * initialized version of player2N. An empty string that will later store the second player's name.
   */
  private String player2N;

  /**
   * firstTime is a BooleanProperty that helps refocus the attention off of the textfield for player name
   * entry. This exists purely for stylistic reasons.
   */
  final BooleanProperty firstTime = new SimpleBooleanProperty(true);

  /**
   * Main method calls launch method from Application class to setup JavaFX.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Overriding start method that was called from launch() in main method.
   *
   * @param primaryStage, the main window of the GUI.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Connect 4");
    this.primaryStage.setResizable(false);

		this.primaryStage.setScene(startScreen());
    this.primaryStage.show();
  }

  /**
   * startScreen creates the starting screen when the GUI is launched or when the user goes back to the main menu.
   *
   * <p>From the startScreen the user can choose a player v player game, player v computer, or to exit the application.
   * Additionally, the user can use the menuBar and all of its features from this scene.
   *
   * @return startScreen The startScreen scene that allows the user to make various choices in regards to playing the game.
   */
  private Scene startScreen() {

    BorderPane start = new BorderPane();
    start.setTop(menuBar());

    /* Sets up the background image for the startScreen. */
    Image startBackground = new Image("/gui_images/StartScreen.png");
    BackgroundSize bsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
    start.setBackground(new Background(new BackgroundImage(startBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bsize)));

    /* Adds PvP Button. */
    Button pvp = new Button();
    pvp.setPadding(Insets.EMPTY);
    Image pvpButton = new Image("/gui_images/PVPButton.png");
    Image pvpButtonHighlighted = new Image("/gui_images/PVPButtonHighlighted.png");
    pvp.setGraphic(new ImageView(pvpButton));
    pvp.setOnMouseEntered(e -> pvp.setGraphic(new ImageView(pvpButtonHighlighted)));
    pvp.setOnMouseExited(e -> pvp.setGraphic(new ImageView(pvpButton)));
    pvp.setOnAction(e -> {

      aiOn = false;
      this.primaryStage.setScene(nameInput(aiOn));

    });

    /* Adds PvPC Button. */
    Button pvpc = new Button();
    pvpc.setPadding(Insets.EMPTY);
    Image pvpcButton = new Image("/gui_images/PVPCButton.png");
    Image pvpcButtonHighlighted = new Image("/gui_images/PVPCButtonHighlighted.png");
    pvpc.setGraphic(new ImageView(pvpcButton));
    pvpc.setOnMouseEntered(e -> pvpc.setGraphic(new ImageView(pvpcButtonHighlighted)));
    pvpc.setOnMouseExited(e -> pvpc.setGraphic(new ImageView(pvpcButton)));
    pvpc.setOnAction(e -> {

      aiOn = true;
      this.primaryStage.setScene(nameInput(aiOn));

    });

    /* Adds quit Button. */
    Button quit = new Button();
    quit.setPadding(Insets.EMPTY);
    Image quitButton = new Image("/gui_images/QuitButton.png");
    Image quitButtonHighlighted = new Image("/gui_images/QuitButtonHighlighted.png");
    quit.setGraphic(new ImageView(quitButton));
    quit.setOnMouseEntered(e -> quit.setGraphic(new ImageView(quitButtonHighlighted)));
    quit.setOnMouseExited(e -> quit.setGraphic(new ImageView(quitButton)));
    quit.setOnAction(e -> Platform.exit());

    /* Groups all buttons into a vbox. */
    VBox vbox = new VBox(pvp, pvpc, quit);
    vbox.setPadding(new Insets(120, 0, 0, 0));
    vbox.setSpacing(40);
    vbox.setPrefWidth(630);
    vbox.setPrefHeight(598);
    vbox.setAlignment(Pos.CENTER);

    start.setCenter(vbox);
    Scene startScreen = new Scene(start);

    return startScreen;
  }

  /**
   * nameInput is a scene that prompts the users for their names that are then displayed in the statusBar.
   *
   * @param aiOnOrOff Whether or not the user is playing the computer.
   * @return nameInput The scene that gathers the user's nicknames.
   */
  private Scene nameInput(boolean aiOnOrOff) {

    BorderPane gameI = new BorderPane();
    gameI.setTop(menuBar());

    /* Perparing the background for the name input screen. */
    Image startBackground = new Image("/gui_images/nameInput.png");
    BackgroundSize bsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
    gameI.setBackground(new Background(new BackgroundImage(startBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bsize)));

    /* Creates label that will show the user what to do or if they make a mistake when entering names. */
    Label howToNameLabel = new Label();

    if (aiOnOrOff) {
      howToNameLabel.setText("Enter your name! 8 or fewer letters or digits allowed.");
    } else {
      howToNameLabel.setText("Enter your names! 8 or fewer letters or digits allowed.");
    }
    howToNameLabel.setFont(Font.font ("Lucida Sans Unicode", 16));
    howToNameLabel.setTextFill(Color.WHITE);
    howToNameLabel.setTextAlignment(TextAlignment.CENTER);


    /* Textfiels for player 1 and 2. */
    TextField textField1 = new TextField ();
    textField1.setText("Player 1");
    textField1.setMaxWidth(150);

    TextField textField2 = new TextField ();
    textField2.setText("Player 2");
    textField2.setMaxWidth(150);

    /* Creating a start button for the game. */
    Button startGame = new Button();
    startGame.setPadding(Insets.EMPTY);
    Image startButton = new Image("/gui_images/start.png");
    startGame.setGraphic(new ImageView(startButton));

    Image startButtonHighlighted = new Image("/gui_images/startButtonHighlighted.png");
    startGame.setGraphic(new ImageView(startButton));
    startGame.setOnMouseEntered(e -> startGame.setGraphic(new ImageView(startButtonHighlighted)));
    startGame.setOnMouseExited(e -> startGame.setGraphic(new ImageView(startButton)));

    gameI.getChildren().addAll(startGame);

    /* Name input validation, makes sure the users enter only letters, digits, or spaces using a regex. */
    startGame.setOnAction(e -> {

      if (!(textField1.getText().matches("^[\\p{L}\\p{N}\\s]+$")) || !(textField2.getText().matches("^[\\p{L}\\p{N}\\s]+$"))) {
        if (aiOnOrOff) {
          howToNameLabel.setText("That name was invalid! Letters or digits only please.");
        } else {
          howToNameLabel.setText("A name was invalid! Letters or digits only please.");
        }
        textField1.setText("Player 1");
        textField2.setText("Player 2");
      } else if ((textField1.getText().length() > 8) || (textField2.getText().length() > 8)) {
        if (aiOnOrOff) {
          howToNameLabel.setText("That name was too long! 8 or fewer letters or digits please.");
        } else {
          howToNameLabel.setText("A name was too long! 8 or fewer letters or digits please.");
        }
        textField1.setText("Player 1");
        textField2.setText("Player 2");
      } else {
        player1N = textField1.getText();

        if (!aiOnOrOff) {
          player2N = textField2.getText();
        } else {
          player2N = "Computer";
        }
        this.primaryStage.setScene(connect4Game(aiOnOrOff, player1N, player2N));

      }


    });

    /* Determines if a second player textfield is needed if the ai is on or not. */
    VBox vbox = new VBox();
    if (!aiOnOrOff) {
      vbox.getChildren().addAll(howToNameLabel, textField1, textField2, startGame);
    } else {
      vbox.getChildren().addAll(howToNameLabel, textField1, startGame);
    }

    vbox.setPadding(new Insets(120, 0, 0, 0));
    vbox.setSpacing(40);
    vbox.setPrefWidth(630);
    vbox.setPrefHeight(598);
    vbox.setAlignment(Pos.CENTER);
    gameI.setCenter(vbox);
    Scene nameInput = new Scene(gameI);

    /* Listener to draw attention off of the textfield. Stylistic preference. */
    textField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue && firstTime.get()) {
        gameI.requestFocus();
        firstTime.setValue(false);
      }
    });

    return nameInput;
  }

  /**
   * connect4Game is a method that takes various scenes and glues them together to create the main board game scene.
   *
   * <p>connect4Game scene consists of the menuBar, the statusBar, and the main playing area.
   *
   * @param aiOnOrOff Whether or not the user is playing the computer.
   * @param player1n The name player one choses.
   * @param player2n The name player two choses.
   * @return connect4Game The main scene of the board game.
   */
  private Scene connect4Game(boolean aiOnOrOff, String player1n, String player2n) {

    /* Glues together the menuBar, statusBar, and playing area for the main game scene. */
    BorderPane game = new BorderPane();
    game.setTop(menuBar());
    game.setCenter(statusBar(aiOnOrOff,player1n ,player2n));
    game.setBottom(playingArea());

    Scene connect4Game = new Scene(game);

    return connect4Game;
  }

  /**
   * playingArea is the combination of the tokens in their dropped places, the main board game grid, and the highligher
   * highlightMousedColumn that shows what column the user is hovering over.
   *
   * @return root The Parent pane in which the board grid and tokens are set together.
   */
  private Parent playingArea() {

    /* Glues together the played tokens, the board game grid, and the highlighter for column selection. */
    Pane root = new Pane();
    root.getChildren().add(tokenRoot);

    Shape gridShape = connect4Board();
    root.getChildren().add(gridShape);
    root.getChildren().addAll(highlightMousedColumn());

    return root;
  }

  /**
   * connect4Board creats a rectagnular shape with circles removed from it so that it looks
   * like a connect4 grid.
   *
   * @return shape The connect4 board game grid.
   */
  private Shape connect4Board() {

    /* Generating a fixed board sized based on columnWiodth. */
    Shape shape = new Rectangle((7 + 1) * columnWidth - 10, (6 + 1) * columnWidth - 25);

    /* Loop set up to subtract circles from the rectangle we just created. */
    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        Circle circle = new Circle(columnWidth / 2);
        circle.setCenterX(columnWidth / 2);
        circle.setCenterY(columnWidth / 2);
        circle.setTranslateX(c * (columnWidth + 5) + (columnWidth / 4));
        circle.setTranslateY(r * (columnWidth + 5) + (columnWidth / 4));

        shape = Shape.subtract(shape, circle);
      }
    }

    shape.setFill(Color.rgb(0, 0, 128));

    return shape;
  }

  /**
   * highlightMousedColumn generates 7 columns overtop of the connect4 board game grid but only one is shown based
   * on the user's mouse movement. This creates a highlighting affect to guide the user to select the column
   * they want to play.
   *
   * <p>This method was adapted from https://www.youtube.com/watch?v=B5H_t0A_C14, a basic tutorial on a
   * Connect4 implementation in JavaFX.
   *
   * @return list The "list" of columns, one will be hilighted while the rest are transparent based on
   * user mouse movement.
   */
  private List<Rectangle> highlightMousedColumn() {

    List<Rectangle> list = new ArrayList<>();

    /* Creates 7 columns that are transparent unless you have the mouse cursor overtop of one. */
    for (int c = 0; c < 7; c ++) {
      Rectangle rect = new Rectangle(columnWidth, (6 + 1) * columnWidth - 25);
      rect.setArcHeight(15);
      rect.setArcWidth(15);
      rect.setTranslateX(c * (columnWidth + 5) + (columnWidth / 4));
      rect.setFill(Color.TRANSPARENT);

      rect.setOnMouseEntered(e -> {
        Color playerColor;
        /* Sets the colour of the highlighter based on current player. */
        if (!aiOn) {
          if (getPlayer() == 1) {
            playerColor = Color.rgb(255, 0, 0, 0.2);
          } else {
            playerColor = Color.rgb(255, 255, 0, 0.2);
          }
        } else {
          playerColor = Color.rgb(255, 0, 0, 0.2);
        }
        rect.setFill(playerColor);
      });
      rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

      final int column = c;
      rect.setOnMouseClicked(e -> {
        setToken(token(getPlayer()), column, getPlayedRow(column));
        Color playerColor;
        /* Sets the colour of the highlighter based on current player. */
        if (!aiOn) {
          if (getPlayer() == 1) {
            playerColor = Color.rgb(255, 0, 0, 0.2);
          } else {
            playerColor = Color.rgb(255, 255, 0, 0.2);
          }
          rect.setFill(playerColor);
        } else {
          playerColor = Color.rgb(255, 0, 0, 0.2);
        }
        rect.setFill(playerColor);
      });
      list.add(rect);
    }

    return list;
  }

  /**
   * highlightWinningConnection highlights the winning configuration of four (or more) in a row by adding to the winning tokens
   * a small black dot.
   *
   * @param gameConfig The current (winning) configuration of the board.
   */
  private void highlightWinningConnection(GameConfig gameConfig) {

    ArrayList<Integer> rows = gameConfig.getWinRow();
    ArrayList<Integer> columns = gameConfig.getWinCol();
    ArrayList<Circle> shape = new ArrayList<Circle>();

    /* Loops through all the pairs of rows and columns and sets a black dot over the winning connections. */
    for(int i = 0; i < rows.size(); i++) {
    	shape.add(winningToken());
    	shape.get(i).setTranslateX(columns.get(i) * (columnWidth + 5) + (columnWidth / 4));
    	shape.get(i).setTranslateY(rows.get(i) * (columnWidth + 5) + (columnWidth / 4));
    }

    tokenRoot.getChildren().addAll(shape);
  }

  /**
   * token creates a circle that is displayed in the tokenRoot grid, ending up displaying in the
   * right location of the board game grid.
   *
   * @param player The player that just placed a token.
   * @return token The requested player's token (circle).
   */
  private Circle token(int player) {

    Circle token = new Circle(columnWidth / 2);

    if (player == 1) {
      token.setFill(Color.RED);
    } else {
      token.setFill(Color.YELLOW);
    }

      token.setCenterX(columnWidth / 2);
      token.setCenterY(columnWidth / 2);

    token.setEffect(new Lighting());

    return token;
  }

  /**
   * winningToken creates a black dot to overlay the winning tokens.
   * @return winDot the black dot shape
   */
  private Circle winningToken() {

    Circle winDot = new Circle(columnWidth / 6);
    winDot.setFill(Color.BLACK);

    winDot.setCenterX(columnWidth / 2);
    winDot.setCenterY(columnWidth / 2);

    winDot.setEffect(new Lighting());

    return winDot;
  }

  /**
   * setToken updates tokenRoot to have a token placed precisley where it should be in the conenct4
   * board game grid.
   *
   * <p>A nice animation plays from the top to the final location of the token in the grid. As well,
   * if the game ends after placing a token, the winnerPopup method is called so the winner is shown
   * to the user.
   *
   * @param token The token of the player who just made a move.
   * @param col The column choice of the user or the computer.
   * @param row The row the token lands in from the column choice of the user or the computer.
   */
  private void setToken(Circle token, int col, int row) {

    /* Won't allow anymore tokens to be played as soon as a winner is found. */
    if (!getGameOver()) {

      /* Makes sure a user can't place a token in a full column, using the fullColumn method from gameConfig. */
      while (!(config.fullColumn(config.getBoard(), col))) {

        /* Updates gameConfig with the move the player made. */
        config.makeMove(config.getBoard(), col, getPlayer());

        /* Updates the gui with the move just made by the player. */
        tokenRoot.getChildren().add(token);
        token.setTranslateX(col * (columnWidth + 5) + (columnWidth / 4));

        /* Creates an animation to drop the token into the correct place. */
        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), token);
        animation.setToY(row * (columnWidth + 5) + (columnWidth / 4));
        animation.play();

        /* Changes to the right player and updates the move counters in the statusBar. */
        if (getPlayer() == 1) {
          setPlayer(2);
          setMoveCount(1);
        } else {
          setPlayer(1);
          setMoveCount(2);
        }

        /* Determining if there is a winner. */
        config.checkForWinner();

        if (config.getWinner() != 0) {
          setGameOver(true);
          winnerPopup(aiOn);
          if (config.getWinner() != -1) {
           animation.onFinishedProperty().set(e -> highlightWinningConnection(config));
          }
          /* Breaks out of the loop as the game is over. */
          break;
        }

        animation.onFinishedProperty().set(e -> {

          /* If playing against the computer player it will make its move when called. */
          if (aiOn && getPlayer() == 2) {

            Circle aiToken = token(2);
            config.makeMove(config.getBoard(), ai.aiMove(config), 2);

            tokenRoot.getChildren().add(aiToken);
            aiToken.setTranslateX(config.getColPlayed() * (columnWidth + 5) + (columnWidth / 4));

            /* Computer player's tokens have an animation as well when it makes it's choice. */
            TranslateTransition compAnimation = new TranslateTransition(Duration.seconds(0.5), aiToken);
            compAnimation.setToY(config.getRowPlayed() * (columnWidth + 5) + (columnWidth / 4));
            compAnimation.play();

            setPlayer(1);
            setMoveCount(2);

            /* Checks to see if there is a winner after making a computer move. */
            config.checkForWinner();

            if (config.getWinner() != 0) {
              setGameOver(true);
              winnerPopup(aiOn);
              if (config.getWinner() != -1) {
                compAnimation.onFinishedProperty().set(c -> highlightWinningConnection(config));
              }
            }

          }

        });

        break;
      }
    }
  }

  /**
   * statusBar is a Hbox that contains pertinant game information such as the two players
   * and how many moves they have made during the course of the game.
   *
   * @param aiOnOrOff Whether or not the user is playing against the computer.
   * @param player1n The name player one choses.
   * @param player2n The name player two choses.
   * @return hbox The combination of all the elements in the statusBar to a Parent.
   */
  private Parent statusBar(boolean aiOnOrOff, String player1n, String player2n) {

    /* Setting up the various elements that make the statusBar look the way it does. */
    Circle player1 = new Circle(columnWidth / 5, Color.RED);
    Circle player2 = new Circle(columnWidth / 5, Color.YELLOW);
    player1.setEffect(new Lighting());
    player2.setEffect(new Lighting());

    Label p1Name = new Label(player1n);
    p1Name.setFont(Font.font ("Lucida Sans Unicode", 16));
    p1Name.setTextFill(Color.RED);
    p1Name.setMinWidth(120);
    p1Name.setMaxWidth(120);
    p1Name.setAlignment(Pos.CENTER_LEFT);

    Label p2Name = new Label(player2n);
    p2Name.setFont(Font.font ("Lucida Sans Unicode", 16));
    p2Name.setTextFill(Color.YELLOW);
    p2Name.setMinWidth(120);
    p2Name.setMaxWidth(120);
    p2Name.setAlignment(Pos.CENTER_RIGHT);

    Label compName = new Label("Computer");
    compName.setFont(Font.font ("Lucida Sans Unicode", 16));
    compName.setTextFill(Color.YELLOW);
    compName.setMinWidth(120);
    compName.setMaxWidth(120);
    compName.setAlignment(Pos.CENTER_RIGHT);

    Label p1MoveLabel = new Label();
    p1MoveLabel.setFont(Font.font ("Lucida Sans Unicode", 14));
    p1MoveLabel.setTextFill(Color.RED);
    p1MoveLabel.textProperty().bind(Bindings.format("Moves: %d", p1MoveCount));

    Label p2MoveLabel = new Label();
    p2MoveLabel.setFont(Font.font ("Lucida Sans Unicode", 14));
    p2MoveLabel.setTextFill(Color.YELLOW);
    p2MoveLabel.textProperty().bind(Bindings.format("Moves: %d", p2MoveCount));

    Region centerRegion = new Region();
    HBox.setHgrow(centerRegion, Priority.ALWAYS);

    /* Starting to gather elements into an Hbox to keep everything aligned. */
    HBox hbox = new HBox(player1, p1Name, p1MoveLabel, centerRegion, p2MoveLabel);

    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(20);
    hbox.setStyle("-fx-background-color: #000080");

    if (aiOnOrOff) {
      hbox.getChildren().addAll(compName, player2);
    } else {
      hbox.getChildren().addAll(p2Name, player2);
    }

    hbox.setAlignment(Pos.CENTER);

    return hbox;
  }

  /**
   * menuBar is a method that generates a menu at the top of all scenes for GUI navigation,
   * computer difficulty settings, and general game/AI/Team information.
   *
   * @return menuBar The menuBar to be set at the top of all scenes.
   */
  private MenuBar menuBar() {

    /* File menu for easy navigation of the GUI. Options are menu and to exit the program. */
    Menu fileMenu = new Menu("File");
    MenuItem menu = new MenuItem("Menu");
    menu.setOnAction(e -> {
      this.primaryStage.setScene(startScreen());
      newGame(false);
    });
    fileMenu.getItems().add(menu);
    fileMenu.getItems().add(new SeparatorMenuItem());
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(e -> Platform.exit());
    fileMenu.getItems().add(exit);

    /* Difficulty settings menu for the ComputerPlayer, choice of depths from 0 to 7. */
    Menu maxDepthMenu = new Menu("Difficulty/Depth Setting");
    MenuItem zero = new MenuItem("Zero (Easy)");
    zero.setOnAction(e -> ai.setMaxDepth(0));
    MenuItem one = new MenuItem("One");
    one.setOnAction(e -> ai.setMaxDepth(1));
    MenuItem two = new MenuItem("Two");
    two.setOnAction(e -> ai.setMaxDepth(2));
    MenuItem three = new MenuItem("Three (Default)");
    three.setOnAction(e -> ai.setMaxDepth(3));
    MenuItem four = new MenuItem("Four");
    four.setOnAction(e -> ai.setMaxDepth(4));
    MenuItem five = new MenuItem("Five");
    five.setOnAction(e -> ai.setMaxDepth(5));
    MenuItem six = new MenuItem("Six");
    six.setOnAction(e -> ai.setMaxDepth(6));
    MenuItem seven = new MenuItem("Seven (Difficult)");
    seven.setOnAction(e -> ai.setMaxDepth(7));
    maxDepthMenu.getItems().addAll(zero, one, two, three, four, five, six, seven);

    /* About menu item. Gives information on connect4, negamax, and the team. */
    Menu aboutMenu = new Menu("About");
    MenuItem c4 = new MenuItem("Connect 4");
    c4.setOnAction(e -> aboutConnect4());
    MenuItem negamaxAI = new MenuItem("Negamax Algorithm");
    negamaxAI.setOnAction(e -> aboutNegamax());
    MenuItem team = new MenuItem("Team");
    team.setOnAction(e -> aboutTeam());
    aboutMenu.getItems().addAll(c4, negamaxAI, team);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu, maxDepthMenu, aboutMenu);

    return menuBar;
  }

  /**
   * newGame is a method that is called whenever the user returns to the mainMenu, either by using the menuBar
   * or after a game is over and they click the return to menu button.
   *
   * <p>Main menu resets the game state so that the previous grid of tokens (tokenRoot) is wiped clean, the
   * move counters are reset, and a new GameConfig is called.
   *
   * @param aiOnOrOff Resets the state of the aiOn variable to false.
   */
  private void newGame(boolean aiOnOrOff) {

    config = new GameConfig();
    //ai = new ComputerPlayer(); // When commented out, keeps the last difficulty setting.
    player = 1;
    tokenRoot = new Pane();
    p1MoveCount = new SimpleIntegerProperty(0);
    p2MoveCount = new SimpleIntegerProperty(0);
    connect4Game(aiOnOrOff, player1N, player1N);
    gameOver = false;
    aiOn = aiOnOrOff;
    firstTime.setValue(true);

  }

  /**
   * aboutConnect4 is a popup window designed to inform the user about the game of Connect4. This is accessed
   * from the menuBar.
   */
  private void aboutConnect4() {

    Stage stage = new Stage();
    stage.initOwner(primaryStage);
    stage.setTitle("About Connect 4");
    stage.setResizable(false);

    Text connect4 = new Text("Connect Four (also known as Captain's Mistress, Four Up, Plot Four, Find Four, Four in a Row, Four in a Line and Gravitrips (in Soviet Union)) is a two-player connection game in which the players first choose a color and then take turns dropping one colored disc from the top into a seven-column, six-row vertically suspended grid.\n\nThe pieces fall straight down, occupying the next available space within the column.\n\nThe objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs.\n\nConnect Four is a solved game. The first player can always win by playing the right moves.");
    BorderPane layout = new BorderPane();
    layout.setPrefSize(400, 260);
    layout.setPadding(new Insets(15, 15, 15, 15));
    connect4.setWrappingWidth(400);
    layout.setCenter(connect4);
    layout.setOnMouseClicked(e -> stage.close());
    stage.setScene(new Scene(layout));

    stage.show();
  }

  /**
   * aboutNegamax is a popup window designed to inform the user about the computer's move algorithm. This is accessed
   * from the menuBar.
   */
  private void aboutNegamax() {

    Stage stage = new Stage();
    stage.initOwner(primaryStage);
    stage.setTitle("About Negamax Search Algorithm");
    stage.setResizable(false);

    Text negamax = new Text("negamax is a recursive algorithm that searches for the best possible column choice by relying on the fact that the board value for the player initially called for is the negation of the value for the opposing player.\n\nA game tree is created from a root node (the first call of negamax), and it works its way down a branch comparing each player's best move until a certain depth (the specified amount of moves into the future) is reached. It then returns back up the tree, comparing different branches to the best one its found so far, setting the best column during this comparison. Eventually it gets back to the root node and sets the overall best column that maximizes the chances of winning for the player it was called for (in this case, the computer).\n\nFor further reading, please visit the Wikipedia page for Negamax (https://en.wikipedia.org/wiki/Negamax).");
    BorderPane layout = new BorderPane();
    layout.setPrefSize(400, 250);
    layout.setPadding(new Insets(15, 15, 15, 15));
    negamax.setWrappingWidth(400);
    layout.setCenter(negamax);
    layout.setOnMouseClicked(e -> stage.close());
    stage.setScene(new Scene(layout));

    stage.show();
  }

  /**
   * aboutTeam is a popup window designed to inform the user about the creators of this game. This is accessed
   * from the menuBar.
   */
  private void aboutTeam() {

    Stage stage = new Stage();
    stage.initOwner(primaryStage);
    stage.setTitle("About The Team");
    stage.setResizable(false);

    Text team = new Text("This Connect 4 game was programmed in Java and JavaFX by T02 - Team 1!\n\nThe members are as follows:\n\nMatthew Cox\nTony Wong\nMinnie Thai\nNathaniel Habtegergesa\nNatinael Ayalew");
    BorderPane layout = new BorderPane();
    layout.setPrefSize(400, 150);
    layout.setPadding(new Insets(15, 15, 15, 15));
    team.setWrappingWidth(400);
    layout.setCenter(team);
    layout.setOnMouseClicked(e -> stage.close());
    stage.setScene(new Scene(layout));

    stage.show();

  }

  /**
   * winnerPopup is a popup window that informs the user who has won the game when the game is over. This could be
   * player 1, player 2, the computer, or if it ended in a draw.
   *
   * @param aiOnOrOff Whether or not the user is playing against the computer.
   */
  private void winnerPopup(boolean aiOnOrOff) {

    /* Used to make sure the sound files are actually found in the folder. */
    ClassLoader classLoader = getClass().getClassLoader();

    Stage stage = new Stage();
    stage.initOwner(primaryStage);
    stage.setTitle("Game Over!");
    stage.setResizable(false);

    BorderPane layout = new BorderPane();
    layout.setPrefSize(300, 175);
    layout.setPadding(new Insets(15, 15, 15, 15));

    /* Loading images for the various popup backgrounds. */
    Image drawBackground = new Image("/gui_images/DrawWinner.png");
    Image redBackground = new Image("/gui_images/RedWinner.png");
    Image yellowBackground = new Image("/gui_images/YellowWinner.png");
    BackgroundSize bsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
    if (config.getWinner() == -1) {
      layout.setBackground(new Background(new BackgroundImage(drawBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bsize)));
    } else if (config.getWinner() == 1) {
      layout.setBackground(new Background(new BackgroundImage(redBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bsize)));
    } else if (config.getWinner() == 2) {
      layout.setBackground(new Background(new BackgroundImage(yellowBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bsize)));
    }

    /* Text that goes ontop of the background to show who has won. */
    Text p1Win = new Text("PLAYER 1 WINS!");
    p1Win.setFont(Font.font ("Lucida Sans Unicode", 18));
    p1Win.setFill(Color.RED);
    p1Win.setTextAlignment(TextAlignment.CENTER);
    Text p2Win = new Text("PLAYER 2 WINS!");
    p2Win.setFont(Font.font ("Lucida Sans Unicode", 18));
    p2Win.setFill(Color.YELLOW);
    p2Win.setTextAlignment(TextAlignment.CENTER);
    Text compWin = new Text("THE AI WINS!");
    compWin.setFont(Font.font ("Lucida Sans Unicode", 18));
    compWin.setFill(Color.YELLOW);
    compWin.setTextAlignment(TextAlignment.CENTER);
    Text draw = new Text("DRAW!");
    draw.setFont(Font.font ("Lucida Sans Unicode", 18));
    draw.setTextAlignment(TextAlignment.CENTER);
    draw.setFill(Color.WHITE);

    /* Sound files give an audio cue to whether you should be happy, sad, or content. */
    File winSoundFile = new File(classLoader.getResource("./gui_sounds/Win.wav").getFile());
    Media winSound = new Media(winSoundFile.toURI().toString());
    MediaPlayer playWinSound = new MediaPlayer(winSound);

    File loseSoundFile = new File(classLoader.getResource("./gui_sounds/Lose.wav").getFile());
    Media loseSound = new Media(loseSoundFile.toURI().toString());
    MediaPlayer playLoseSound = new MediaPlayer(loseSound);

    File drawSoundFile = new File(classLoader.getResource("./gui_sounds/Draw.wav").getFile());
    Media drawSound = new Media(drawSoundFile.toURI().toString());
    MediaPlayer playDrawSound = new MediaPlayer(drawSound);

    VBox vbox = new VBox();

    if (config.getWinner() == 1) {
      vbox.getChildren().add(p1Win);
      playWinSound.setAutoPlay(true);
    } else if (config.getWinner() == 2) {
      if (aiOnOrOff) {
        vbox.getChildren().add(compWin);
        playLoseSound.setAutoPlay(true);
      } else {
        vbox.getChildren().add(p2Win);
        playWinSound.setAutoPlay(true);
      }
    } else {
      vbox.getChildren().add(draw);
      playDrawSound.setAutoPlay(true);
    }

    /* Setting up return to main menu button to play the game again. */
    Button menu = new Button();
    menu.setPadding(Insets.EMPTY);
    Image menuButton = new Image("/gui_images/MenuButton.png");
    Image menuButtonHighlighted = new Image("/gui_images/MenuButtonHighlighted.png");
    menu.setGraphic(new ImageView(menuButton));
    menu.setOnMouseEntered(e -> menu.setGraphic(new ImageView(menuButtonHighlighted)));
    menu.setOnMouseExited(e -> menu.setGraphic(new ImageView(menuButton)));
    menu.setOnAction(e -> {
      this.primaryStage.setScene(startScreen());
      newGame(false);
      stage.close();
    });

    vbox.getChildren().add(menu);
    vbox.setSpacing(25);
    vbox.setAlignment(Pos.CENTER);

    layout.setCenter(vbox);
    stage.setScene(new Scene(layout));

    stage.show();
  }

  /**
   * setPlayer updates the current player after the previous player places their token.
   *
   * @param player The next player whose turn it is.
   */
  private void setPlayer(int player) {

    if (player == 1) {
      this.player = 1;
    } else {
      this.player = 2;
    }
  }

  /**
   * getPlayer retrieves the current player of the ongoing connect4 game.
   *
   * @return player The player whose turn it is.
   */
  private int getPlayer() {

    return player;
  }

  /**
   * getPlayedRow returns the row that was just played in the connect4 game.
   *
   * @param column The column choice of the user or computer player.
   * @return thePlayedRow The row that was just played after making a connect4 move.
   */
  private int getPlayedRow(int column) {

    GameConfig toGetRightRow = new GameConfig(config);
    toGetRightRow.makeMove(toGetRightRow.getBoard(), column, getPlayer());
    return toGetRightRow.getRowPlayed();
  }

  /**
   * setMoveCount updates the move counters by 1 for players 1 and 2 depending on which player just played.
   *
   * @param player The player who just played.
   */
  private void setMoveCount(int player) {

    if (player == 1) {
      this.p1MoveCount.set(p1MoveCount.get()+1);
    } else {
      this.p2MoveCount.set(p2MoveCount.get()+1);
    }
  }

  /**
   * getMoveCount retrieves the current move count of a specified player (passed as an argument).
   *
   * @param player The specific player whose move count is requested.
   * @return MoveCount Either p1MoveCount or p2MoveCount based on the player agrument.
   */
  private IntegerProperty getMoveCount(int player) {

    if (player == 1) {
      return p1MoveCount;
    } else {
      return p2MoveCount;
    }
  }

  /**
   * setGameOver sets the gameOver variable to true if the game ends after a token has been placed.
   *
   * @param state Whether or not the game ended or not.
   */
  private void setGameOver(boolean state) {

    if (state == true) {
      this.gameOver = true;
    } else if (state == false) {
      this.gameOver = false;
    }
  }

  /**
   * getGameOver retrieves the game state that is stored in the gameOver variable.
   *
   * @return gameOver The state of the game (ongoing or finished).
   */
  private boolean getGameOver() {

    return gameOver;
  }

}