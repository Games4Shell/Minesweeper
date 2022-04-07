import java.util.Random;

public class Board {

    private int[][] board;
    private boolean[][] mineBoard;
    public int minesNumber;
    private int mine = 9;
    private int[][] initialSpace = new int[2][2];
    private int firstStrikeCleanSize;
    private int[] firstStrike;

    Board(String difficulty, int[] firstStrike) {
        this.firstStrike = firstStrike;
        createBoard(difficulty);
    }

    private void createBoard(String difficulty) {
        setBoardSize(difficulty);
        setInitialSpace();
        setBlankBoard();
        setMines();
        removeInitialSpace();
    }

    private void removeInitialSpace() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] >= 10) {
                    this.board[i][j] -= 10;
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(this.board[i][j] + " ");
                }

            }
            System.out.println();
        }
    }

    private void setBoardSize(String difficulty) {
        if (difficulty.equals("Easy")) {
            this.board = new int[8][8];
            this.mineBoard = new boolean[8][8];
            this.minesNumber = 10;
            this.firstStrikeCleanSize = 2;
        }
        if (difficulty.equals("Medium")) {
            this.board = new int[16][16];
            this.mineBoard = new boolean[16][16];
            this.minesNumber = 40;
            this.firstStrikeCleanSize = 3;
        }
        if (difficulty.equals("Hard")) {
            this.board = new int[24][24];
            this.mineBoard = new boolean[24][24];
            this.minesNumber = 99;
            this.firstStrikeCleanSize = 4;
        }
    }

    private void setInitialSpace() {
        this.initialSpace[0][0] = this.firstStrike[0] - this.firstStrikeCleanSize - 1;
        this.initialSpace[0][1] = this.firstStrike[0] + this.firstStrikeCleanSize + 1;
        this.initialSpace[1][0] = this.firstStrike[1] + this.firstStrikeCleanSize + 1;
        this.initialSpace[1][1] = this.firstStrike[1] - this.firstStrikeCleanSize - 1;
    }

    private boolean isInInitialSpace(int row, int col) {
        boolean isRow = (this.initialSpace[0][0] < row) && (row < this.initialSpace[0][1]);
        boolean isCol = (this.initialSpace[1][0] > col) && (col > this.initialSpace[1][1]);
        if (isRow && isCol) {
            return true;
        }
        return false;
    }

    private void setBlankBoard() {
        for (int i = 0; i<this.board.length; i++) {
            for (int j = 0;j<this.board.length; j++) {
                mineBoard[i][j] = false;
                if (isInInitialSpace(i, j)) {
                    board[i][j] = 10;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    private void setMines() {
        Random randomNumber = new Random();
        for (int i = 0; i < this.minesNumber; i++) {
            int col = randomNumber.nextInt(this.board[0].length);
            int row = randomNumber.nextInt(this.board.length);
            if (board[row][col] < 9) {
                board[row][col] = this.mine;
                mineBoard[row][col] = true;
                setProximityTiles(row, col);
            } else {
                i--;
            }
        }
    }

    public boolean isMine(int row, int col) {
        if (board[row][col] == this.mine) {
            return true;
        }
        return false;
    }

    private void setProximityTiles(int row, int col) {
        int[] edge_x = setEdge(row);
        int[] edge_y = setEdge(col);
        for (int x = edge_x[0]; x <= edge_x[1]; x++) {
            for (int y = edge_y[0]; y <= edge_y[1]; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                setTile(row + x, col + y);
            }
        }
    }

    public int[] setEdge(int pos) {
        int[] edge = { -1, 1 };
        if (pos == 0) {
            edge[0]++;
        }
        if (pos == this.board.length - 1) {
            edge[1]--;
        }
        return edge;
    }

    private void setTile(int row, int col) {
        if (board[row][col] != 9) {
            board[row][col] += 1;
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public boolean[][] getMineBoard() {
        return this.mineBoard;
    }

}
