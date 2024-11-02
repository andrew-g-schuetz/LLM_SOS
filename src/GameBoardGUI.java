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
                                if (game.getBoard().checkForSOS(row, column) && game.getGameType().equals("Simple Game")) {
                                    JOptionPane.showMessageDialog(null, game.getCurrentPlayer().getName() + " wins!");
                                    dispose();


                                }
                                if(game.getGameType().equals("General Game")){
                                    if(game.getBoard().checkForSOS(row,column)){
                                        game.getCurrentPlayer().incrementScore();
                                        if(isBoardFull()){
                                            showResults();
                                            dispose();
                                        }


                                    }else{
                                        if(isBoardFull()){
                                            showResults();
                                            dispose();
                                        }
                                    }



                                }
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

    private void updateTitle() {
        setTitle("SOS Game - " + game.getCurrentPlayer().getName() + "'s Turn");
    }
}

