import java.util.Date;
import java.text.SimpleDateFormat;

public class HumanPlayer {
    
    public String name;
    private String difficulty;

    HumanPlayer(String name, String difficulty){
        this.name = name;
        this.difficulty = difficulty;
        new Game(difficulty,name,true);
    }

    public static boolean checkDifficulty(String difficulty){
        if (!difficulty.equals("Easy") && !difficulty.equals("Medium") && !difficulty.equals("Hard")){
            System.err.println("The difficulties available are: Easy, Medium and Hard.");
            return false;
        }
        return true;
    }

    private static String setName(){
        String inputName = Terminal.readLine("Choose your name : ");
        String name = inputName;
        if (name.length()>15){
            System.err.println("The name introduced is too long. The name length must be between 1-15 characters");
            name = setName();
        }
        return name;
    }

    public static String setDifficulty(){
        String difficulty = Terminal.readLine("Choose the difficulty : ");
        if(!checkDifficulty(difficulty)){
            difficulty = setDifficulty();
        }
        return difficulty;
    }

    public static void main(String[] args) {
        String name=setName();
        String difficulty = setDifficulty();
        HumanPlayer player = new HumanPlayer(name,difficulty);
    }
}


