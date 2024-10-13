

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private Board board;
    private Player currentPlayer;

    /*
    * Game constructor
    * playerOne: First player
    * playerTwo: Second player
    * boardSize: size of game board
    * */
    public Game(Player playerOne, Player playerTwo, int boardSize){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board(boardSize);
        this.currentPlayer = playerOne;
    }

    /*
    * Switch the current player
    * */
    public void switchTurns(){
        currentPlayer = (currentPlayer == playerOne) ? playerTwo: playerOne;
    }


    /*
    * Return the board variable that holds the Board class object
    * */
    public Board getBoard(){
        return board;
    }

    /*
    * Get the current player
    * */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }



}
