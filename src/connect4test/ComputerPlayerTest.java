package connect4test;

import connect4.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ComputerPlayerTest is a JUnit test class that thoroughly tests the ComputerPlayer class by setting different
 * game configurations up and makes sure the intended result is returned.
 *
 * <p>The only method that is public in ComputerPlayer that contains logic to test on is aiMove. In this JUnit
 * test suite, the opening moves that are hardcoded for the computer player are tested, various winning and
 * blocking scenarios are tested, and moves where the computer evaluates the board and uses the negamax
 * algorithm are tested as well. This handy website, http://connect4.gamesolver.org/, is used to easily see
 * if the negamax algorithm returns the best possible move.
 *
 * @author T02-1 - Matthew Cox
 * @version 1.1
 */
public class ComputerPlayerTest {

  /**
   * Empty board test, the computer is hardcoded to play in the middle column, the best possible move to
   * open a game of Connect4 with.
   */
  @Test
  public void test_ComputerPlayer_aiMove_EmptyBoard() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board is empty - testing for aiMove to return column 3.", 3, ai.aiMove(configTest));

  }

  /**
   * The human has played in the middle column, the computer is hardcoded to also play in the middle column,
   * the best possible move to make when playing second in a game of Connect4.
   */
  @Test
  public void test_ComputerPlayer_aiMove_P1MadeMoveInMiddleCol() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 1 human player token in middle column - testing for aiMove to return column 3.", 3, ai.aiMove(configTest));

  }

  /**
   * The human has played in the bottom row in a way that could trip up the negamax algorithm, the computer
   * player is hardcoded to block potential winning plays that arise from this situation. The computer makes the
   * best possible move by playing in column 4.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockBottomRow1() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,1,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 2 human player tokens in board - testing for aiMove to return column 4.", 4, ai.aiMove(configTest));

  }

  /**
   * The human has played in the bottom row in a way that could trip up the negamax algorithm, the computer
   * player is hardcoded to block potential winning plays that arise from this situation. The computer makes the
   * best possible move by playing in column 3.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockBottomRow2() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,1,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 2 human player tokens in board - testing for aiMove to return column 2.", 2, ai.aiMove(configTest));

  }

  /**
   * The human has played in the bottom row in a way that could trip up the negamax algorithm, the computer
   * player is hardcoded to block potential winning plays that arise from this situation. The computer makes the
   * best possible move by playing in column 1.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockBottomRow3() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,1,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 2 human player tokens in board - testing for aiMove to return column 1.", 1, ai.aiMove(configTest));

  }

  /**
   * The human has played in the bottom row in a way that could trip up the negamax algorithm, the computer
   * player is hardcoded to block potential winning plays that arise from this situation. The computer makes the
   * best possible move by playing in column 5.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockBottomRow4() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,1,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 2 human player tokens in board - testing for aiMove to return column 5.", 5, ai.aiMove(configTest));

  }

  /**
   * The computer player can win the game by playing in the column of index 1. This test sees if the computer
   * will make the winning move.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToWinRow() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,1,1,0,0},
                         {0,0,2,2,2,1,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 computer tokens in a row - testing for aiMove to return column 1.", 1, ai.aiMove(configTest));

  }

  /**
   * The computer player can win the game by playing in the column of index 3. This test sees if the computer
   * will make the winning move.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToWinCol() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,1,2,0,0,0},
                         {0,1,1,2,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 computer tokens in a column - testing for aiMove to return column 3.", 3, ai.aiMove(configTest));

  }

  /**
   * The computer player can win the game by playing in the column of index 4. This test sees if the computer
   * will make the winning move.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToWinUpDiag() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,1,0,0},
                         {0,0,2,1,1,0,0},
                         {0,2,1,1,2,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 computer tokens in a diagonal - testing for aiMove to return column 4.", 4, ai.aiMove(configTest));

  }

  /**
   * The computer player can win the game by playing in the column of index 1. This test sees if the computer
   * will make the winning move.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToWinDownDiag() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,2,0,0},
                         {0,0,0,2,1,0,0},
                         {0,0,2,1,1,0,0},
                         {0,0,1,1,2,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 computer tokens in a diagonal - testing for aiMove to return column 1.", 1, ai.aiMove(configTest));

  }

  /**
   * The human player can win the game by playing in the column of index 1. This test sees if the computer
   * will block the human player's winning move before their next turn.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockRow() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,1,1,1,2,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 human player tokens in a row - testing for aiMove to return column 1.", 1, ai.aiMove(configTest));

  }

  /**
   * The human player can win the game by playing in the column of index 3. This test sees if the computer
   * will block the human player's winning move before their next turn.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockCol() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,2,1,0,0,0},
                         {0,0,2,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 human player tokens in a column - testing for aiMove to return column 3.", 3, ai.aiMove(configTest));

  }

  /**
   * The human player can win the game by playing in the column of index 4. This test sees if the computer
   * will block the human player's winning move before their next turn.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockUpDiag() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,1,1,0,0},
                         {0,0,1,2,2,0,0},
                         {0,1,2,2,1,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 human player tokens in a diagonal - testing for aiMove to return column 4.", 4, ai.aiMove(configTest));

  }

  /**
   * The human player can win the game by playing in the column of index 1. This test sees if the computer
   * will block the human player's winning move before their next turn.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AINeedsToBlockDownDiag() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,1,0,0},
                         {0,0,0,1,1,0,0},
                         {0,0,1,2,2,0,0},
                         {0,0,2,2,1,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 human player tokens in a diagonal - testing for aiMove to return column 1.", 1, ai.aiMove(configTest));

  }

  /**
   * Both the human player and computer have winning moves available in the board, however it is the computer's turn.
   * This method tests if the computer will prioritize winning over blocking by playing in column index 1 instead of 5.
   */
  @Test
  public void test_ComputerPlayer_aiMove_AIWinsOverBlocking() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,1,0,0,0,0,0},
                         {0,1,0,0,0,0,0},
                         {0,1,2,2,2,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has 3 human player tokens in a col and 3 computer tokens in a row - testing for aiMove to return column 5.", 5, ai.aiMove(configTest));

  }

  /**
   * When the board is configured in this fashion, the negamax algorithm will choose to play again in the middle column
   * if the maxDepth is set to 3. This is because the evaluated score from the middle column is the greater than other
   * potential moves as it only searches three configurations into the future. So the expected result is column index 3.
   */
  @Test
  public void test_ComputerPlayer_aiMove_MaxDepth3Move() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer(3);

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has tokens in it, ai max depth set to 3 (default) - testing for aiMove to return column 3.", 3, ai.aiMove(configTest));

  }

  /**
   * When the board is configured in this fashion (exactly the same as the previous test), the negamax algorithm will choose
   * to play again in the second from right column if the maxDepth is set to 7. This is because the evaluated score from there
   * is the greater than other potential moves as it searches seven configurations into the future. The expected result is column index 5.
   */
  @Test
  public void test_ComputerPlayer_aiMove_MaxDepth7Move() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer(7);

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Board has tokens in it, ai max depth set to 7 - testing for aiMove to return column 5.", 5, ai.aiMove(configTest));

  }

  /**
   * Here the board is filled with a number of moves by both the human player and computer player. With the maxDepth
   * set to seven, the best column to play is column index 5 (as shown using the solver http://connect4.gamesolver.org/?pos=44444432655552322133367.
   * This tests to make sure that at the maximum depth setting, the computer will play the right move and keep it's hopes at a comeback alive.
   */
  @Test
  public void test_ComputerPlayer_aiMove_MaxDepth7MoveFullerBoard() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer(7);

    int[][] testBoard = {{0,0,0,2,0,0,0},
                         {0,0,1,1,0,0,0},
                         {0,1,2,2,1,0,0},
                         {0,2,1,1,2,0,0},
                         {0,2,1,2,1,2,0},
                         {2,2,1,1,2,1,1}};

    configTest.setBoard(testBoard);

    assertEquals("Board has tokens in it, ai max depth set to 7, best possible move is in column 5 - testing for aiMove to return column 5.", 5, ai.aiMove(configTest));

  }

  /**
   * Here the board is even fuller than the previous example. With the maxDepth set to seven, the best column to play is column index 5 again
   * (as shown using the solver http://connect4.gamesolver.org/?pos=444444326555523221333676711277776. This tests to make sure that at the maximum depth setting,
   * the computer will play the right move to play towards a draw game.
   */
  @Test
  public void test_ComputerPlayer_aiMove_MaxDepth7MoveEvenFullerBoard() {

    GameConfig configTest = new GameConfig();
    ComputerPlayer ai = new ComputerPlayer(7);

    int[][] testBoard = {{0,0,0,2,0,0,2},
                         {0,2,1,1,0,0,1},
                         {0,1,2,2,1,1,2},
                         {1,2,1,1,2,2,1},
                         {2,2,1,2,1,2,1},
                         {2,2,1,1,2,1,1}};

    configTest.setBoard(testBoard);

    assertEquals("Board has tokens in it, ai max depth set to 7, best possible move is in column 5 - testing for aiMove to return column 5.", 5, ai.aiMove(configTest));

  }


}