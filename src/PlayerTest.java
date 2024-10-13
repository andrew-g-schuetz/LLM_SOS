import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player playerOne;
    Player playerTwo;
    @BeforeEach
    public void setUp(){
        playerOne = new Player("Andrew", 'S');
        playerTwo = new Player("Scott", 'O');
    }

    /**
     * Test the ability for Player class to fetch name
     */

    @Test
    public void testGetName(){
        assertEquals("Andrew", playerOne.getName());
        assertEquals("Scott", playerTwo.getName());

    }

    /*
    * Test whether the letter getters return properly
    * */
    @Test
    public void testGetLetter(){
        assertEquals('S', playerOne.getLetter());
        assertEquals('O', playerTwo.getLetter());
    }



}
