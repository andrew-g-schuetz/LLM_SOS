public interface SOSGameMode {
    boolean makeMove(int row, int col, char currentLetter);

    boolean checkForWinner(int row, int col);

    boolean isGameOver();

    void showResults();
}
