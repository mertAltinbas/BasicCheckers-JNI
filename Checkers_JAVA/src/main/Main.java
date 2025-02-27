package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;

public class Main extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JButton[][] buttons = new JButton[8][8];
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int targetRow = -1;
    private int targetCol = -1;  
    Font font = new Font("Arial", Font.BOLD, 100);
    Font font1 = new Font("Arial", Font.BOLD, 50);

    public Main() {
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 8));

        initializeBoard();
        createBoardButtons();
        setupKeyBindings();
        updateBoard();
    }

    private void createBoardButtons() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground((j + i) % 2 == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                add(buttons[i][j]);
            }
        }
    }

    private void setupKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0), "selectInitialCell");
        actionMap.put("selectInitialCell", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectInitialCell();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedCell(-1, 0);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedCell(1, 0);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedCell(0, -1);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedCell(0, 1);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "selectPiece");
        actionMap.put("selectPiece", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPiece();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "movePiece");
        actionMap.put("movePiece", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePiece();
            }
        });
    }

    private void selectInitialCell() {
        selectedRow = 0;
        selectedCol = 0;
        highlightSelectedCell();
    }

    private void highlightSelectedCell() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            }
        }
        if (selectedRow != -1 && selectedCol != -1) {
            buttons[selectedRow][selectedCol].setBackground(Color.GRAY);
        }
        if (targetRow != -1 && targetCol != -1) {
            buttons[targetRow][targetCol].setBackground(Color.YELLOW); // Hedef hücreyi sarı ile vurgula
        }
    }

    private void moveSelectedCell(int rowOffset, int colOffset) {
        if (selectedRow != -1 && selectedCol != -1) {
            int newRow = selectedRow + rowOffset;
            int newCol = selectedCol + colOffset;
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                selectedRow = newRow;
                selectedCol = newCol;
                highlightSelectedCell();
            }
        }
    }

    private void selectPiece() {
        if (selectedRow != -1 && selectedCol != -1) {
            // Seçilen taşın rengini yeşil yap
            buttons[selectedRow][selectedCol].setBackground(Color.GREEN);
            targetRow = selectedRow; // Taşın seçilen konumunu kaydet
            targetCol = selectedCol; // Taşın seçilen konumunu kaydet
        }
    }

    private void movePiece() {
        if (targetRow != -1 && targetCol != -1) {
            boolean moveSuccessful = makeMove(targetRow, targetCol, selectedRow, selectedCol);
            if (moveSuccessful) {
                updateBoard();
            }
            // Seçimi sıfırla
            targetRow = -1;
            targetCol = -1;
        }
    }

    private void handleButtonClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            if (!buttons[row][col].getText().isEmpty()) {
                selectedRow = row;
                selectedCol = col;
                buttons[row][col].setBackground(Color.GRAY);
            }
        } else {
            boolean moveSuccessful = makeMove(selectedRow, selectedCol, row, col);
            if (moveSuccessful) {
                updateBoard();
            }
            buttons[selectedRow][selectedCol].setBackground((selectedRow + selectedCol) % 2 == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    // Native functions
    public native void initializeBoard();
    public native boolean makeMove(int startRow, int startCol, int endRow, int endCol);
    public native int[][] getBoard();

    private void updateBoard() {
        int[][] board = getBoard();  // get board from c++

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {  // black pieces
                    buttons[i][j].setText("●");
                    buttons[i][j].setFont(font);
                    buttons[i][j].setForeground(Color.BLACK);
                } else if (board[i][j] == 2) {  // white pieces
                    buttons[i][j].setText("●");
                    buttons[i][j].setFont(font);
                    buttons[i][j].setForeground(Color.WHITE);
                } else if (board[i][j] == 3) { // black king
                    buttons[i][j].setText("Ö");
                    buttons[i][j].setForeground(Color.BLACK);
                    buttons[i][j].setFont(font1);
                } else if (board[i][j] == 4) { // white king
                    buttons[i][j].setText("Ö");
                    buttons[i][j].setForeground(Color.WHITE);
                    buttons[i][j].setFont(font1);
                } else {  // empty cell
                    buttons[i][j].setText("");
                }

                buttons[i][j].setFocusable(false);
            }
        }
    }

    static {
        System.loadLibrary("libCheckers_CPP");
    }

    public static void main(String[] args) {
        Main checkersBoard = new Main();
        checkersBoard.setVisible(true);
    }
}