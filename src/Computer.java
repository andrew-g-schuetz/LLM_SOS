import java.util.Random;

public class Computer extends Player {

    public Computer(String name, char letter, String playerType){
        super(name, letter, 0,playerType);
    }

    public void makeMove(Game game){
        Board board = game.getBoard();
        Random rand = new Random();

        int[] emptyCells = new int[board.getSize() * board.getSize()];
        int emptyCount = 0;

        for(int row = 0; row < board.getSize(); row++){
            for(int col =0; col < board.getSize(); col++){
                if(board.getCell(row,col) == ' '){
                    emptyCells[emptyCount++] = row * board.getSize() + col;
                }
            }
        }

        if (emptyCount > 0) {
            // Pick a random empty spot
            int randomIndex = rand.nextInt(emptyCount);
            int cellIndex = emptyCells[randomIndex];
            int row = cellIndex / board.getSize();
            int col = cellIndex % board.getSize();

            // Make the move for the computer
            board.makeMove(row, col, getLetter());

            // Check for SOS formation and update score
            if (board.checkForSOS(row, col)) {
                this.incrementScore();
            }
        }

    }

    public void playTurn(Game game, SOSGameMode gameMode) {
        // Computer makes its move
        makeMove(game);

        // If the game is over, show results
        if (gameMode.isGameOver()) {
            gameMode.showResults();
        } else {
            // Switch turns to the next player
            game.switchTurns();
        }
    }


}
