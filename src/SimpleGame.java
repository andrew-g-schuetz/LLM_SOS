import javax.swing.*;

public class SimpleGame implements SOSGameMode {
    private Game game;
    private boolean gameEnded = false;
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
            //showResults();

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
        if(!isBoardFull()){
            JOptionPane.showMessageDialog(null, game.getCurrentPlayer().getName() + " wins!");
        }

    }

    private boolean isBoardFull() {
        return game.getBoard().isBoardFull();
    }
}