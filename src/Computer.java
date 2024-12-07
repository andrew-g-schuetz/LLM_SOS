import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Computer extends Player {

    private Game game;
    private SOSGameMode gameMode;
    private JButton[][] buttons;
    private GameBoardGUI gameBoardGUI;
    private static String API_KEY;

    public Computer(String name, char letter, int score, Game game, SOSGameMode gameMode, JButton[][] buttons, GameBoardGUI gameBoardGUI) {
        super(name, letter, score, "Computer");
        this.game = game;
        this.gameMode = gameMode;
        this.buttons = buttons;
        this.gameBoardGUI = gameBoardGUI;
    }

    //String representation of the board's current state
    private String getBoardState() {
        StringBuilder boardState = new StringBuilder();
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                boardState.append(game.getBoard().getCell(i, j));
            }
            boardState.append("\n");
        }
        return boardState.toString();
    }

    //Escape special characters for JSON formatting
    private String escapeForJSON(String input) {
        return input.replace("\n", "\\n").replace("\"", "\\\"");
    }

    //API call
    private int[] getMoveFromAPI(String boardState) throws IOException {
        //Obtain API KEY
        try{
            API_KEY = Config.getApiKey();
        }catch(IOException e){
            throw new RuntimeException("API KEY error" + e);
        }
        String escapedBoardState = escapeForJSON(boardState);

        //Request body for OPENAI
        String requestBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": ["
                + "    {\"role\": \"system\", \"content\": \"You are an AI that provides the best move for a game of SOS based on the current board layout. The move must be in the format: letter, row, col (for example: 'S, row 3, column 4'). Make sure the response is always in this format.\"},"
                + "    {\"role\": \"user\", \"content\": \"Here is the current board layout:\\n" + escapedBoardState + "Please provide the letter (S or O), row, and column for the best move.\"}"
                + "],"
                + "\"max_tokens\": 50"
                + "}";

        //Setup the HTTP connection
        HttpURLConnection connection = (HttpURLConnection) new URL("https://api.openai.com/v1/chat/completions").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        //Write reqest body to the connection
        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes("UTF-8"));
            os.flush();
        }

        //Handling the response
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = connection.getInputStream()) {
                //Read the response into a string
                String response = new BufferedReader(new InputStreamReader(is))
                        .lines()
                        .collect(Collectors.joining("\n"));

                // Extract the "content" field from response
                String content = response.split("\"content\":")[1].split("}", 2)[0].trim();

                // Validate the response format using regex
                Pattern pattern = Pattern.compile("(S|O),?\\s*row\\s*(\\d+),\\s*column\\s*(\\d+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(content);

                if (matcher.find()) {
                    char letter = matcher.group(1).charAt(0); // 'S' or 'O'
                    int row = Integer.parseInt(matcher.group(2)) - 1; // Convert to 0-indexed
                    int column = Integer.parseInt(matcher.group(3)) - 1; // Convert to 0-indexed

                    return new int[]{row, column, letter};
                } else {
                    throw new IOException("Failed to parse move from AI response: Invalid format. Expected 'letter, row, column'. Response: " + content);
                }
            }
        } else {
            //Handle errors
            try (InputStream is = connection.getErrorStream()) {
                String errorResponse = new BufferedReader(new InputStreamReader(is))
                        .lines()
                        .collect(Collectors.joining("\n"));
                throw new IOException("Error: " + responseCode + " - " + errorResponse);
            }
        }
    }

    public void makeComputerMove() {
        try {
            String boardState = getBoardState();
            int[] move = new int[0];
            //Make sure the game is not over before making an API call
            if(!gameMode.isGameOver()) {move = getMoveFromAPI(boardState);}
            
            int row = move[0];
            int col = move[1];
            char currentLetter = (char) move[2];
            System.out.println("Placed " + currentLetter + " on row: " + (int)(row+1) + " and column: " + (int)(col+1));

            // Ensure the move is valid (check if the cell is empty)
            while (game.getBoard().getCell(row, col) != '-') {
                // If the cell is occupied, request another move from the AI
                System.out.println("Cell (" + row + ", " + col + ") is already occupied. Asking AI for a new move...");
                boardState = getBoardState();  // Re-fetch the board state in case it's updated
                if(!gameMode.isGameOver()) {move = getMoveFromAPI(boardState);}  // Get the next move from the API
                row = move[0];
                col = move[1];
                currentLetter = (char) move[2];
                System.out.println("Placed " + currentLetter + " on row: " + (int)(row+1) + " and column: " + (int)(col+1));
            }

            // Ensure the move is valid
            if (game.getBoard().getCell(row, col) == '-') {
                int tempRow = row;
                int tempCol = col;
                char tempLetter = currentLetter;
                if (gameMode.makeMove(row, col, currentLetter)) {
                    // Update the UI and game state
                    Timer timer = new Timer(2000, e -> {
                        buttons[tempRow][tempCol].setText(String.valueOf(tempLetter));

                        if (game.getGameType().equals("Simple Game") && game.getBoard().checkForSOS(tempRow, tempRow)) {
                            gameMode.showResults();
                            return;
                        }

                        if (!game.getBoard().checkForSOS(tempRow, tempCol)) {
                            game.switchTurns();
                        }

                        if (gameMode.isGameOver()) {
                            gameMode.showResults();
                        } else {
                            gameBoardGUI.updateTitle();
                            if (game.getCurrentPlayer().getPlayerType().equals("Computer")) {
                                makeComputerMove();
                            }
                        }
                    });

                    timer.setRepeats(false);
                    timer.start();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
