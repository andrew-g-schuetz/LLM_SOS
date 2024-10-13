/*
* Player class that represents the characteristics of the player
* */
public class Player {

    //name to the player given by the user
    private String name;
    //letter the player has chosen: 'S' or 'O'
    private char letter;

    /*
    * Player constructor class
    * String:name: name of the player
    * char:letter: letter of the player
    * */
    public Player(String name, char letter){
        this.name = name;
        this.letter = letter;
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

}
