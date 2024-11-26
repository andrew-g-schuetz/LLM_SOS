import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Game {

    private Player playerOne;
    private Player playerTwo;
    private Board board;
    private Player currentPlayer;
    private String gameType;
    private boolean recordGame;
    private BufferedWriter gameLogWriter;
    /*
    * Game constructor
    * playerOne: First player
    * playerTwo: Second player
    * boardSize: size of game board
    * */
    public Game(Player playerOne, Player playerTwo, int boardSize, String gameType, boolean recordGame){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board(boardSize,this);
        this.currentPlayer = playerOne;
        this.gameType = gameType;
        this.recordGame = recordGame;

        if(recordGame){
            try{
                gameLogWriter = new BufferedWriter(new FileWriter("game_log.txt"));
                gameLogWriter.write("Game Type: " + gameType + "\n");
                gameLogWriter.write("Board Size: " + boardSize + "\n");
                gameLogWriter.write(playerOne.getName() + " vs " + playerTwo.getName() + "\n");
                gameLogWriter.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void logMove(int row, int col, char letter) {

            try {
                gameLogWriter.write(currentPlayer.getName() + " (" + letter + ") moved to [" + row + ", " + col + "]\n");
                gameLogWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void closeLog() {
        if (gameLogWriter != null) {
            try {
                gameLogWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
