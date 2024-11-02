import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardGUI extends JFrame {

    private Game game;
    private JButton[][] buttons;

    public GameBoardGUI(Game game) {
        this.game = game;
        setTitle("SOS Game - " + game.getCurrentPlayer().getName() + "'s Turn");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(game.getBoard().getSize(), game.getBoard().getSize()));
        buttons = new JButton[game.getBoard().getSize()][game.getBoard().getSize()];

        initializeButtons();

        setVisible(true);
    }

    private void initializeButtons() {
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                buttons[i][j] = new JButton("-");
                int row = i;
                int column = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        char currentLetter = game.getCurrentPlayer().getLetter();
                        if (game.getBoard().makeMove(row, column, currentLetter)) {
                            buttons[row][column].setText(String.valueOf(currentLetter));
                                //Logic for the Simple game and will call on methods in the Board class
                                if (game.getBoard().checkForSOS(row, column) && game.getGameType().equals("Simple Game")) {
                                    JOptionPane.showMessageDialog(null, game.getCurrentPlayer().getName() + " wins!");
                                    dispose();


                                }
                                //Logic for the General Game and will call on methods in the Board class and Player properties
                                if(game.getGameType().equals("General Game")){
                                    if(game.getBoard().checkForSOS(row,column)){
                                        game.getCurrentPlayer().incrementScore();
                                        if(isBoardFull()){
                                            showResults();
                                            dispose();
                                        }

                                    //What will happen in case of the last move not resulting in a draw
                                    }else{
                                        if(isBoardFull()){
                                            showResults();
                                            dispose();
                                        }
                                    }



                                }//Switch the players
                                game.switchTurns();
                                updateTitle();


                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid move. Try again.");
                        }
                    }
                });
                add(buttons[i][j]);
            }
        }
    }

    /*
    * Checks if the board is full
    * returns false is board isn't full and true if it is
    * */
    private boolean isBoardFull(){
        for(int i = 0; i < game.getBoard().getSize();i++){
            for(int j = 0; j < game.getBoard().getSize(); j++){
                if(buttons[i][j].getText().equals("-")){
                    return false;
                }
            }
        }
        return true;
    }

    /*
    * Shows the results of the game for a General Game and display message with scores
    * */
    private void showResults(){
        String message;
        if(game.getCurrentPlayer().getScore() > game.getSecondPlayer().getScore()){
            message = game.getCurrentPlayer().getName() + " Wins\n" +
                        "Score: " + game.getCurrentPlayer().getScore() + "\n" +
                    game.getSecondPlayer().getName() + " Loses\n" +
                    "Score: " + game.getSecondPlayer().getScore() + "\n";

        }else if(game.getSecondPlayer().getScore() > game.getCurrentPlayer().getScore()){
            message = game.getSecondPlayer().getName() + " Wins\n" +
                    "Score: " + game.getSecondPlayer().getScore() + "\n" +
                    game.getCurrentPlayer().getName() + " Loses\n" +
                    "Score: " + game.getCurrentPlayer().getScore() + "\n";
        }else{
            message = "Match is a draw\n" +
                    game.getSecondPlayer().getName() + "\n" +
                    "Score: " + game.getSecondPlayer().getScore() + "\n" +
                    game.getCurrentPlayer().getName() + "\n" +
                    "Score: " + game.getCurrentPlayer().getScore() + "\n";
        }
        JOptionPane.showMessageDialog(null, message);
    }

    /*
    * Updates the Title at the top of the game board
    * */
    private void updateTitle() {
        setTitle("SOS Game - " + game.getCurrentPlayer().getName() + "'s Turn");
    }
}

