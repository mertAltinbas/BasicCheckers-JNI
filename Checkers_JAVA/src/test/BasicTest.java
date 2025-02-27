package test;

import main.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicTest {

    private Main main;
    @BeforeEach
    void setUp() {
        main = new Main();
        main.initializeBoard();
    }

    @Test
    void testBoardInitialization() {
        int[][] board = main.getBoard();
        // Check the initial setup of the board
        assertEquals(12, countPieces(board, 1)); // Black
        assertEquals(12, countPieces(board, 2)); // White
        assertEquals(0, countPieces(board, 3)); // Black kings
        assertEquals(0, countPieces(board, 4)); // White kings
    }

    @Test
    void testBlackPiecesInRightPlace() {
        int[][] board = main.getBoard();
        // Check specific positions for black pieces
        assertEquals(1, board[0][0]);
        assertEquals(1, board[0][2]);
        assertEquals(1, board[0][4]);
        assertEquals(1, board[0][6]);
    }

    @Test
    void testWhitePiecesInRightPlace() {
        int[][] board = main.getBoard();
        // Check specific positions for white pieces
        assertEquals(2, board[7][1]);
        assertEquals(2, board[7][3]);
        assertEquals(2, board[7][5]);
        assertEquals(2, board[7][7]);
    }

    @Test
    void testInvalidMoveOutOfBounds() {
        assertFalse(main.makeMove(5, 0, 8, 1)); // out of bounds
    }

    @Test
    void testInvalidMoveOccupiedDestination() {
        assertFalse(main.makeMove(6, 0, 5, 1)); // invalid move, destination occupied
    }

    @Test
    void testValidMove() {
        assertTrue(main.makeMove(5, 1, 4, 2));
        assertEquals(0, main.getBoard()[5][1]);
        assertEquals(2, main.getBoard()[4][2]);
    }

    @Test
    void testCaptureOpponentPiece() {
        main.makeMove(2, 0, 3, 1);
        main.makeMove(5, 3, 4, 2);

        assertTrue(main.makeMove(3, 1, 5, 3));
        assertEquals(0, main.getBoard()[4][2]);
        assertEquals(1, main.getBoard()[5][3]);
    }

    @Test
    void testInvalidMoveDiagonal() {
        assertFalse(main.makeMove(7, 1, 6, 1));
    }

    @Test
    void testPieceCannotMoveBackward() {
        main.makeMove(2, 6, 3, 7);
        main.makeMove(5, 5, 4, 6);
        assertFalse(main.makeMove(3, 7, 2, 6));
    }


    private int countPieces(int[][] board, int pieceType) {
        int count = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == pieceType) {
                    count++;
                }
            }
        }
        return count;
    }
}