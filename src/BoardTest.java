import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Player playerOne = new Player("Andrew", 'S', 0, "Human");
    private Player playerTwo = new Player("Scott", 'O', 0, "Human");
    private Game game = new Game(playerOne, playerTwo, 8, "Simple Game");
    /*
    * Setup a three by three board for the setup
    * */
    @BeforeEach
    public void setUp(){

         board = new Board(8, game);
    }

    @Test
    public void testBoardSize(){
        assertEquals(8,board.getSize());
    }

    @Test
    public void testTurn(){
        assertEquals('S', board.getTurn());
    }

    /*
    * testing a move inside the bounds of the board
    * */
    @Test
    public void testMove(){
        assertTrue(board.makeMove(0,0,'S'));
        assertEquals('O', board.getTurn());
    }

    /*
    * Testing a move out of the bounds of the board
    * */
    @Test
    public void testInvalidBoundsMove(){
        assertFalse(board.makeMove(-2,-2,'S'));
        assertFalse(board.makeMove(9,9,'O'));
    }

    /*
    * test a move on the board with a space that is already filled
    * */
    @Test
    public void testMoveOnFilledSpace(){
        assertTrue(board.makeMove(0,0,'S'));
        assertFalse(board.makeMove(0,0,'O'));
    }

    /*
    * Test if the boards will change turns after a player places a piece
    * */
    @Test
    public void testTurnsRotating(){
        assertEquals('S', board.getTurn());
        assertTrue(board.makeMove(0,0,'S'));

        assertEquals('O', board.getTurn());
        assertTrue(board.makeMove(0,1, 'O'));

        assertEquals('S', board.getTurn());

    }

    /*
    * Test to see if you can fill out the entire board
    * */
    @Test
    public void testFillBoard(){
        for(int i = 0; i < board.getSize(); i++){
            for(int j =0; j<board.getSize(); j++){
                assertTrue(board.makeMove(i,j,board.getTurn()));
            }
        }
    }

    /*
    * Check for horizontal SOS being true
    * */
    @Test
    public void testSOSSuccessfulHorizontal(){
        board.makeMove(0,0,'S');
        board.makeMove(0,1,'O');
        board.makeMove(0,2,'S');

        boolean result = board.checkForSOS(0,2);

        assertTrue(result);
    }

    /*
    * Test for vertical SOS being true
    * */
    @Test
    public void testSOSSuccessfulVertical(){
        board.makeMove(0,0,'S');
        board.makeMove(1,0,'O');
        board.makeMove(2,0,'S');

        boolean result = board.checkForSOS(2,0);

        assertTrue(result);
    }

    /*
    * Test for diagonal SOS being true
    * */
    @Test
    public void testSOSDiagonal(){
        board.makeMove(0,0,'S');
        board.makeMove(1,1,'O');
        board.makeMove(2,2,'S');

        boolean result = board.checkForSOS(2,2);

        assertTrue(result);
    }

    /*
    * Making sure SOS isn't formed
    * */
    @Test
    public void testSOSWhenNotFormed(){

        board.makeMove(1, 0, 'S');
        board.makeMove(1, 1, 'O');
        board.makeMove(1, 2, 'O');


        boolean result = board.checkForSOS(1, 2);


        assertFalse(result);
    }

    /*
     * Making sure SOS isn't formed
     * */
    @Test
    public void testSOSWhenNotFormedTwo(){

        board.makeMove(1, 0, '0');
        board.makeMove(1, 1, 'O');
        board.makeMove(1, 2, 'S');


        boolean result = board.checkForSOS(1, 2);


        assertFalse(result);
    }

    /*
     * Making sure SOS isn't formed
     * */
    @Test
    public void testSOSWhenNotFormedThree(){

        board.makeMove(1, 0, '0');
        board.makeMove(1, 1, 'S');
        board.makeMove(1, 2, '0');


        boolean result = board.checkForSOS(1, 2);


        assertFalse(result);
    }

    /*
     * Making sure SOS isn't formed
     * */
    @Test
    public void testSOSWhenNotFormedFour(){

        board.makeMove(1, 0, 'S');
        board.makeMove(1, 1, 'S');
        board.makeMove(1, 2, 'S');


        boolean result = board.checkForSOS(1, 2);


        assertFalse(result);
    }

    /*
     * Making sure SOS isn't formed
     * */
    @Test
    public void testSOSWhenNotFormedFive(){

        board.makeMove(1, 0, 'O');
        board.makeMove(1, 1, 'O');
        board.makeMove(1, 2, 'O');


        boolean result = board.checkForSOS(1, 2);


        assertFalse(result);
    }


}
