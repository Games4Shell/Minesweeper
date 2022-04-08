public class Game {

    public String[] availableDiffiiculties = {"Easy","Medium","Hard"};
    private int[] minesInDifficulties = {10,40,99};
    private int[][] availableBoardSizes ={{8,8},{16,16},{24,24}};

    private boolean[][] isVisible;
    private boolean[][] mineBoard;
    private int[][] board;
    private int[] strikePos;
    public Board actualBoard;
    public boolean isLost = false;
    public int minesNumber;
    private int gameScore;

    private final String requestStrikeMessage = "Choose your move : ";
    private final String badStrikeInput = "The imput should be : row col";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BACKGROUND_RED	= "\u001B[41m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    Game(String difficulty, String playerName, boolean printInShell) {
        setBoardSize(difficulty);
        requestFirstStrike(printInShell);
        String startTime = Top.setTime();
        printBoard(printInShell);
        setBoard();
        while ( !isGameLost() && !isGameSolved()){
            requestStrike(printInShell);
        }
        if (isGameSolved()){
            printWinMessage(printInShell);
            String winTime = Top.setTime();
            this.gameScore = Top.calculateScore(startTime, winTime);
            printScore(printInShell);
            Top.inTop(playerName, gameScore, difficulty, printInShell);
            finish();
        }
        if (isGameLost()){
            printLoseMessage(printInShell);
            finish();
        }
    }

    private void setBoardSize(String difficulty) {
        for (int i=0; i<this.availableDiffiiculties.length; i++){
            if (availableDiffiiculties[i].equals(difficulty)){
                this.board = new int[availableBoardSizes[i][0]][availableBoardSizes[i][1]];
                this.mineBoard = new boolean[availableBoardSizes[i][0]][availableBoardSizes[i][1]];
                this.minesNumber = minesInDifficulties[i];
            }
        }
    }

    private void printBoard(boolean printInShell) {
        if (printInShell){
            printInShell();
        } else {
            returnToBot();
        }
    }

    private void returnToBot() {
    }

    private void printInShell(){
        System.out.println();
        for (int i = 0; i < this.board.length; i++) {
            if (i==0){
                System.out.print("   \\Col\t");
                for (int j = 0; j < this.board[0].length; j++) {
                    if (j+1<10){
                        System.out.print(" "+(j+1)+" ");
                    } else{
                        System.out.print((j+1)+" ");
                    }
                }
                System.out.println();
                System.out.println(" Row\\\t");
            }
            System.out.print("   "+(i+1)+"\t");
            for (int j = 0; j < board[0].length; j++) {
                if (this.isVisible[i][j]) {
                    printChar(board[i][j]);
                    continue;
                }
                System.out.print(" # ");
            }
            System.out.println();
        }
    }

    private void printChar(int value) {
        switch (value) {
            case 0:
                System.out.print("   ");
                break;
            case 1:
                System.out.print(ANSI_WHITE+" 1 "+ANSI_RESET);
                break;
            case 2:
                System.out.print(ANSI_CYAN+" 2 "+ANSI_RESET);
                break;
            case 3:
                System.out.print(ANSI_RED+" 3 "+ANSI_RESET);
                break;
            case 4:
                System.out.print(ANSI_PURPLE+" 4 "+ANSI_RESET);
                break;
            case 5:
                System.out.print(ANSI_GREEN+" 5 "+ANSI_RESET);
                break;
            case 6:
                System.out.print(ANSI_BLUE+" 6 "+ANSI_RESET);
                break;
            case 7:
                System.out.print(ANSI_YELLOW+" 7 "+ANSI_RESET);
                break;
            case 8:
                System.out.print(ANSI_BLACK+" 8 "+ANSI_RESET);
                break;
            case 9:
                System.out.print(" "+ANSI_BACKGROUND_RED +" "+ANSI_RESET+" ");
                break;
        
            default:
                break;
        }
        
    }

    private void requestStrike(boolean inShell){
        if (inShell){
            requestStrikeShell();
        } else {
            requestStrikeBot();
        }
    }

    private void requestFirstStrike(boolean inShell) {
        requestStrike(inShell);
    }

    private void requestStrikeShell() {
        int[] strikePos = new int[2];
        String input = Terminal.readLine(requestStrikeMessage);
        String[] inputArray = input.split(" ");
        if (inputArray.length > 2){
            System.err.println(badStrikeInput);
            requestStrikeShell();
        }
        for (int i=0; i<2;i++){
            try{
                strikePos[i]= -1 +Integer.parseInt(inputArray[i]);
            }catch(Exception e){
                System.err.println(badStrikeInput);
                requestStrikeShell();
                break;
            }
        }
        isInBoard(strikePos);
    }

    private void requestStrikeBot() {
    }

    private void isInBoard(int[] strikePos){
        if(this.board.length<=strikePos[0] || this.board[0].length<=strikePos[1] || strikePos[0]<0 || strikePos[1]<0){
            System.err.println("The row or col is out of the board. Please insert new coordinates");
            requestStrikeShell();
        }
        this.strikePos = strikePos;
    }

    private void setBoard() {
        Board tablero = new Board(this.board, this.strikePos);
        this.actualBoard = tablero;
        this.board = actualBoard.getBoard();
        this.mineBoard = actualBoard.getMineBoard();
        setisVisible();
    }

    private void setisVisible() {
        this.isVisible = new boolean[this.board.length][this.board[0].length];
        for (int i = 0; i < this.isVisible.length; i++) {
            for (int j = 0; j < this.isVisible[0].length; j++) {
                isVisible[i][j] = false;
            }

        }
    }

    private boolean isGameLost() {
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

    private boolean isGameSolved() {
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[0].length; y++) {
                if (this.mineBoard[x][y] == this.isVisible[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printScore(boolean printInShell){
        if (printInShell){
            printScoreInShell();
        }
    }

    private void printScoreInShell() {
        int hour = this.gameScore / 3600;
        int min = (this.gameScore%3600) / 60;
        int sec = this.gameScore % 60;
        System.out.println("Your time is: "+Top.setTime(hour)+":"+Top.setTime(min)+":"+Top.setTime(sec));
    }

    private void printWinMessage(boolean printInShell){
        if(printInShell){
            printWinMessageShell();
        }
    }

    private void printWinMessageShell() {
        System.out.println("WIN");
    }

    private void printLoseMessage(boolean printInShell){
        if(printInShell){
            printLoseMessageShell();
        }
    }

    private void printLoseMessageShell() {
        System.out.println("BOOM");
    }

    private void finish(){
        System.exit(0);
    }

}