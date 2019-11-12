package connect4text;

import connect4.*;
import java.util.Scanner;

/**
 * Game contains the two game types the user can chose, player vs. player or player vs. computer. It prompts the user to make this choice
 * then starts the game of Connect4.
 *
 * @author T02-1 - Nathaniel Habtegergesa, Matthew Cox
 * @version 1.1
 */
public class Game {

  /**
   * main method to start the game of Connect4.
   */
  public static void main(String[] args) {

    Game connect4 = new Game();
    System.out.println("\n         Welcome to the game of\n               Connect4!");

    /* Gets the user to choose which game type the want to play. */
    Scanner input = new Scanner(System.in);
    String gameType = getGameType(input);

    /* Starts a player vs. player game. */
    if (gameType.equals("H")) {

      System.out.println("\nSetting up your 2-player Connect4 game!");
      connect4.pvp();

    /* Starts a player vs. computer game after the user selects a maxDepth for the AI. */
    } else if (gameType.equals("C")) {

      System.out.println("\nSetting up your Human vs. AI Connect4 game!");
      Scanner maxDepth = new Scanner(System.in);
      int depth = getDepth(maxDepth);
      connect4.pvai(depth);
    }

  }

  /**
   * config stores an instance of GameConfig to manage the ongoing Connect4 game.
   */
  private GameConfig config = new GameConfig();

  /**
   * human is an instance of HumanPlayer to take input from the user(s).
   */
  private HumanPlayer human = new HumanPlayer();

  /**
   * ai is an instance of ComputerPlayer to play against the user, a maxDepth is set later by the user.
   */
  private ComputerPlayer ai = new ComputerPlayer();

  /**
   * pvp is a method to handle the turns of a player vs. player game.
   */
  private void pvp() {

    /* Draws the empty board before the first player's turn. */
    config.draw();

    /* Loops until the game has a winner or it ends in a draw. */
		while (true) {

      /* First player's turn. */
      humanTurn(1);

      /* Checks to see if the move the first player just made won them the game. */
      config.checkForWinner();
      if (config.getWinner() == 1) {

        /* Draws the updated board after the first player has won the game. */
        config.draw();
				System.out.println("\n   Player 1 wins the game!");
        /* Exits the game as it is over. */
				break;
			}

      /* Draws the updated board after the first player has taken their turn. */
      config.draw();

      /* Second player's turn. */
      humanTurn(2);

      /* Checks to see if the move the second player just made won them the game. */
      config.checkForWinner();
      if (config.getWinner() == 2) {

        /* Draws the updated board after the second player has won the game. */
        config.draw();
				System.out.println("\n   Player 2 wins the game!");
        /* Exits the game as it is over. */
        break;
			}

      /* Draws the updated board after the second player has taken their turn. */
      config.draw();

      /* Checks to see if the game has ended in a draw. This comes after the second player's turn
      as it is only possible after an even amount of moves has been made (42 total). */
      if (config.getWinner() == -1) {

				System.out.println("\n     Game ended in a draw!");
        /* Exits the game as it is over. */
        break;
      }
		}
  }

  /**
   * pvai is a method to handle the turns of a player vs. computer game.
   *
   * @param depth The maxDepth the user chose for the AI.
   */
  private void pvai(int depth) {

    /* Sets the computer's searching depth. */
    ai.setMaxDepth(depth);
    System.out.println("\nMaximum search depth was set to " + depth + ". Good luck!");

    /* Draws the empty board before the first player's turn. */
    config.draw();

    /* Loops until the game has a winner or it ends in a draw. */
    while (true) {

      /* First player's turn. */
      humanTurn(1);

      /* Checks to see if the move the first player just made won them the game. */
      config.checkForWinner();
      if (config.getWinner() == 1) {

        /* Draws the updated board after the first player has won the game. */
        config.draw();
        System.out.println("\n   Player 1 wins the game!");
        /* Exits the game as it is over. */
        break;
      }

      /* Draws the updated board after the first player has taken their turn. */
      config.draw();

      /* Computer player's turn. */
      aiTurn(config);

      /* Checks to see if the move the computer just made won them the game. */
      config.checkForWinner();
      if (config.getWinner() == 2) {

        /* Draws the updated board after the computer has won the game. */
        config.draw();
        System.out.println("\n   The AI has won the game!");
        /* Exits the game as it is over. */
        break;
      }

      /* Draws the updated board after the computer has taken its turn. */
      config.draw();

      /* Checks to see if the game has ended in a draw. This comes after the computer player's turn
      as it is only possible after an even amount of moves has been made (42 total). */
      if (config.getWinner() == -1) {

        System.out.println("\n     Game ended in a draw!");
        /* Exits the game as it is over. */
        break;
      }
    }
  }

  /**
   * humanTurn prompts the current player (taken as an argument) for their column choice. It then updates
   * the Connect4 board after the make their choice.
   *
   * <p>The method will also check to see if the column they selected was full or not. It will
   * continue to prompt them for a column choice if the column is full.
   *
   * @param player The current player of the Conenct4 game.
   */
  private void humanTurn(int player) {

    System.out.println("\nPlayer " + player + " it's your turn to play!");

    int col = -1;

    /* Loops until the user makes a column choice that isn't full. */
    while (col == -1) {
      col = human.getColumn();
      if (config.fullColumn(config.getBoard(), col)) {
        System.out.println("\nChosen column (" + (col+1) + ") is full!");
        col = -1;
      }
    }

    /* Updates the board with the move of the human player. */
    config.makeMove(config.getBoard(), col, player);
  }

  /**
   * aiTurn takes the current game configuration in as an argument and makes the
   * computer's move based on the best column it chooses.
   *
   * @param gameConfig The current configuration of the Conenct4 game.
   */
  private void aiTurn(GameConfig gameConfig) {

    System.out.println("\nIt's the computer's turn to play!");

    /* Updates the board with the move of the computer player. */
    config.makeMove(config.getBoard(), ai.aiMove(config), 2);
  }

  /**
   * getGameType is a method that takes in Scanner input from the user to choose a player vs. player
   * or player vs. computer game.
   *
   * @param in The user's choice of game type.
   * @return gameType The type of game chosen by the user.
   */
  private static String getGameType(Scanner in) {

    System.out.print("\n         Choose your game type!\n     Enter H for a 2-player game or\nEnter C to play against the computer: ");

    /* Loops until the user has chosen H for pvp or C for pvai. */
    while (!in.hasNext("[HC]")) {
      System.out.println("\nThat wasn't a valid game type!");
      System.out.print("\nPlease enter H or C to play: ");
      in.next();
    }

    String gameType = in.next();
    return gameType;
  }

  /**
   * getDepth is a method that takes in Scanner input from the user to choose the maxDepth for the AI
   * after they chose to play against the computer.
   *
   * @param in The user's choice for maxDepth (between 0-7).
   * @return depth The depth the user chose for the AI.
   */
  private static int getDepth(Scanner in) {

    System.out.print("\nChoose a maximum search depth between 0 (easy) to 7 (difficult) for the AI: ");

    /** Loops until a useable depth value is selected and returned. */
    while (true) {

      if (!in.hasNextInt()) {
        /* Tells the user they need to type a number. */
        System.out.println("\nThat wasn't even a number!");
        System.out.print("\nPlease enter a maximum search depth between 0 and 7: ");
      } else {

        int depth = in.nextInt();
        /* Validates the depth is between 0 and 7. */
        if (depth >= 0 && depth <= 7) {
          return depth;
        }
        /* Tells the user that wasn't in the right range. */
        System.out.println("\nYour choice was not in the accepted range!");
        System.out.print("\nPlease enter a maximum search depth between 0 and 7: ");
      }

      in.nextLine();
    }
  }

}