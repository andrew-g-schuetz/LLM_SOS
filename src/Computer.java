import javax.swing.*;
import java.util.Random;

public class Computer extends Player {

    private Game game;
    private SOSGameMode gameMode;
    private JButton[][] buttons;
    private GameBoardGUI gameBoardGUI;

    public Computer(String name, char letter, int score, Game game, SOSGameMode gameMode, JButton[][] buttons, GameBoardGUI gameboardGUI){
        super(name, letter, score, "Computer");
        this.game = game;
        this.gameMode = gameMode;
        this.buttons = buttons;
        this.gameBoardGUI = gameboardGUI;
    }



    public void makeComputerMove() {
        // Declare variables as final or effectively final
        final int row, col;
        final char currentLetter = Math.random() < 0.5 ? 'S' : 'O';

        // Find an empty spot for the move
        int tempRow = -1;
        int tempCol = -1;
        while (true) {
            tempRow = (int) (Math.random() * game.getBoard().getSize());
            tempCol = (int) (Math.random() * game.getBoard().getSize());

            // Check if the spot is empty (not 'S' or 'O')
            if (game.getBoard().getCell(tempRow, tempCol) == '-') {
                break;
            }
        }

        row = tempRow;
        col = tempCol;

        // Make the computer's move logically (without UI update yet)
        if (gameMode.makeMove(row, col, currentLetter)) {
            // Use a Swing Timer to delay the UI update and subsequent checks
            Timer timer = new Timer(2000, e -> {
                // Update the button's text after the delay
                buttons[row][col].setText(String.valueOf(currentLetter));

                // Check if it's a Simple Game and if an "SOS" is detected
                if (game.getGameType().equals("Simple Game") && game.getBoard().checkForSOS(row, col)) {
                    gameMode.showResults();
                    //dispose();
                    return; // Exit if the game ends
                }

                // Switch turns only if no "SOS" is found in the current move
                if (!game.getBoard().checkForSOS(row, col)) {
                    game.switchTurns();
                }

                // Check if the game is over (board full or end condition met)
                if (gameMode.isGameOver()) {
                    gameMode.showResults();
                    //dispose();
                } else {
                    gameBoardGUI.updateTitle(); // Update the title to indicate the next player's turn
                    if (game.getCurrentPlayer().getPlayerType().equals("Computer")) {
                        makeComputerMove(); // Automatically make the next computer move
                    }
                }
            });

            timer.setRepeats(false); // Ensure the timer only runs once
            timer.start(); // Start the timer
        }
    }




}
