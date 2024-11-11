import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player playerOne;
    Player playerTwo;
    @BeforeEach
    public void setUp(){
        playerOne = new Player("Andrew", 'S', 0, "Human");
        playerTwo = new Player("Scott", 'O', 0, "Human");
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

    /*
    * Test the get score method for Players
    * */
    @Test
    public void testGetScore(){
        assertEquals(0, playerOne.getScore());
        assertEquals(0, playerTwo.getScore());
    }

    /*
    * Test the incrementScore method on players
    * */
    @Test
    public void testIncrementScore(){
        //Increments player one score by 1
        playerOne.incrementScore();
        assertEquals(1, playerOne.getScore());

        //Increments player two score by 2
        playerTwo.incrementScore();
        playerTwo.incrementScore();
        assertEquals(2, playerTwo.getScore());


    }


}
