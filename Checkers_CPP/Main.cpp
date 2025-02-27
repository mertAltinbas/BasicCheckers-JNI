#include <vector>
#include <jni.h>
#include <iostream>
#include "main_Main.h"

const int BOARD_SIZE = 8;
enum Piece {
    EMPTY, BLACK, WHITE, BLACK_KING, WHITE_KING
};

bool whiteTurn = true;  // Track turn


std::vector<std::vector<Piece>> board(BOARD_SIZE, std::vector<Piece>(BOARD_SIZE, EMPTY));

void initializeBoard() {
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[row][col] = EMPTY;  // every cell empty first
            if ((row + col) % 2 == 0) { // only black cells
                if (row < 3) {
                    board[row][col] = BLACK; // top 3 row
                } else if (row > 4) {
                    board[row][col] = WHITE; // last 3 row
                }
            }
        }
    }
}

bool isBlackSquare(int row, int col) {
    return (row + col) % 2 == 0;
}

bool makeMove(int startRow, int startCol, int endRow, int endCol) {
    std::cout << "Trying Move: (" << startRow << ", " << startCol << ") to (" << endRow << ", " << endCol
              << ")\n";

    // control out of bounds
    if (startRow < 0 || startRow >= BOARD_SIZE || startCol < 0 || startCol >= BOARD_SIZE ||
        endRow < 0 || endRow >= BOARD_SIZE || endCol < 0 || endCol >= BOARD_SIZE) {
        std::cout << "Invalid Move: Out of bounds.\n";
        return false;
    }

    // Turn Check
    if ((board[startRow][startCol] == WHITE || board[startRow][startCol] == WHITE_KING) && !whiteTurn) {
        std::cout << "Invalid Move: not White Turn.\n";
        return false;
    }
    if ((board[startRow][startCol] == BLACK || board[startRow][startCol] == BLACK_KING) && whiteTurn) {
        std::cout << "Invalid Move: Not black turn.\n";
        return false;
    }

    // selected cell empty?
    if (board[startRow][startCol] == EMPTY) {
        std::cout << "Invalid Move: Selected cell is empty.\n";
        return false;
    }

    // destination cell empty?
    if (board[endRow][endCol] != EMPTY) {
        std::cout << "Invalid Move: Destination cell is empty.\n";
        return false;
    }

    // Is it black cell
    if (!isBlackSquare(endRow, endCol)) {
        std::cout << "Invalid Move: only move in black cells.\n";
        return false;
    }

    int rowDiff = endRow - startRow;
    int colDiff = endCol - startCol;

    // control of piece, up or down
    if ((board[startRow][startCol] == BLACK && rowDiff <= 0) || (board[startRow][startCol] == WHITE && rowDiff >= 0)) {
        std::cout << "Invalid Move: Invalid direction.\n";
        return false;
    }

    // beat a piece
    if (std::abs(rowDiff) == 2 && std::abs(colDiff) == 2) {
        int midRow = (startRow + endRow) / 2;
        int midCol = (startCol + endCol) / 2;

        // is any enemy piece in middle cell?
        if (board[midRow][midCol] == EMPTY || board[midRow][midCol] == board[startRow][startCol]) {
            std::cout << "Invalid Move: No valid pieces in middle cell.\n";
            return false;
        }

        // clear middle cell (take opponent piece)
        board[midRow][midCol] = EMPTY;
    }

    // Is move valid?
    if ((std::abs(rowDiff) != 1 && std::abs(rowDiff) != 2) || (std::abs(colDiff) != 1 && std::abs(colDiff) != 2)) {
        std::cout << "Invalid Move: The movement is not a one-step or two-step diagonal.\n";
        return false;
    }

    // Move piece
    board[endRow][endCol] = board[startRow][startCol];
    board[startRow][startCol] = EMPTY;

    // check piece is going to be king
    if ((board[endRow][endCol] == BLACK && endRow == BOARD_SIZE - 1) ||
        (board[endRow][endCol] == WHITE && endRow == 0)) {
        board[endRow][endCol] = (board[endRow][endCol] == BLACK) ? BLACK_KING : WHITE_KING;
    }

    std::cout << "Move Successful.\n";

    // change turn if not captured opponent piece
    if (std::abs(rowDiff) != 2) {
        whiteTurn = !whiteTurn;
    }

    return true;
}


JNIEXPORT jboolean JNICALL
Java_main_Main_makeMove(JNIEnv *env, jobject obj, jint startRow, jint startCol, jint endRow, jint endCol) {
    return makeMove(startRow, startCol, endRow, endCol);
}

JNIEXPORT jobjectArray JNICALL Java_main_Main_getBoard(JNIEnv *env, jobject obj) {
    jobjectArray boardArray = env->NewObjectArray(BOARD_SIZE, env->FindClass("[I"), nullptr);
    for (int i = 0; i < BOARD_SIZE; i++) {
        jintArray rowArray = env->NewIntArray(BOARD_SIZE);
        env->SetIntArrayRegion(rowArray, 0, BOARD_SIZE, (jint *) &board[i][0]);
        env->SetObjectArrayElement(boardArray, i, rowArray);
    }
    return boardArray;
}

JNIEXPORT void JNICALL Java_main_Main_initializeBoard(JNIEnv *env, jobject obj) {
    initializeBoard();
}