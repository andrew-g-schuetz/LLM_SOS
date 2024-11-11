import javax.swing.*;

public class GeneralGame implements SOSGameMode{
    private Game game;

    public GeneralGame(Game game){
        this.game = game;
    }

    @Override
    public boolean makeMove(int row, int col, char currentLetter) {

        boolean validMove = game.getBoard().makeMove(row, col, currentLetter);
        if(!validMove){
            return false;
        }

        boolean sosFormed = game.getBoard().checkForSOS(row,col);
        if(sosFormed){
            game.getCurrentPlayer().incrementScore();
        }

        return true;
    }

    @Override
    public boolean checkForWinner(int row, int col) {
        if (game.getBoard().checkForSOS(row, col)) {
            game.getCurrentPlayer().incrementScore();
            return false;
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
        if (game.getPlayerOne().getScore() > game.getPlayerTwo().getScore()) {
            message = game.getPlayerOne().getName() + " Wins with score: " + game.getPlayerOne().getScore() +"\n"+
                    game.getPlayerTwo().getName() + " Loses with score: " + game.getPlayerTwo().getScore();
        } else if (game.getPlayerTwo().getScore() > game.getPlayerOne().getScore()) {
            message = game.getPlayerTwo().getName() + " Wins with score: " + game.getPlayerTwo().getScore() +"\n"+
                    game.getPlayerOne().getName() + " Loses with score: " + game.getPlayerOne().getScore();
        } else {
            message = "It's a draw!";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}