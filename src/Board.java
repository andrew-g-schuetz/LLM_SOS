public class Board {
    private char[][] grid;
    private int size;
    private char turn = 'S';
    Game game;

    public Board(int size, Game game){
        this.size = size;
        this.grid = new char[size][size];

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                grid[i][j] = '-';
            }
        }
        this.game = game;
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

    /**
     * Check if the board is full
     *
     * @return True if no empty cells are left, otherwise false
     */
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '-') {
                    return false; // An empty cell is found
                }
            }
        }
        return true; // No empty cells left
    }

    // Get cell value at (row, col)
    public char getCell(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return grid[row][col];
        }
        return ' ';  // Return a space if the row/col is out of bounds
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
            turn = (turn == 'S') ?'O':'S';
            return true;
        }
        return false;
    }

    /*
     * Check if placing a letter results in an "SOS" formation
     *
     * @param row the row index of the last placed letter
     * @param column the column index of the last placed letter
     * @return True if an "SOS" is found, else return false
     */
    public boolean checkForSOS(int row, int column) {
        char currentLetter = grid[row][column];

        // Check if the current letter is either 'S' or 'O'
        if (currentLetter != 'S' && currentLetter != 'O') {
            return false;
        }

        // Determine who placed the piece
        boolean hasSOS = false;

        // Check for "SOS" in all directions from the placed letter
        if (currentLetter == 'S') {
            hasSOS = checkSurroundingForS(row, column);
            if (hasSOS) {

                return true; // Player One wins
            }
        } else if (currentLetter == 'O') {
            hasSOS = checkSurroundingForO(row, column);
            if (hasSOS) {

                return true; // Player Two wins
            }
        }

        return false; // No SOS found, continue the game
    }

    /*
     * Check surrounding positions for SOS formation with 'S' in the center
     *
     * @param row the row index of the 'S'
     * @param column the column index of the 'S'
     * @return True if SOS formation is found, else return false
     */
    private boolean checkSurroundingForS(int row, int column) {

        return (checkDirection(row, column, 0, -1, 'O', 'S', 'S') ||  // Left
                checkDirection(row, column, 0, 1, 'O', 'S', 'S') ||   // Right
                checkDirection(row, column, -1, 0, 'O', 'S', 'S') ||  // Up
                checkDirection(row, column, 1, 0, 'O', 'S', 'S') ||   // Down
                checkDirection(row, column, -1, -1, 'O', 'S', 'S') || // Top-left
                checkDirection(row, column, 1, 1, 'O', 'S', 'S') ||   // Bottom-right
                checkDirection(row, column, -1, 1, 'O', 'S', 'S') ||  // Top-right
                checkDirection(row, column, 1, -1, 'O', 'S', 'S'));   // Bottom-left
    }

    /*
     * Check surrounding positions for SOS formation with 'O' in the center
     *
     * @param row the row index of the 'O'
     * @param column the column index of the 'O'
     * @return True if SOS formation is found, else return false
     */
    private boolean checkSurroundingForO(int row, int column) {
        // Check for "SOS" formation with 'O' in the center
        return (checkDirectionForO(row, column, 0, -1) ||  // Left
                checkDirectionForO(row, column, 0, 1) ||   // Right
                checkDirectionForO(row, column, -1, 0) ||  // Up
                checkDirectionForO(row, column, 1, 0) ||   // Down
                checkDirectionForO(row, column, -1, -1) || // Top-left
                checkDirectionForO(row, column, 1, 1) ||   // Bottom-right
                checkDirectionForO(row, column, -1, 1) ||  // Top-right
                checkDirectionForO(row, column, 1, -1));   // Bottom-left
    }

    /*
     * Check for SOS formation with 'O' at the center by looking in one direction
     *
     * @param row the row index of the 'O'
     * @param column the column index of the 'O'
     * @param rowOffset vertical offset to check for 'S'
     * @param columnOffset horizontal offset to check for 'S'
     * @return True if SOS formation is found, else return false
     */
    private boolean checkDirectionForO(int row, int column, int rowOffset, int columnOffset) {
        // Calculate the positions of the potential SOS formation
        int newRow1 = row + rowOffset;        // Position of the first 'S'
        int newCol1 = column + columnOffset;  // Position of the first 'S'
        int newRow2 = row - rowOffset;        // Position of the second 'S'
        int newCol2 = column - columnOffset;  // Position of the second 'S'

        // Check if the first position is within bounds and is 'S'
        boolean firstCondition = isInBounds(newRow1, newCol1) && grid[newRow1][newCol1] == 'S';
        // Check if the second position is within bounds and is 'S'
        boolean secondCondition = isInBounds(newRow2, newCol2) && grid[newRow2][newCol2] == 'S';

        // Both conditions must be true to form an SOS with 'O' as the last piece
        return firstCondition && secondCondition;
    }

    /*
     * Check for SOS formation in a specific direction
     *
     * @param row the starting row index
     * @param column the starting column index
     * @param rowOffset vertical offset for the direction
     * @param columnOffset horizontal offset for the direction
     * @param firstChar the character to match in the first position
     * @param secondChar the character to match in the center position
     * @param thirdChar the character to match in the last position
     * @return True if SOS formation is found, else return false
     */
    private boolean checkDirection(int row, int column, int rowOffset, int columnOffset, char firstChar, char secondChar, char thirdChar) {
        int newRow1 = row + rowOffset;
        int newCol1 = column + columnOffset;
        int newRow2 = row + 2 * rowOffset;
        int newCol2 = column + 2 * columnOffset;

        // Check bounds and conditions for SOS
        return (isInBounds(newRow1, newCol1) && grid[newRow1][newCol1] == firstChar &&
                isInBounds(newRow2, newCol2) && grid[newRow2][newCol2] == thirdChar);
    }

    /*
     * Check if the specified position is within the bounds of the board
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return True if the position is within bounds, else return false
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }



}
