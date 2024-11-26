import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;

public class ComputerTest {

    private Game mockGame;
    private SOSGameMode mockGameMode;
    private JButton[][] buttons;
    private GameBoardGUI mockGameBoardGUI;
    private Computer computer;

    @BeforeEach
    public void setUp() {
        // Mock players
        Player playerOne = new Player("Andrew", 'S', 0, "Human");
        Player playerTwo = new Player("Scott", 'O', 0, "Human");

        // Mock Game setup with players and a board size of 3
        mockGame = new Game(playerOne, playerTwo, 3, "Simple Game", false);

        // Mock GameBoardGUI setup (assuming it uses the mockGame)
        mockGameBoardGUI = new GameBoardGUI(mockGame, false);

        // Create a 3x3 grid of buttons for testing purposes
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("-");
            }
        }

        // Mock SOSGameMode setup (SimpleGame is assumed to extend SOSGameMode)
        SimpleGame simpleGameMode = new SimpleGame(mockGame);

        // Initialize the Computer player
        computer = new Computer("Computer", 'S', 0, mockGame, simpleGameMode, buttons, mockGameBoardGUI);
    }

    @Test
    public void testGameInitialization() {
        // Ensure the board is initially empty
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("-", buttons[i][j].getText(), "The board should be initially empty.");
            }
        }
        // Ensure the first player is player one
        assertEquals(mockGame.getCurrentPlayer().getName(), "Andrew", "The first player should be player one.");
    }



    @Test
    public void testSwitchTurnsAfterMove() {
        // Before the move, the current player should be the first player
        Player initialPlayer = mockGame.getCurrentPlayer();

        // Make the computer move
        computer.makeComputerMove();

        // Wait for the move to complete
        try {
            Thread.sleep(2500); // Wait for the timer to execute the move
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // After the move, the turn should switch to the other player
        Player currentPlayerAfterMove = mockGame.getCurrentPlayer();
        assertNotEquals(initialPlayer, currentPlayerAfterMove, "The turn should switch after the computer move.");
    }



}
