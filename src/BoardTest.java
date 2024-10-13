import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    /*
    * Setup a three by three board for the setup
    * */
    @BeforeEach
    public void setUp(){

        board = new Board(8);
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
    public void testinvalidBoundsMove(){
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


}