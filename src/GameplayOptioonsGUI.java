import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameplayOptioonsGUI extends JFrame {
    private JComboBox<String> gameTypeComboBox;
    private JComboBox<Integer> boardSizeComboBox;
    private JComboBox<String> letterComboBox;
    private JButton startGameButton;

    public GameplayOptioonsGUI(){
        setTitle("SOS Game Setup");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2));

        JLabel gameTypeLabel = new JLabel("Select Game Type:");
        gameTypeComboBox = new JComboBox<>(new String[]{"Simple Game", "General Game"});

        JLabel boardSizeLabel = new JLabel("Select Board Size:");
        boardSizeComboBox = new JComboBox<>(new Integer[]{3,4,5,6,7,8});

        JLabel letterLabel = new JLabel("Choose Your Letter Piece:");
        letterComboBox = new JComboBox<>(new String[]{"S","O"});

        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(10,20));
        startGameButton.addActionListener(new StartGameAction());

        add(gameTypeLabel);
        add(gameTypeComboBox);
        add(boardSizeLabel);
        add(boardSizeComboBox);
        add(letterLabel);
        add(letterComboBox);
        add(startGameButton);

        setVisible(true);
    }

    private class StartGameAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String gameType = (String) gameTypeComboBox.getSelectedItem();
            int boardSize = (int) boardSizeComboBox.getSelectedItem();
            char letter = letterComboBox.getSelectedItem().toString().charAt(0);

            Player playerOne = new Player(JOptionPane.showInputDialog("Enter name for Player 1:"), letter,0);
            Player playerTwo = new Player(JOptionPane.showInputDialog("Enter name for Player 2:"), letter == 'S' ? 'O': 'S',0);
            System.out.println(gameType);
            Game game = new Game(playerOne, playerTwo, boardSize, gameType);

            dispose();

            new GameBoardGUI(game);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(GameplayOptioonsGUI::new);
    }
}

