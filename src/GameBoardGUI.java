import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameBoardGUI extends JFrame {

    private Game game;
    private JButton[][] buttons;
    private SOSGameMode gameMode;
    private JRadioButton player1S, player1O, player2S, player2O;
    private ButtonGroup player1Group, player2Group;
    private Computer computerPlayer;
    private JButton replayButton;
    private JButton newGameButton;
    private int boardSize;


    public GameBoardGUI(Game game, boolean isRecording) {
        this.game = game;

        // Set game mode (Simple or General)
        if (game.getGameType().equals("Simple Game")) {
            this.gameMode = new SimpleGame(game);
        } else if (game.getGameType().equals("General Game")) {
            this.gameMode = new GeneralGame(game);
        }
        setTitle("SOS Game - " + game.getCurrentPlayer().getName() + "'s Turn");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the game board
        JPanel boardPanel = new JPanel(new GridLayout(game.getBoard().getSize(), game.getBoard().getSize()));
        buttons = new JButton[game.getBoard().getSize()][game.getBoard().getSize()];
        initializeButtons(boardPanel);

        // Panel for the player selection
        JPanel playerSelectionPanel = new JPanel(new GridLayout(2, 2));
        player1S = new JRadioButton("S");
        player1O = new JRadioButton("O");
        player2S = new JRadioButton("S");
        player2O = new JRadioButton("O");

        player1Group = new ButtonGroup();
        player2Group = new ButtonGroup();

        // Group the radio buttons for each player
        player1Group.add(player1S);
        player1Group.add(player1O);
        player2Group.add(player2S);
        player2Group.add(player2O);

        // Default selection for each player
        player1S.setSelected(true);
        player2S.setSelected(true);

        //Disables radio buttons for computer players
        if(game.getPlayerOne().getPlayerType().equals("Computer")){
            player1S.setEnabled(false);
            player1O.setEnabled(false);
        }
        if(game.getPlayerTwo().getPlayerType().equals("Computer")){
            player2S.setEnabled(false);
            player2O.setEnabled(false);
        }

        //Add player selection details to the panel
        playerSelectionPanel.add(new JLabel(game.getPlayerOne().getPlayerType() + " Player 1: " + game.getPlayerOne().getName()));
        playerSelectionPanel.add(player1S);
        playerSelectionPanel.add(player1O);
        playerSelectionPanel.add(new JLabel(game.getPlayerTwo().getPlayerType() + " Player 2: " + game.getPlayerTwo().getName()));
        playerSelectionPanel.add(player2S);
        playerSelectionPanel.add(player2O);

        // Add the panels to the frame
        add(boardPanel, BorderLayout.CENTER);
        add(playerSelectionPanel, BorderLayout.SOUTH);

        //Replay Game Button
        replayButton = new JButton("Replay Game");
        replayButton.setEnabled(isRecording);
        replayButton.addActionListener(e -> replayGame());

        //New Game Button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            dispose();

            new GameplayOptioonsGUI();
        });

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(newGameButton);
        topPanel.add(replayButton);

        add(topPanel,BorderLayout.NORTH);

        //Get the boarderSize for replay
        boardSize = game.getBoard().getSize();
        setVisible(true);

        //initialize the computer player
        computerPlayer = new Computer(game.getCurrentPlayer().getName(),game.getCurrentPlayer().getLetter(),0,game, gameMode, buttons, this);
        // If it's the computer's turn, make the computer's first move automatically
        if (game.getCurrentPlayer().getPlayerType().equals("Computer")) {
            computerPlayer.makeComputerMove();
        }
    }

    // Initialize the game board buttons
    private void initializeButtons(JPanel boardPanel) {
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                buttons[i][j] = new JButton("-");
                int row = i;
                int col = j;

                // Add action listener to each button for making a move
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the current player's letter based on the radio button selection
                        char currentLetter = game.getCurrentPlayer().getLetter();
                        if (game.getCurrentPlayer().getName().equals(game.getPlayerOne().getName())) {
                            if (player1S.isSelected()) {
                                currentLetter = 'S';
                            } else if (player1O.isSelected()) {
                                currentLetter = 'O';
                            }
                        } else {
                            if (player2S.isSelected()) {
                                currentLetter = 'S';
                            } else if (player2O.isSelected()) {
                                currentLetter = 'O';
                            }
                        }

                        // Make the human move
                        if (gameMode.makeMove(row, col, currentLetter)) {
                            buttons[row][col].setText(String.valueOf(currentLetter));
                            //game.logMove(row, col, currentLetter);

                            // Check if the game type is "Simple Game" and if an "SOS" is detected
                            if (game.getGameType().equals("Simple Game") && game.getBoard().checkForSOS(row, col)) {
                                gameMode.showResults();
                                game.closeLog();
                                //dispose();
                                replayButton.setEnabled(true);
                                return;
                            }

                            if (!game.getBoard().checkForSOS(row, col)) {
                                game.switchTurns();
                            }

                            // Check if the game is over (board full or end condition met)
                            if (gameMode.isGameOver()) {
                                gameMode.showResults();
                                game.closeLog();
                                //dispose();
                                replayButton.setEnabled(true);
                            } else {
                                updateTitle();

                            }


                            // Make the computer move if it's the computer's turn
                            if (game.getCurrentPlayer().getPlayerType().equals("Computer")) {

                                computerPlayer.makeComputerMove();
                                //updateTitle();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid move. Try again.");
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    /*
    * replayGame will replay the moves on the board in the order that they were placed
    * */
    public void replayGame() {
        // Clear the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setText("-");
            }
        }

        int boardSize = 0;
        List<String[]> moves = new ArrayList<>(); // To store moves for delayed replay

        try (BufferedReader br = new BufferedReader(new FileReader("game_log.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Get board size
                if (line.startsWith("Board Size:")) {
                    boardSize = Integer.parseInt(line.split(":")[1].trim());

                }

                // Extract moves and player name
                if (line.matches(".*\\((S|O)\\).*")) {
                    Pattern pattern = Pattern.compile("(\\w+) \\((S|O)\\).*\\[(\\d+), (\\d+)]");
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        String replayCurrentPlayer = matcher.group(1); // Extract player name
                        String newLetter = matcher.group(2);           // Extract letter (S or O)
                        int x = Integer.parseInt(matcher.group(3));    // Extract x-coordinate
                        int y = Integer.parseInt(matcher.group(4));    // Extract y-coordinate

                        // Store the move details for delayed execution
                        moves.add(new String[]{replayCurrentPlayer, newLetter, String.valueOf(x), String.valueOf(y)});

                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Replay the moves with a delay
        Timer timer = new Timer(1500, null); // 1000ms (1-second) delay
        final int[] moveIndex = {0}; // To track the current move

        timer.addActionListener(e -> {
            if (moveIndex[0] < moves.size()) {
                // Get the current move
                String[] move = moves.get(moveIndex[0]);
                //get the current player
                String replayCurrentPlayer = move[0];
                //get the current letter
                String newLetter = move[1];
                //positions of letter
                int x = Integer.parseInt(move[2]);
                int y = Integer.parseInt(move[3]);

                setTitle("SOS Game - " + replayCurrentPlayer + "'s Turn");
                // Update the letters on the board
                buttons[x][y].setText(newLetter);


                moveIndex[0]++;
            } else {
                // Stop the timer when all moves are replayed
                ((Timer) e.getSource()).stop();
                gameMode.showResults();
            }
        });

        // Ensure the timer repeats until all moves are replayed
        timer.setRepeats(true);
        // Start the timer
        timer.start();


    }



    /*
    * Update the title on top of the board
    * */
    public void updateTitle() {
        setTitle("SOS Game - " + game.getCurrentPlayer().getName() + "'s Turn");
    }
}