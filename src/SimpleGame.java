import javax.swing.*;

public class SimpleGame implements SOSGameMode {
    private Game game;

    public SimpleGame(Game game){
        this.game = game;
    }

    @Override
    public boolean makeMove(int row, int col, char currentLetter){
        return game.getBoard().makeMove(row,col,currentLetter);
    }

    @Override
    public boolean checkForWinner(int row, int col){
        if (game.getBoard().checkForSOS(row, col)) {
            JOptionPane.showMessageDialog(null, game.getCurrentPlayer().getName() + " wins!");
            return true;
        }
        return false;
    }

    @Override
    public boolean isGameOver() {
        if (isBoardFull()) {
            JOptionPane.showMessageDialog(null, "Match is a draw! Good Game.");
            return true;
        }
        return false;
    }

    @Override
    public void showResults() {
        // No need for additional results display in Simple Game
    }

    private boolean isBoardFull() {
        return game.getBoard().isBoardFull();
    }
}


