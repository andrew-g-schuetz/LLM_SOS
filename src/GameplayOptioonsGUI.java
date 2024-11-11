import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameplayOptioonsGUI extends JFrame {
    private JComboBox<String> gameTypeComboBox;
    private JComboBox<Integer> boardSizeComboBox;
    private JComboBox<String> playerOneOption;
    private JComboBox<String> playerTwoOption;
    private JComboBox<String> letterComboBox;
    private JButton startGameButton;

    public GameplayOptioonsGUI(){
        setTitle("SOS Game Setup");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2));

        JLabel gameTypeLabel = new JLabel("Select Game Type:");
        gameTypeComboBox = new JComboBox<>(new String[]{"Simple Game", "General Game"});

        JLabel boardSizeLabel = new JLabel("Select Board Size:");
        boardSizeComboBox = new JComboBox<>(new Integer[]{3,4,5,6,7,8});

        JLabel playerOneUser = new JLabel("Player One Type:");
        playerOneOption = new JComboBox<>(new String[]{"Human", "Computer"});

        JLabel playerTwoUser = new JLabel("Player Two Type:");
        playerTwoOption = new JComboBox<>(new String[]{"Human", "Computer"});

//        JLabel letterLabel = new JLabel("Choose Your Letter Piece:");
        letterComboBox = new JComboBox<>(new String[]{"S","O"});
        JLabel space = new JLabel();
        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(10,20));
        startGameButton.addActionListener(new StartGameAction());

        add(gameTypeLabel);
        add(gameTypeComboBox);
        add(boardSizeLabel);
        add(boardSizeComboBox);
        add(playerOneUser);
        add(playerOneOption);
        add(playerTwoUser);
        add(playerTwoOption);
        //add(letterLabel);
        //add(letterComboBox);
        add(space);
        add(startGameButton);

        setVisible(true);
    }

    private class StartGameAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String gameType = (String) gameTypeComboBox.getSelectedItem();
            int boardSize = (int) boardSizeComboBox.getSelectedItem();
            char letter = letterComboBox.getSelectedItem().toString().charAt(0);
            String playerOneType = (String)playerOneOption.getSelectedItem();
            String playerTwoType = (String)playerTwoOption.getSelectedItem();

            Player playerOne = new Player(JOptionPane.showInputDialog("Enter name for Player 1:"), letter,0, playerOneType);
            Player playerTwo = new Player(JOptionPane.showInputDialog("Enter name for Player 2:"), letter == 'S' ? 'O': 'S',0, playerTwoType);
            //System.out.println(gameType);
            Game game = new Game(playerOne, playerTwo, boardSize, gameType);

            dispose();

            new GameBoardGUI(game);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(GameplayOptioonsGUI::new);
    }
}

