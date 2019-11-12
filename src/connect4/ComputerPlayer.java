package connect4;

import java.util.ArrayList;
import java.util.Random;

/**
 * ComputerPlayer is used to create an instance of a Connect4 AI that chooses the best column to play given the
 * current game configuration and a maximum search depth for it's negamax algorithm.
 *
 * <p>ComputerPlayer relies most heavily on a recursive algorithm defined in the negamax method. It utlizes an
 * evaluate method to determine how best to move so that it can beat the user. The difficulty level of the
 * ComputerPlayer can be scaled using maxDepth values from 0 (easy) to 7 (difficult). The algorithm could go
 * beyond a maxDepth of 7, but larger numbers start to significantly slow the performance of the game.
 *
 * @author T02-1 - Matthew Cox
 * @version 1.2
 */
public class ComputerPlayer extends GameConfig {

  /**
   * maxDepth stores the depth that the negamax method searches to.
   *
   * <p>Essentially the maximum amount of times negamax will call itself to find the best move for the computer.
   */
  private int maxDepth;

  /**
   * bestCol stores the column the negamax algorithm determines is the best move for the computer.
   */
  private int bestCol;

  /**
   * evaluationBoard contains how many winning 4-in-a-row combinations there are from each space.
   *
   * <p>This static board is used to evaluate the human or computer player's current "score" or
   * how likely they are to win given the current configuration of the game board.
   */
  private static int[][] evaluationBoard = {{3,4,5,7,5,4,3},
                                            {4,6,8,10,8,6,4},
                                            {5,8,11,13,11,8,5},
                                            {5,8,11,13,11,8,5},
                                            {4,6,8,10,8,6,4},
                                            {3,4,5,7,5,4,3}};

  /**
   * Default constructor for the ComputerPlayer class.
   */
  public ComputerPlayer() {

    setMaxDepth(3);
    setBestCol(4);
  }

  /**
   * Constructor for the ComputerPlayer that takes an argument for the maximum depth.
   *
   * @param maxDepth The value the user chooses for the computer player difficulty.
   */
  public ComputerPlayer(int maxDepth) {

    setMaxDepth(maxDepth);
    setBestCol(4);
  }

  /**
   * getMaxDepth returns the maximum depth that was set by the user at the start of the game.
   *
   * @return maxDepth The depth value chosen for the computer player difficulty.
   */
  private int getMaxDepth() {

    return maxDepth;
  }

  /**
   * getBestCol returns the column that the computer determined to be for its best possible move.
   *
   * @return bestCol The column the computer has chosen for its move.
   */
  private int getBestCol() {

    return bestCol;
  }

  /**
   * setMaxDepth sets the depth at which the negamax algorithm will begin at to look for the best move.
   *
   * <p>Each time the negamax algorithm is called, it reduces the depth by 1 until it reaches 0 (the terminal node).
   *
   * @param maxDepth The maximum amount of times the computer will recursively call the negamax algorithm.
   */
  public void setMaxDepth(int maxDepth) {

    if (maxDepth > 0) {

      this.maxDepth = maxDepth;
    } else {

      this.maxDepth = 1;
    }
  }

  /**
   * setBestCol sets the column that the computer wants to play.
   *
   * @param bestCol The column that the computer chose for its move.
   */
  private void setBestCol(int bestCol) {

    if (bestCol >= 0 && bestCol <= 6) {

      this.bestCol = bestCol;
    }
  }

  /**
   * aiMove analyzes the current game configuration and determines the best possible column for the computer to play.
   *
   * <p>aiMove mianly uses a combination of a quick search for the computer to win this move, a quick search
   * for the computer to block a user's next move that would have won, and a recursive negamax algorithm that seraches many
   * possible game configurations after different moves to find the best possible column for the computer to play right now.
   *
   * @param gameConfig The current configuration of the game and all it's associated properties.
   * @return bestCol The best column for the computer to play, sometimes this is determined by logic other
   * than the recursive negamax algorithm in order to speed up play.
   */
  public int aiMove(GameConfig gameConfig) {

    /* Counts the amount of times the user player has played.
    Used to help avoid a bad opening from the computer player. */
    int count = 0;
    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {
        if (gameConfig.getBoard()[r][c] == 1) {
          count ++;
        }
      }
    }

    /*By the nature of the evaluation board, sometimes at the beginning of the game after playing the middle column, if the user
    starts playing the bottom row to win, the computer values the second row from the bottom more than blocking a potential future winning play by
    the user. This alleviates the issue by playing a move or two ahead of the user. */
		if (count == 0 || count == 1) {
        return 3;
		} else {
			if (gameConfig.getBoard()[5][3] == 1 && gameConfig.getBoard()[5][4] == 1 && gameConfig.getBoard()[5][5] == 0) {
				return 5;
			} else if (gameConfig.getBoard()[5][3] == 1 && gameConfig.getBoard()[5][2] == 1 && gameConfig.getBoard()[5][1] == 0) {
				return 1;
			} else if (gameConfig.getBoard()[5][3] == 1 && gameConfig.getBoard()[5][5] == 1 && gameConfig.getBoard()[5][4] == 0) {
				return 4;
			} else if (gameConfig.getBoard()[5][3] == 1 && gameConfig.getBoard()[5][1] == 1 && gameConfig.getBoard()[5][2] == 0) {
				return 2;
			}
		}

    /* Skips the negamax algorithm and plays the computer's winning move.
    Helps speed up computation at high max depth settings. */
    for (int c = 0; c < 7; c ++) {
      GameConfig win = new GameConfig(gameConfig);
      if (!fullColumn(win.getBoard(),c)) {
				makeMove(win.getBoard(), c, 2);
        win.checkForWinner();
        if (win.getWinner() == 2) {
          return c;
        }
			}
    }

    /* Skips the negamax algorithm and blocks a user's winning move.
    Helps speed up computation at high max depth settings. */
    for (int c = 0; c < 7; c ++) {
      GameConfig block = new GameConfig(gameConfig);
      if (!fullColumn(block.getBoard(),c)) {
				makeMove(block.getBoard(), c, 1);
        block.checkForWinner();
        if (block.getWinner() == 1) {
          return c;
        }
			}
    }

    /* Initial call for the computer player's root node/curent configuration of the board seen by the user. */
    negamax(gameConfig, getMaxDepth(), 1);

    /* Fall back method if the computer is trying to play a full column.
    This can sometimes occur at the end of the game with a high max depth setting. */
    while ((fullColumn(gameConfig.getBoard(), getBestCol()))) {
      Random rand = new Random();
      int randCol = rand.nextInt(7);
      setBestCol(randCol);
    }

    return getBestCol();
  }

  /**
   * getChildren facilitates the negamax algorithms ability to search "children" of the
   * root node (the current game configuration).
   *
   * <p>getChildren takes in a game configuration and the current player as arguments
   * in order to generate an ArrayList of new game configurations with all possible moves
   * in each column for the specified player.
   *
   * @param gameConfig The root or current node of the game board to be further explored by the negamax algorithm.
   * @param player The current player that the moves will be made for to generate children.
   * @return children The ArrayList of all new game board configuration that were able to be made from the player moving.
   */
  private ArrayList<GameConfig> getChildren(GameConfig gameConfig, int player) {

    ArrayList<GameConfig> children = new ArrayList<GameConfig>();

    for(int c = 0; c < 7; c ++) {

			if (!fullColumn(gameConfig.getBoard(), c)) {

        GameConfig child = new GameConfig(gameConfig);
        makeMove(child.getBoard(), c, player);
        child.setColPlayed(c);
				children.add(child);
			}
		}

		return children;
	}

  /**
   * negamax is a recursive algorithm that searches for the best possible column choice for the computer player by
   * relying on the fact that the board value for the computer player is the negation of the value for the user.
   *
   * <p>negamax when called for a player attempts to find a move that maximizes the negation of the value resulting from the
   * particular move. Essentially, a game tree is created from a root node (the first call of negamax), and it works its way down from
   * there comparing each player's best move until a certain depth is reached. It then returns back up the tree, comparing different branches
   * to the best one its found so far, then setting the best column during this comparison. Eventually it gets back to the root node and
   * sets the best column for the computer to play in order to maximize its chances of winning. A more thorough explanation of the algorithm
   * can be found at this wikipedia page (https://en.wikipedia.org/wiki/Negamax).
   *
   * @param gameConfig The current configuration of the game (the root node).
   * @param depth The current depth the recursive algorithm is searching at. The intialized depth is the maxDepth.
   * @param color The current player the algorithm is searching for. Initalized for the computer player (color = 1).
   * @return bestScore The score used to compare children of a game configuration against one another.
   */
  private int negamax(GameConfig gameConfig, int depth, int color) {

    int player;
    int bestScore;

    /* Sets the initial best score depending on what player it is searching for.
    276 is the total value of the evaluationBoard. */
    if (color == 1) {

      player = 2;
      bestScore = 276;
    } else {

      player = 1;
      bestScore = -276;
    }

    gameConfig.checkForWinner();

    /* If the terminal node is reached, the algorithm returns the heuristic value of the node (ie. the evaluation).
    This is modified by the color (current player) for proper use in comparing values among the terminal node branches. */
    if (depth == 0) {

      return color * evaluate(gameConfig.getBoard());
    } else if (gameConfig.getWinner() != 0) {

      /* If the computer is the winner of this node, it should immediately be set as the best possible move.
      Thus why it returns the maximum value of an integer. */
      if (gameConfig.getWinner() == 2) {

        return Integer.MAX_VALUE;

      /* If the user is the winner of this node, it should immediately be set as the worst possible move.
      Thus why it returns the minimum value of an integer. */
      } else if (gameConfig.getWinner() == 1) {

        return Integer.MIN_VALUE;

      /* If the game ends up being a draw in this node, the value returned shoudl be 0 so it can be avoided if there is
      a better possible move elsewhere. */
      } else {

        return 0;
      }
    }

    ArrayList<GameConfig> children = new ArrayList<GameConfig>(getChildren(gameConfig, player));

    /* If the current player the algorithm is searching for is the computer,
    the next negamax call will be the negation of the value for the user. */
    if (player == 2) {

      for (GameConfig child : children) {

        int value = -negamax(child, depth-1, -color);

        if (value < bestScore) {

          setBestCol(child.getColPlayed());
          bestScore = value;
        }
      }
    /* If the current player the algorithm is searching for is the user,
    the next negamax call will be the for the computer. */
    } else {

      for (GameConfig child : children) {

        int value = negamax(child, depth-1, color);

        if (value > bestScore) {

          setBestCol(child.getColPlayed());
          bestScore = value;
        }
      }
    }

    return bestScore;
  }

  /**
   * evaluate uses the static evaluationBoard to determine a score for the current board state.
   *
   * <p>evaluate will return a score above 138 if the current configuration of the tokens favour the computer,
   * and will return a score below 138 if the current configuration favours the user. This is used in the negamax
   * algorithm to determine if a certain node/child of the game configuration is better than another, allowing the
   * computer to pick the column that led to the node/child.
   *
   * @param board The current game configuration board the negamax algorithm is analyzing.
   * @return score The value of the board for the called for, a combination of the midScore and calculated sum.
   */
  private int evaluate(int[][] board) {

    /* midscore is 138 since the total score of an empty board is 276. */
    int midScore = 138;
    int sum = 0;

    /* Loops through the entire evaluationBoard. */
    for (int r = 0; r < 6; r ++) {
      for (int c = 0; c < 7; c ++) {

        /* Compares computer positions in the board being analyzed to that of the evaluationBoard.
        Adds to the sum the evaluationBoard value if the computer occupies that space. */
        if (board[r][c] == 2) {

          sum += evaluationBoard[r][c];

        /* Compares user positions in the board being analyzed to that of the evaluationBoard.
        Subtracts from the sum the evaluationBoard value if the user occupies that space. */
        } else if (board[r][c] == 1) {

          sum -= evaluationBoard[r][c];
        }
      }
    }

    int score = midScore + sum;

    return score;
  }

}