/*
* Player class that represents the characteristics of the player
* */
public class Player{

    //name to the player given by the user
    private String name;
    //letter the player has chosen: 'S' or 'O'
    private char letter;
    //Score for the player
    private int score;

    private final String playerType;
    /*
    * Player constructor class
    * String:name: name of the player
    * char:letter: letter of the player
    * */
    public Player(String name, char letter, int score, String playerType){
        this.name = name;
        this.letter = letter;
        this.score = score;
        this.playerType = playerType;
    }

    /*
    *returns the name of the Player
    * */
    public String getName(){
        return name;
    }

    /*
    * returns the letter of the Player
    * */
    public char getLetter(){
        return letter;
    }

    public String getPlayerType(){return playerType;}

    public void setLetter(char letter){
        this.letter = letter;
    }

    public int getScore(){
        return score;
    }

    public void incrementScore(){
        score++;
    }

}
