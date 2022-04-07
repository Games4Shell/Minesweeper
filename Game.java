public class Game {

    private boolean[][] isVisible;
    private boolean[][] mineBoard;
    private int[][] board;
    public Board actualBoard;
    public boolean isLost = false;

    Game(int[] firstStrike, String difficulty) {
        setBoard(difficulty, firstStrike);
    }

    private void setBoard(String difficulty, int[] firstStrike) {
        Board tablero = new Board(difficulty, firstStrike);
        this.actualBoard = tablero;
        this.board = actualBoard.getBoard();
        this.mineBoard = actualBoard.getMineBoard();
        setisVisible();
    }

    public int totalMines() {
        return this.actualBoard.minesNumber;
    }

    private void setisVisible() {
        this.isVisible = new boolean[this.board.length][this.board[0].length];
        for (int i = 0; i < this.isVisible.length; i++) {
            for (int j = 0; j < this.isVisible[0].length; j++) {
                isVisible[i][j] = false;
            }

        }
    }

    public boolean isGameLost() {
        return isLost;
    }

    private void gameLost() {
        this.isLost = true;
    }

    public void Strike(int[] pos) {
        int row = pos[0];
        int col = pos [1];
        isVisible[row][col] = true;
        if (mineBoard[row][col]) {
            gameLost();
        }
        if (board[row][col]==0){
            isAdjacentTo0(row, col);
        }
    }

    private void isAdjacentTo0(int row, int col) {
        int[] edge_x = actualBoard.setEdge(row);
        int[] edge_y = actualBoard.setEdge(col);
        for (int x = edge_x[0]; x <= edge_x[1]; x++) {
            for (int y = edge_y[0]; y <= edge_y[1]; y++) {
                if (x == 0 && y == 0 || isVisible[row + x][col + y] || mineBoard[row+x][col+y]) {
                    continue;
                }
                isVisible[row + x][col + y] = true;
                if (this.board[row + x][col + y] == 0) {
                    isAdjacentTo0(row + x, col + y);
                }
            }
        }
    }

    public void printGameBoard() {
        actualBoard.printBoard();
    }

    public boolean[][] getVisibilityBoard() {
        return this.isVisible;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public boolean isGameSolved() {
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[0].length; y++) {
                if (this.mineBoard[x][y] == this.isVisible[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
}
