CONNECT 4
-----------------------
CPSC 233 Team Project
-----------------------
Created by T02 - Team 1
Matthew Cox, Tony Wong, Minnie Thai, Nathaniel Habtegergesa, and Natinael Ayalew
-----------------------

Two JUnit test cases are provided to thoroughly test the logical components of the game (GameConfig and
ComputerPlayer). These JUnit tests account for average, boundary, and outlier cases where possible to make sure each
method is working properly. Additionally, you may want to test input handling in both the text-based and
gui-based versions. There are clear indications what input is valid, however upon entering an invalid entry
both versions will tell the user why it was invalid and to retry their entry.

-----------------------

To run GameConfigTest JUnit test in command/console window (src directory)
enter for Windows "java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore connect4test.GameConfigTest"
enter for Mac/Linux "java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore connect4test.GameConfigTest"

To run ComputerPlayerTest JUnit test in command/console window (src directory)
enter for Windows "java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore connect4test.ComputerPlayerTest"
enter for Mac/Linux "java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore connect4test.ComputerPlayerTest"

-----------------------
