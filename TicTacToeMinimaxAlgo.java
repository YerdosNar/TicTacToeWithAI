package MyGame;

import java.util.*;

public class TicTacToeMinimaxAlgo {
    private static final List<Integer> CHOICES = new ArrayList<>();
    static final Scanner SC = new Scanner(System.in);
    private static final int SIZE = 3;
    private static final char[][] BOARD = new char[SIZE][SIZE];
    private final static char PERSON = 'X';
    private final static char CPU = 'O';
    public static void fillTheBoard () {
        for (char[] chars : BOARD) {
            Arrays.fill(chars, ' ');
        }
    }
    public static void printTheBoard () {
        System.out.println(BOARD[0][0] + "|" + BOARD[0][1] + "|" + BOARD[0][2]);
        System.out.println("-+-+-");
        System.out.println(BOARD[1][0] + "|" + BOARD[1][1] + "|" + BOARD[1][2]);
        System.out.println("-+-+-");
        System.out.println(BOARD[2][0] + "|" + BOARD[2][1] + "|" + BOARD[2][2]);
    }

    /** this method fills the board a player or the cpu makes its move
     *
     * @param i is the place that the player or the cpu chose
     * @param choice this can be either "O" or "X" depending who is playing
     */
    public static void boardAfterChoice (int i, char choice) {
        switch (i) {
            case 1:
                BOARD[2][0] = choice;
                break;
            case 2:
                BOARD[2][1] = choice;
                break;
            case 3:
                BOARD[2][2] = choice;
                break;
            case 4:
                BOARD[1][0] = choice;
                break;
            case 5:
                BOARD[1][1] = choice;
                break;
            case 6:
                BOARD[1][2] = choice;
                break;
            case 7:
                BOARD[0][0] = choice;
                break;
            case 8:
                BOARD[0][1] = choice;
                break;
            case 9:
                BOARD[0][2] = choice;
                break;
            default:
                System.out.println("Wrong!");
                break;
        }
    }

    /** I wrote this method to get player's input
     * here I could have handled some exceptions such as writing over cpu's choice, but I hope player will not do that
     * ... please don't write over cpu's choice. I don't want to write any try-catch or do-while things.
     */
    public static void personChoice () {
        int i = SC.nextInt();
        CHOICES.add(i);
        boardAfterChoice(i, PERSON);
    }

    /** here cpu chooses based on minimax algorithm's evaluation
     *
     */
    public static void cpuChoice () {
        int bestScore = Integer.MIN_VALUE; //MIN_VALUE is for checking... later you'll see in the code
        int bestMove = -1;
        for (int i = 1; i <= 9; i++) { //here I'm going through the List to check
            if (!CHOICES.contains(i)) { /*if the List doesn't contain that choice
            in other words if that position is empty */
                boardAfterChoice(i, CPU); //then cpu chooses that place and puts "O"
                int score = minimax(BOARD, 0, false); /*evaluates with the minimax algorithm
                in range (-10 to +10) */
                boardAfterChoice(i, ' '); //after checking, cpu erases "O" and puts empty space back
                if (score > bestScore) { //here it checks if score is higher than other options
                    bestScore = score; //if it is higher, then gives "bestScore" value of "score"
                    bestMove = i; //and makes the move, because it is higher (better)
                }
            }
        }
        CHOICES.add(bestMove);
        boardAfterChoice(bestMove, CPU);
    }

    /** this method is for the minimax algorithm and works very well
     *
     *
     * @param board game board
     * @param depth depth was search
     * @param isMax if it's true -> cpu, else -> player
     * @return evaluation
     */
    public static int minimax (char[][] board, int depth, boolean isMax) {
        if (checkWin(board, CPU)) {//these are the conditions to get out of the recursive method
            return 10;
        }
        if (checkWin(board, PERSON)) {
            return -10;
        }
        if (isBoardFilled(board)) {
            return 0;
        }
        int bestScore = (isMax) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (isMax) ? CPU : PERSON;
                    int score = minimax(board, depth + 1, !isMax);
                    board[i][j] = ' ';
                    bestScore = (isMax) ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }
    public static boolean isBoardFilled(char[][] arr) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (arr[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean checkWin (char[][] arr, char symbol) {
        for (int i = 0; i < SIZE; i++) {
            if ((arr[i][0] == symbol && arr[i][1] == symbol && arr[i][2] == symbol) ||
                    (arr[0][i] == symbol && arr[1][i] == symbol && arr[2][i] == symbol))
                return true;
        }
        return (arr[0][0] == symbol && arr[1][1] == symbol && arr[2][2] == symbol) ||
                (arr[0][2] == symbol && arr[1][1] == symbol && arr[2][0] == symbol);
    }
    public static void main(String[] args) {
        boolean end = true;
        fillTheBoard();
        printTheBoard();
        while (end) {
            System.out.print("Enter your choice: ");
            personChoice();
            printTheBoard();
            if (checkWin(BOARD, PERSON)) {
                System.out.println("You won!");
                end = false;
            } else if (!checkWin(BOARD, PERSON) && CHOICES.size() == 9) {
                System.out.println("Draw.");
                break;
            }
            System.out.println("-----\nNow CPU: ");
            cpuChoice();
            printTheBoard();
            if (checkWin(BOARD, CPU)) {
                System.out.println("You lost...");
                end = false;
            } else if (CHOICES.size() == 9) {
                System.out.println("Draw.");
                end = false;
            }
        }
    }
}