package connect4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * GameConfig contains the main logic for the Connect4 game and handles updating the board and displaying it to the user.
 *
 * <p>GameConfig creates a 2D array to hold values for freespaces, the first player, and the second player. It has a number of methods
 * to handle user and computer moves, the previously played column, who the winner is, and printing the board to the console.
 *
 * @author T02-1 - Tony Wong, Matthew Cox
 * @version 1.1
 */
public class GameConfig {

  /**
   * board stores the positions of the Connect4 players tokens in a 2D integer array.
   */
  private int[][] board;

  /**
   * winner stores the value of the game's winner.
   *
   * <p>0 is the value for an ongoing game, 1 if the first player wins,
   * 2 if the second player wins, and -1 if the game ends in a draw.
   */
  private int winner;

  /**
   * colPlayed stores the column choice of the move that was just made.
   */
  private int colPlayed;

  /**
   * rowPlayed stores the row of the move that was just made.
   */
  private int rowPlayed;

  /**
   * winCol is an ArrayList that stores all the winning connection's column positions (that are paired with winRow) to highlight
   * the winning connections in the GUI version of the game.
   */
  private ArrayList<Integer> winCol = new ArrayList<Integer>();

  /**
   * winRow is an ArrayList that stores all the winning connection's row positions (that are paired with winCol) to highlight
   * the winning connections in the GUI version of the game.
   */
  private ArrayList<Integer> winRow = new ArrayList<Integer>();

  /**
   * Default constructor for GameConfig, sets up the board with free spaces and the winner to 0.
   */
  public GameConfig() {

    setWinner(0);
    setColPlayed(0);
    setRowPlayed(0);
    board = new int[6][7];

    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        board[r][c] = 0;
      }
    }
  }

  /**
   * Copy constructor for GameConfig.
   *
   * @param oldConfig The old configuration that is being copied into a new configuration.
   */
  public GameConfig(GameConfig oldConfig) {

    setWinner(oldConfig.getWinner());
    setColPlayed(oldConfig.getColPlayed());
    board = new int[6][7];

    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        board[r][c] = oldConfig.getBoard()[r][c];
      }
    }
  }

  /**
   * getBoard retrieves the current board stored in a GameConfig object.
   *
   * @return board The state of the Connect4 game after a number of moves have occured.
   */
  public int[][] getBoard() {

    return board;
  }

  /**
   * setBoard sets a GameConfig object's board to a new board being passed in as an argument.
   *
   * @param board The 2D array containing the state of a Connect4 game.
   */
  public void setBoard(int[][] board) {

    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        this.board[r][c] = board[r][c];
      }
    }
  }

  /**
   * getWinCol returns the ArrayList of winning connection's column positions.
   *
   * @return winCol ArrayList that stores all the winning connection's column positions.
   */
  public ArrayList<Integer> getWinCol() {

    return winCol;
  }

  /**
   * getWinRow returns the ArrayList of winning connection's row positions.
   *
   * @return winRow ArrayList that stores all the winning connection's row positions.
   */
  public ArrayList<Integer> getWinRow() {

  	return winRow;
  }

  /**
   * getColPlayed retrieves the column that was just played after a move was made.
   *
   * @return colPlayed The column choice of the move that was just made.
   */
  public int getColPlayed() {

    return colPlayed;
  }

  /**
   * setColPlayed sets the column that was just played after a move was made.
   *
   * @param colPlayed The column that was just played.
   */
  public void setColPlayed(int colPlayed) {

    if (colPlayed >= 0 && colPlayed <= 6) {

      this.colPlayed = colPlayed;
    }
  }

  /**
   * getRowPlayed retrieves the row that was just played after a move was made.
   *
   * @return rowPlayed The associated row of a column choice of the move that was just made.
   */
  public int getRowPlayed() {

    return rowPlayed;
  }

  /**
   * setRowPlayed sets the row that was just played after a move was made.
   *
   * @param rowPlayed The row associated with the column that was just played.
   */
  public void setRowPlayed(int rowPlayed) {

    if (rowPlayed >= 0 && rowPlayed <= 6) {

      this.rowPlayed = rowPlayed;
    }
  }

  /**
   * getWinner retrieves the current game state (0 ongoing, 1 player one win, 2 player two win, -1 draw).
   *
   * @return winner The winner based on the current board configuration.
   */
  public int getWinner() {

    return winner;
  }

  /**
   * setWinner sets the current game state after checking for a winner.
   *
   * @param winner The winner based on the current board configuration.
   */
  public void setWinner(int winner) {

    if (winner >= -1 && winner <= 2) {

      this.winner = winner;
    }
  }

  /**
   * fullColumn takes in the current game board and a column selection as arguments to determine if that column is full or not.
   *
   * @param board The current game board.
   * @param col The column being checked.
   * @return true/false Whether or not the column is indeed full.
   */
  public boolean fullColumn(int[][] board, int col) {

    if (board[0][col] != 0) {

      return true;
    } else {

      return false;
    }
  }

  /**
   * makeMove updates the board to reflect a user or computer's desired move.
   *
   * <p>makeMove checks to see whether or not the desired column is full before
   * placing the token in the appropriate row.
   *
   * @param board The 2D array containing the state of a Connect4 game.
   * @param col The column that a user or the computer wants to play in.
   * @param player The current player trying to make the move in the game.
   */
  public void makeMove(int[][] board, int col, int player) {

    if (!fullColumn(board, col)) {

      for (int r = 1; r < 6; r ++) {

        if (board[5][col] == 0) {

          board[5][col] = player;
          setBoard(board);
          setColPlayed(col);
          setRowPlayed(5);
          break;
        } else if (board[r][col] != 0) {

          board[r-1][col] = player;
          setBoard(board);
          setColPlayed(col);
          setRowPlayed(r-1);
          break;
        }
      }
    }
  }

  /**
   * draw is a method that prints the current board configuration to the console.
   */
  public void draw() {

    /* Easy viewing of each column and its associated number. */
    System.out.println("\n| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
    System.out.println();

    /* A loop designed to print every value out in the 2D array. */
    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        /* Printing out every column with just one "|" except the far right column. */
        if (c != 6) {
          if (board[r][c] == 1) {
            System.out.print("| " + "X" + " ");
          } else if (board[r][c] == 2) {
            System.out.print("| " + "O" + " ");
          } else {
            System.out.print("| " + "_" + " ");
          }

        /* Prints an extra "|" on the right to make the board look nice in the far right column. */
        } else {
          if (board[r][c] == 1) {
            System.out.println("| " + "X" + " |");
          } else if (board[r][c] == 2) {
            System.out.println("| " + "O" + " |");
          } else {
            System.out.println("| " + "_" + " |");
          }
        }
      }
    }
  }

  /**
   * checkForWinner takes in the current game configuration as an argument and sets the winner
   * variable to 1 for the first player, 2 for the second player, 0 for an ongoing game,
   * or -1 for a draw.
   */
  public void checkForWinner() {

    /* Variable to track if the winner was set yet. */
    boolean winnerSet = false;

    /* Checks for a winner in a horizontal row. */
    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 4; c ++) {

        if (board[r][c] != 0 &&
        board[r][c] == board[r][c+1] &&
        board[r][c] == board[r][c+2] &&
        board[r][c] == board[r][c+3]) {

          setWinner(board[r][c]);
          winRow.addAll(Arrays.asList(r, r, r, r));
          winCol.addAll(Arrays.asList(c, c+1, c+2, c+3));
          winnerSet = true;
        }
      }
    }


    /* Checks for a winner in a vertical column. */
    for (int r = 0; r < 3; r ++) {
      for (int c = 0; c < 7; c ++) {

        if (board[r][c] != 0 &&
        board[r][c] == board[r+1][c] &&
        board[r][c] == board[r+2][c] &&
        board[r][c] == board[r+3][c]) {

          setWinner(board[r][c]);
          winRow.addAll(Arrays.asList(r, r+1, r+2, r+3));
          winCol.addAll(Arrays.asList(c, c, c, c));
          winnerSet = true;
        }
      }
    }


    /* Checks for a winner in a ascending diagonal. */
    for (int r = 5; r > 2; r --) {
      for (int c = 0; c < 4; c ++) {

        if(board[r][c] != 0 &&
        board[r][c] == board[r-1][c+1] &&
        board[r][c] == board[r-2][c+2] &&
        board[r][c] == board[r-3][c+3]) {

          setWinner(board[r][c]);
          winRow.addAll(Arrays.asList(r, r-1, r-2, r-3));
          winCol.addAll(Arrays.asList(c, c+1, c+2, c+3));
          winnerSet = true;
        }
      }
    }

    /* Checks for a winner in a descending diagonal. */
    for (int r = 5; r > 2; r --) {
      for (int c = 6; c > 2; c --) {

        if(board[r][c] != 0 &&
        board[r][c] == board[r-1][c-1] &&
        board[r][c] == board[r-2][c-2] &&
        board[r][c] == board[r-3][c-3]) {

          setWinner(board[r][c]);
          winRow.addAll(Arrays.asList(r, r-1, r-2, r-3));
          winCol.addAll(Arrays.asList(c, c-1, c-2, c-3));
          winnerSet = true;
        }
      }
    }

    /* Sets the winner to 0 (ongoing) if the board still has empty spaces. */
    if (winnerSet == false) {
      for (int r = 0; r < 6; r ++) {
        for (int c = 0; c < 7; c ++) {

          if (board[r][c] == 0) {

            setWinner(board[r][c]);
            winnerSet = true;
          }
        }
      }
    }

    /* Sets the winner to -1 (draw) if the board is full and there is still no winner. */
    if (winnerSet == false) {
      if (board[0][0] != 0 &&
      board[0][1] != 0 &&
      board[0][2] != 0 &&
      board[0][3] != 0 &&
      board[0][4] != 0 &&
      board[0][5] != 0 &&
      board[0][6] != 0) {

        setWinner(-1);
      }
    }
  }

}