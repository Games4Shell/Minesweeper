import java.io.Console;

public class Terminal {
    
    public static String readLine(String requestMessage){
        Console console = System.console();
        String input = console.readLine("Choose your move : ");
        return input;
    }

}
