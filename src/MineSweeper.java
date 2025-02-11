
import java.util.Random;
import java.util.Scanner;


public class MineSweeper {

    private String[][] board;
    private String[][] mineMap;
    private int rows, cols, mineCount;
    private boolean gameOver = false;
    private int openedCells = 0;
    
    public MineSweeper(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = (rows * cols) / 4;
        this.board = new String[rows][cols];
        this.mineMap = new String[rows][cols];
        createBoard();
        placeMines();
    }

    public void createBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = "-";
                mineMap[i][j] = "-";
            }
        }
    }

    public void placeMines() {
        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < mineCount) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!mineMap[r][c].equals("*")) {
                mineMap[r][c] = "*";
                placedMines++;
            }
        }
    }

    public int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nr = r + i;
                int nc = c + j;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && mineMap[nr][nc].equals("*")) {
                    count++;
                }
            }
        }
        return count;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Minesweeper Game!");
        printBoard();
        
        while (!gameOver) {
            System.out.print("Enter Row: ");
            int r = scanner.nextInt();
            System.out.print("Enter Column: ");
            int c = scanner.nextInt();
            
            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                System.out.println("Invalid coordinates, try again!");
                continue;
            }
            if (!board[r][c].equals("-")) {
                System.out.println("This coordinate has already been selected, enter another coordinate.");
                continue;
            }
            
            if (mineMap[r][c].equals("*")) {
                System.out.println("Game Over!!");
                revealMines();
                break;
            } else {
                int adjacentMines = countAdjacentMines(r, c);
                board[r][c] = String.valueOf(adjacentMines);
                openedCells++;
                printBoard();
                if (openedCells == (rows * cols - mineCount)) {
                    System.out.println("You Win!");
                    break;
                }
            }
        }
        scanner.close();
    }
    
    private void printBoard() {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    private void revealMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mineMap[i][j].equals("*")) {
                    board[i][j] = "*";
                }
            }
        }
        printBoard();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows, cols;
        do {
            System.out.print("Enter the number of rows (min 2): ");
            rows = scanner.nextInt();
            System.out.print("Enter the number of columns (min 2): ");
            cols = scanner.nextInt();
            if (rows < 2 || cols < 2) {
                System.out.println("Please enter a matrix of size at least 2x2.");
            }
        } while (rows < 2 || cols < 2);
        
        MineSweeper game = new MineSweeper(rows, cols);
        game.play();
    }
}





