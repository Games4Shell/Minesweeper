import java.util.Random;

public class Board {

    private int[][] board;
    private boolean[][] mineBoard;
    public int minesNumber;
    private int mine = 9;
    private int[][] initialSpace = new int[2][2];

    Board(int[][] blankBoard, int[] firstStrike) {
        this.board = blankBoard;
        setInitialSpace(firstStrike);
        setBlankBoard();
        setMines();
        removeInitialSpace();
    }

    private void setInitialSpace(int[] firstStrike) {
        int firstStrikeCleanSize = this.board.length / 4;
        this.initialSpace[0][0] = firstStrike[0] - firstStrikeCleanSize - 1;
        this.initialSpace[0][1] = firstStrike[0] + firstStrikeCleanSize + 1;
        this.initialSpace[1][0] = firstStrike[1] + firstStrikeCleanSize + 1;
        this.initialSpace[1][1] = firstStrike[1] - firstStrikeCleanSize - 1;
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

    private void removeInitialSpace() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] >= 10) {
                    this.board[i][j] -= 10;
                }
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public boolean[][] getMineBoard() {
        return this.mineBoard;
    }

}
