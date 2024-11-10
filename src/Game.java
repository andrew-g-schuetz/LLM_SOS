

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private Board board;
    private Player currentPlayer;
    private String gameType;

    /*
    * Game constructor
    * playerOne: First player
    * playerTwo: Second player
    * boardSize: size of game board
    * */
    public Game(Player playerOne, Player playerTwo, int boardSize, String gameType){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board(boardSize,this);
        this.currentPlayer = playerOne;
        this.gameType = gameType;
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

    public Player getSecondPlayer(){
        if (currentPlayer == playerOne){
            return playerTwo;
        }else{
            return playerOne;
        }
    }

    public Player getPlayerOne(){
        return playerOne;
    }

    public Player getPlayerTwo(){
        return playerTwo;
    }
    /*
    * Returns the game type
    * */
    public String getGameType(){return gameType; }







}
