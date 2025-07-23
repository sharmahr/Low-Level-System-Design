# Designing a Tic Tac Toe Game

## Requirements
1. The Tic-Tac-Toe game should be played on a 3x3 grid.
2. Two players take turns marking their symbols (X or O) on the grid.
3. The first player to get three of their symbols in a row (horizontally, vertically, or diagonally) wins the game.
4. If all the cells on the grid are filled and no player has won, the game ends in a draw.
5. The game should have a user interface to display the grid and allow players to make their moves.
6. The game should handle player turns and validate moves to ensure they are legal.
7. The game should detect and announce the winner or a draw at the end of the game.

Certainly! Here's an example implementation of a Tic-Tac-Toe game in Java based on the given requirements, along with explanations of the code and design patterns used:

```java
import java.util.Scanner;

class TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            System.out.println("Player " + currentPlayer + ", enter your move (row[0-2] column[0-2]): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (isValidMove(row, col)) {
                makeMove(row, col);

                if (isWinner(currentPlayer)) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }

                if (isBoardFull()) {
                    printBoard();
                    System.out.println("It's a draw!");
                    break;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isValidMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        return board[row][col] == '-';
    }

    private void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
    }

    private boolean isWinner(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
```

Explanation:
1. The `TicTacToe` class represents the game logic and state. It has a 2D character array `board` to store the game grid and a `currentPlayer` variable to keep track of the current player's turn.

2. The `initializeBoard` method initializes the game board with '-' characters to represent empty cells.

3. The `play` method is the main game loop. It repeatedly prompts the players to enter their moves, validates the moves, updates the board, and checks for a winner or a draw until the game ends.

4. The `printBoard` method displays the current state of the game board.

5. The `isValidMove` method checks if a move is valid by ensuring the selected cell is within the board boundaries and is currently empty.

6. The `makeMove` method updates the game board with the current player's symbol at the selected position.

7. The `isWinner` method checks if a player has won the game by examining the rows, columns, and diagonals of the game board.

8. The `isBoardFull` method checks if all the cells on the game board are filled, indicating a draw.

9. The `Main` class contains the main method, which creates an instance of the `TicTacToe` class and starts the game by calling the `play` method.

Design Patterns Used:
- Model-View-Controller (MVC) Pattern: Although not explicitly implemented, the code follows the principles of the MVC pattern. The `TicTacToe` class represents the model, which encapsulates the game state and logic. The `printBoard` method acts as a simple view, displaying the game board to the players. The `play` method serves as the controller, handling user input and coordinating the game flow.

- Singleton Pattern: The `TicTacToe` class can be modified to follow the Singleton pattern if you want to ensure that only one instance of the game exists throughout the application. This can be achieved by making the constructor private and providing a static method to retrieve the instance.

The code provides a basic implementation of the Tic-Tac-Toe game that meets the given requirements. It allows two players to take turns marking their symbols on the grid, validates moves, detects a winner or a draw, and displays the game board using a simple command-line interface.

The game logic is encapsulated within the `TicTacToe` class, promoting modularity and separation of concerns. The use of the MVC pattern, although not explicitly implemented, helps in organizing the code and separating the game logic from the user interface.