package connect4test;

import connect4.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * GameConfigTest is a JUnit test class that thoroughly tests the public methods that contain logic in GameConfig.
 *
 * <p>Methods are tested with average, boundary, and outlier cases where possible to make sure each method
 * is working properly. The methods tested are; fullColumn, makeMove, and checkForWinner/getWinner.
 *
 * @author T02-1 - Matthew Cox
 * @version 1.1
 */
public class GameConfigTest {

  /**
   * Empty board test for fullColumn. The expected result is that the index 3 column is not full, so false.
   */
  @Test
  public void test_GameConfig_fullColumn_EmptyColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Column index 3 is completely free - testing for fullColumn to return false.", false, configTest.fullColumn(configTest.getBoard(), 3));

  }

  /**
   * Test for fullColumn that has some moves made. The expected result is that the index 3 column is not full, so false.
   */
  @Test
  public void test_GameConfig_fullColumn_FreeColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Column index 3 has a free space in row 5 - testing for fullColumn to return false.", false, configTest.fullColumn(configTest.getBoard(), 3));

  }

  /**
   * Test for fullColumn that has moves made and a full column. The expected result is that the index 3 column is full, so true.
   */
  @Test
  public void test_GameConfig_fullColumn_FullColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);

    assertEquals("Column index 3 is full - testing for fullColumn to return true.", true, configTest.fullColumn(configTest.getBoard(), 3));

  }

  /**
   * Test for makeMove that has moves made in other columns but not the one being played. The expected result is that since the index 3 column is
   * empty, the colPlayed and rowPlayed variables will be 3 and 5 respectively.
   */
  @Test
  public void test_GameConfig_makeMove_EmptyColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,2,0,2,0,0},
                         {0,0,1,0,1,0,0},
                         {0,0,2,0,2,0,0},
                         {0,0,1,0,1,0,0}};

    configTest.setBoard(testBoard);
    configTest.makeMove(configTest.getBoard(), 3, 1);

    assertEquals("Column index 3 is has a free space in row 5 - testing for getColPlayed to be 3.", 3, configTest.getColPlayed());
    assertEquals("Column index 3 is has a free space in row 5 - testing for getRowPlayed to be 0.", 5, configTest.getRowPlayed());

  }

  /**
   * Test for makeMove that has moves made in the column currently being played. The expected result is that since the index 3 column has a free
   * space in row index 0, the colPlayed and rowPlayed variables will be 3 and 0 respectively.
   */
  @Test
  public void test_GameConfig_makeMove_FreeColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.makeMove(configTest.getBoard(), 3, 2);

    assertEquals("Column index 3 is has a free space in row 0 - testing for getColPlayed to be 3.", 3, configTest.getColPlayed());
    assertEquals("Column index 3 is has a free space in row 0 - testing for getRowPlayed to be 0.", 0, configTest.getRowPlayed());

  }

  /**
   * Test for makeMove that has moves made in the column currently being played. The expected result is that since the index 3 column is full,
   * the colPlayed and rowPlayed variables will both be 0 because the default GameConfig constructor sets them to 0 when initialized. Getting these
   * results means that makeMove used fullColumn to see that the column is full and did not update the variables since no move was made.
   */
  @Test
  public void test_GameConfig_makeMove_FullColumn() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,1,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.makeMove(configTest.getBoard(), 3, 1);

    assertEquals("Column index 3 is full - testing for getColPlayed to return 0 because the default constructor sets it to 0.", 0, configTest.getColPlayed());
    assertEquals("Column index 3 is full - testing for getRowPlayed to return 0 because the default constructor sets it to 0.", 0, configTest.getRowPlayed());

  }

  /**
   * Test for getWinner that has a completely full board with no winner. The expected result once a checkForWinner method is called
   * on the GameConfig is -1, the designed indicator of a draw.
   */
  @Test
  public void test_GameConfig_getWinner_Draw() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{2,1,1,2,2,1,2},
                         {1,2,2,1,1,2,1},
                         {2,1,1,2,2,1,2},
                         {1,2,2,1,1,2,1},
                         {2,1,1,2,2,1,2},
                         {1,2,2,1,1,2,1}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board is full with no winner - testing for getWinner to return -1 (draw game).", -1, configTest.getWinner());

  }

  /**
   * Test for getWinner that has an almost full board with one free space open and no winning connection. The expected result once a checkForWinner
   * method is called on the GameConfig is 0, the designed indicator of an ongoing game.
   */
  @Test
  public void test_GameConfig_getWinner_OngoingGame() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{2,1,1,2,2,1,0},
                         {1,2,2,1,1,2,1},
                         {2,1,1,2,2,1,2},
                         {1,2,2,1,1,2,1},
                         {2,1,1,2,2,1,2},
                         {1,2,2,1,1,2,1}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has one free space with no winner - testing for getWinner to return 0 (ongoing game).", 0, configTest.getWinner());

  }

  /**
   * Test for getWinner that has a completely empty board. The expected result once a checkForWinner
   * method is called on the GameConfig is 0, the designed indicator of an ongoing game.
   */
  @Test
  public void test_GameConfig_getWinner_EmptyBoard() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board is completely empty - testing for getWinner to return 0 (ongoing game).", 0, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the first player winning in a row. The expected result once a checkForWinner
   * method is called on the GameConfig is 1, the designed indicator of the first player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P1WinInRow() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,1,1,1,1,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player one winning in a row - testing for getWinner to return 1.", 1, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the second player winning in a row. The expected result once a checkForWinner
   * method is called on the GameConfig is 2, the designed indicator of the second player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P2WinInRow() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0},
                         {0,0,2,2,2,2,0},
                         {0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player two winning in a row - testing for getWinner to return 2.", 2, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the first player winning in a column. The expected result once a checkForWinner
   * method is called on the GameConfig is 1, the designed indicator of the first player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P1WinInCol() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player one winning in a column - testing for getWinner to return 1.", 1, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the second player winning in a column. The expected result once a checkForWinner
   * method is called on the GameConfig is 2, the designed indicator of the second player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P2WinInCol() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player two winning in a column - testing for getWinner to return 2.", 2, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the first player winning in an ascending diagonal. The expected result once a checkForWinner
   * method is called on the GameConfig is 1, the designed indicator of the first player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P1WinInUpDiag() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,1,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,1,0,0,0,0},
                         {0,1,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player one winning in an ascending diagonal - testing for getWinner to return 1.", 1, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the second player winning in an ascending diagonal. The expected result once a checkForWinner
   * method is called on the GameConfig is 2, the designed indicator of the second player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P2WinInUpDiag() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,0,0,2,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,2,0,0,0,0},
                         {0,2,0,0,0,0,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player two winning in an ascending diagonal - testing for getWinner to return 2.", 2, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the first player winning in a descending diagonal. The expected result once a checkForWinner
   * method is called on the GameConfig is 1, the designed indicator of the first player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P1WinInDownDiag() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,1,0,0,0,0},
                         {0,0,0,1,0,0,0},
                         {0,0,0,0,1,0,0},
                         {0,0,0,0,0,1,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player one winning in a descending diagonal - testing for getWinner to return 1.", 1, configTest.getWinner());

  }

  /**
   * Test for getWinner that has the second player winning in a descending diagonal. The expected result once a checkForWinner
   * method is called on the GameConfig is 2, the designed indicator of the second player having won the game.
   */
  @Test
  public void test_GameConfig_getWinner_P2WinInDownDiag() {

    GameConfig configTest = new GameConfig();

    int[][] testBoard = {{0,0,0,0,0,0,0},
                         {0,0,2,0,0,0,0},
                         {0,0,0,2,0,0,0},
                         {0,0,0,0,2,0,0},
                         {0,0,0,0,0,2,0},
                         {0,0,0,0,0,0,0}};

    configTest.setBoard(testBoard);
    configTest.checkForWinner();

    assertEquals("Connect4 board has player two winning in a descending diagonal - testing for getWinner to return 2.", 2, configTest.getWinner());

  }

}