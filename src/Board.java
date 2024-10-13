public class Board {
    private char[][] grid;
    private int size;
    private char turn = 'S';

    public Board(int size){
        this.size = size;
        this.grid = new char[size][size];

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                grid[i][j] = '-';
            }
        }
    }



    /*
    * Get the size of the board
    * */
    public int getSize(){
        return size;
    }

    /*
    * Getter for the turn variable
    * */
    public char getTurn(){
        return turn;
    }

    /*
    * Make a move on the board
    *
    * return True if board placement is valid, else return false
    * */
    public boolean makeMove(int row, int column, char letter){
        if(row >= 0 && row < grid.length && column >= 0 && column < grid.length
                 && grid[row][column] == '-'){
            grid[row][column] = letter;

            turn = (turn == 'S') ? 'O': 'S';
            return true;
        }
        return false;
    }
}
