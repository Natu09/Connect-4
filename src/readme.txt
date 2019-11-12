CONNECT 4
-----------------------
CPSC 233 Team Project
-----------------------
Created by T02 - Team 1
Matthew Cox, Tony Wong, Minnie Thai, Nathaniel Habtegergesa, and Natinael Ayalew
-----------------------

Our project is the turn-based board game Connect Four. It supports two human players that play against one another
as well as the ability for a single human to play against the computer. Both versions of the game (text-based and
graphical user interface) have a menu for choosing these two game modes. There are also options in both to choose
the difficulty of the computer player. In the text-based version, the console prompts the user for column input
and displays and updated board once the user makes a valid column selection for their token. In the gui version,
the user clicks in the column they want to place their token. The gui highlights whose turn it is by changing a
semi-transparent rectangle over the current moused column to the current players colour. Once the game ends in
either version, a clear prompt is displayed saying who won or if the game ended in a draw.

-----------------------

The files have already been compiled in this submission for convience. However, if there is a need to recompile
in the future, when the java files are moved into the src directory enter the command "javac -d . *.java" this
will recompile them and overwrite the associated class files in their respective packages. JUnit test files
can be recompiled using "javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java" (swap ; for : on Linux/Mac)
when the test files are moved into the src directory.

To run GUI version of game when in command/console window (src directory) enter "java connect4gui.GUIGame"

To run text version of game when in command/console window (src directory) enter "java connect4text.Game"

-----------------------

