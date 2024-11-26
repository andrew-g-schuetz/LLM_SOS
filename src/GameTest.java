import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private Player playerOne;
    private Player playerTwo;

    /*
    * Setup before each unit test
    * */
    @BeforeEach
    public void setUp(){
        playerOne = new Player("Andrew", 'S',0, "Human");
        playerTwo = new Player("Scott", 'O',0, "Human");

        game = new Game(playerOne, playerTwo, 8, "Simple Game", false);
    }

    /*
    * Test if the board object is returned from getter
    * */
    @Test
    public void testBoardGetter(){
        assertNotNull(game.getBoard());
    }

    /*
    * Test to see if the current player is player one at the start of the game
    * */
    @Test
    public void testGetCurrentPLayer(){
        assertEquals(playerOne, game.getCurrentPlayer());
    }

    /*
    * Test if switching the players functions properly
    * */
    @Test
    public void testSwitchingPlayers(){
        assertEquals(playerOne, game.getCurrentPlayer());

        game.switchTurns();

        assertEquals(playerTwo, game.getCurrentPlayer());

        game.switchTurns();

        assertEquals(playerOne, game.getCurrentPlayer());
    }


    /*
    * Test the getter for the board object
    * */
    @Test
    public void testGetBoard(){
        Board board = game.getBoard();
        assertEquals(8, board.getSize());
    }

    /*
    * Test for getGameType
    * */
    @Test
    public void testGetGameType(){
        assertEquals("Simple Game", game.getGameType());
        assertNotEquals("General Game", game.getGameType());
    }
}
