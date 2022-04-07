import java.io.Console;
import java.util.Date;
import java.text.SimpleDateFormat;

public class HumanPlayer {
    
    public String name;
    private String difficulty;
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

    HumanPlayer(String name, String difficulty){
        this.name = name;
        this.difficulty = difficulty;
    }

    public static boolean checkDifficulty(String difficulty){
        if (!difficulty.equals("Easy") && !difficulty.equals("Medium") && !difficulty.equals("Hard")){
            System.err.println("The difficulties available are: Easy, Medium and Hard.");
            return false;
        }
        return true;
    }

    private void Play() {
        blankBoard();
        int[] previewStrike = {0,0};
        Game preview = new Game(previewStrike,this.difficulty);
        int[] firstStrike = doFirstStrike(preview);
        Game game = new Game(firstStrike,this.difficulty);
		game.Strike(firstStrike);
        SimpleDateFormat dateStart = new SimpleDateFormat("HH:mm:ss");
        String timeStart = dateStart.format(new Date());
        printGameBoard(game);
		while (!hasWon(game) && !game.isGameLost()) {
			int[] strikePos = readFromTerminal(game);
            game.Strike(strikePos);
            printGameBoard(game);
		}
        if (hasWon(game)){
            System.out.println("WIN");

            SimpleDateFormat dateWin = new SimpleDateFormat("HH:mm:ss");
            String timeWin = dateWin.format(new Date());

            int puntuaction = timeNeeded(timeStart,timeWin);

            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
            String dateNow = date.format(new Date());

            Top.inTop(this.name,dateNow,puntuaction,this.difficulty);
            System.exit(0);
        }
        System.out.println("BOOM");
    }

    private int timeNeeded(String timeStart, String timeWin) {
        String[] timeArrayStart = timeStart.split(":");
        String[] timeArrayWin = timeWin.split(":");
        int totalHour = Integer.parseInt(timeArrayWin[0])-Integer.parseInt(timeArrayStart[0]);
        int totalMin = Integer.parseInt(timeArrayWin[1])-Integer.parseInt(timeArrayStart[1]);
        int totalSec = Integer.parseInt(timeArrayWin[2])-Integer.parseInt(timeArrayStart[2]);
        int totalTime = totalHour* 3600 + totalMin*60 +totalSec;
        if(totalTime==0){totalTime++;}
        int hour = totalTime / 3600;
        int min = (totalTime%3600) / 60;
        int sec = totalTime % 60;
        System.out.println("Your time is: "+Top.setTime(hour)+":"+Top.setTime(min)+":"+Top.setTime(sec));
        return totalTime;
    }

    private void blankBoard() {
        int [][] board = new int[1][1];
        if (difficulty.equals("Easy")) {
            board = new int[8][8];
        }
        if (difficulty.equals("Medium")) {
            board = new int[16][16];
        }
        if (difficulty.equals("Hard")) {
            board = new int[24][24];
        }
        printBlankBoard(board);
    }

    private void printBlankBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (i==0){
                System.out.print("   \\Col\t");
                for (int j = 0; j < board[0].length; j++) {
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
                System.out.print(" # ");
            }
            System.out.println();
        }
    }

    private void printGameBoard(Game game) {
        System.out.println();
        int[][] board = game.getBoard();
        boolean[][] visibilityBoard = game.getVisibilityBoard();
        for (int i = 0; i < board.length; i++) {
            if (i==0){
                System.out.print("   \\Col\t");
                for (int j = 0; j < board[0].length; j++) {
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
                if (visibilityBoard[i][j]) {
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

    private static boolean hasWon(Game game) {
		if (game.isGameSolved()) {
			return true;
		}
		return false;
	}

    private static int[] doFirstStrike(Game game){
        return readFromTerminal(game);
    }

    private static int[] readFromTerminal(Game game){
        int[] strikePos = new int[2];
        Console console = System.console();
        String input = console.readLine("Choose your move : ");
        String[] inputArray = input.split(" ");
        if (inputArray.length > 2){
            System.err.println("The imput should be : row col");
            strikePos = readFromTerminal(game);
        }
        for (int i=0; i<2;i++){
            try{
                strikePos[i]= -1 +Integer.parseInt(inputArray[i]);
            }catch(Exception e){
                System.err.println("The imput should be : row col");
                strikePos = readFromTerminal(game);
                break;
            }
        }
        strikePos = checkOutOfIndex(strikePos, game);
        return strikePos;
    }
    
    private static int[] checkOutOfIndex(int[] strikePos, Game game){
        if(game.getBoard().length<=strikePos[0] || game.getBoard()[0].length<=strikePos[1] || strikePos[0]<0 || strikePos[1]<0){
            System.err.println("The row or col is out of the board. Please insert new coordinates");
            strikePos=readFromTerminal(game);
        }
        return strikePos;
    }

    private static String setName(){
        Console console = System.console();
        String inputName = console.readLine("Choose your name : ");
        String name = inputName;
        if (name.length()>15){
            System.err.println("The name introduced is too long. The name length must be between 1-15 characters");
            name = setName();
        }
        return name;
    }

    public static String setDifficulty(){
        Console console = System.console();
        String inputDifficulty = console.readLine("Choose the difficulty : ");
        String difficulty = inputDifficulty;
        if(!checkDifficulty(difficulty)){
            difficulty = setDifficulty();
        }
        return difficulty;
    }

    public static void main(String[] args) {
        String name=setName();
        String difficulty = setDifficulty();
        HumanPlayer player = new HumanPlayer(name,difficulty);
        player.Play();
    }
}


