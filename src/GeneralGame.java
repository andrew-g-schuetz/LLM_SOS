import javax.swing.*;

public class GeneralGame implements SOSGameMode{
    private Game game;

    public GeneralGame(Game game){
        this.game = game;
    }

    @Override
    public boolean makeMove(int row, int col, char currentLetter) {
        return game.getBoard().makeMove(row, col, currentLetter);
    }

    @Override
    public boolean checkForWinner(int row, int col) {
        if (game.getBoard().checkForSOS(row, col)) {
            game.getCurrentPlayer().incrementScore();
        }
        return false;
    }

    @Override
    public boolean isGameOver() {
        return game.getBoard().isBoardFull();
    }

    @Override
    public void showResults() {
        String message;
        if (game.getCurrentPlayer().getScore() > game.getSecondPlayer().getScore()) {
            message = game.getCurrentPlayer().getName() + " Wins with score: " + game.getCurrentPlayer().getScore();
        } else if (game.getSecondPlayer().getScore() > game.getCurrentPlayer().getScore()) {
            message = game.getSecondPlayer().getName() + " Wins with score: " + game.getSecondPlayer().getScore();
        } else {
            message = "It's a draw!";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}
