package connect4text;

import java.util.Scanner;

/**
 * HumanPlayer contains methods to facilitate interaction from the user, prompting them for a column choice for their token.
 *
 * @author T02-1 - Minnie Thai
 * @version 1.1
 */
public class HumanPlayer {

  /**
   * input uses Scanner to get a column choice from the user.
   */
  private Scanner input = new Scanner(System.in);

  /**
   * inputColumn stores the column choice from the user.
   */
  private int inputColumn;

  /**
   * column is corrected value from the user to choose the right array index in the game board.
   */
  private int column;

  /**
   * getColumn reads the user's input and uses isNumber and isValid methods to make sure the choice is
   * within the game board before returning the column selected.
   *
   * @return column The corrected column choice from the user.
   */
  public int getColumn() {

    isNumber();

    if (isValid(inputColumn) == true) {

      column = inputColumn - 1; //Changes user's column choice to the correct column index
      return column;
    } else {

      getColumn();
    }
    return column; //Should not be possible.
  }

  /**
   * isNumber validates the user's input to make sure it is a number that they entered.
   */
  private void isNumber() {

		System.out.print("\nEnter a column (1-7) to place your token: ");

    /* try-catch implemetation to handle user input that is not a number. */
		try {

			inputColumn = input.nextInt();
		} catch (Exception e) {

			System.out.println("\nOops! That was not a number!");
			input.next();
			getColumn();
		}
	}

  /**
   * isValid makes sure the user chose a column that is actually on the board.
   *
   * @param someColumn The column that the user chose as their move.
   */
  private boolean isValid(int someColumn) {

		if (someColumn >= 1 && someColumn <= 7) {

			return true;

		} else {

			System.out.println("\nOops, that choice was not even on the board!");
			return false;
		}
	}

}