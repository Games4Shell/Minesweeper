import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
    
    public static final String	RESET				= "\u001B[0m";

	public static final String	HIGH_INTENSITY		= "\u001B[1m";
	public static final String	LOW_INTENSITY		= "\u001B[2m";

	public static final String	ITALIC				= "\u001B[3m";
	public static final String	UNDERLINE			= "\u001B[4m";
	public static final String	BLINK				= "\u001B[5m";
	public static final String	RAPID_BLINK			= "\u001B[6m";
	public static final String	REVERSE     		= "\u001B[7m";
	public static final String	INVISIBLE_TEXT		= "\u001B[8m";

	public static final String	BLACK				= "\u001B[30m";
	public static final String	RED					= "\u001B[31m";
	public static final String	GREEN				= "\u001B[32m";
	public static final String	YELLOW				= "\u001B[33m";
	public static final String	BLUE				= "\u001B[34m";
	public static final String	MAGENTA				= "\u001B[35m";
	public static final String	CYAN				= "\u001B[36m";
	public static final String	WHITE				= "\u001B[37m";

	public static final String	BACKGROUND_BLACK	= "\u001B[40m";
	public static final String	BACKGROUND_RED		= "\u001B[41m";
	public static final String	BACKGROUND_GREEN	= "\u001B[42m";
	public static final String	BACKGROUND_YELLOW	= "\u001B[43m";
	public static final String	BACKGROUND_BLUE		= "\u001B[44m";
	public static final String	BACKGROUND_MAGENTA	= "\u001B[45m";
	public static final String	BACKGROUND_CYAN		= "\u001B[46m";
	public static final String	BACKGROUND_WHITE	= "\u001B[47m";

    public static void printColor(String toPrint) {
        System.out.print(toPrint);
    }

    public static void printColor(String toPrint, String fontColor) {
        String color = setFontColor(fontColor);
        System.out.print(color + toPrint + RESET);
    }

    public static void printColor(String toPrint, String fontColor, String backColor) {
        String color = setFontColor(fontColor);
        String background = setBackColor(backColor);
        System.out.print(color + background + toPrint + RESET);
    }

    public static void printColor(String toPrint, String fontColor, Pointer pointer) {
        String color = setFontColor(fontColor);
        String pointerColor = setBackColor(pointer.color);
        if (pointer.color.equals(fontColor)){
            System.out.print(pointerColor + REVERSE + toPrint + RESET);
        }
        System.out.print(color + pointerColor + toPrint + RESET);
    }

    
    private static String setBackColor(String fontColor) {
        switch (fontColor) {
            case "black":
                return BACKGROUND_BLACK;
            case "red":
                return BACKGROUND_RED;
            case "green":
                return BACKGROUND_GREEN;
            case "yellow":
                return BACKGROUND_YELLOW;
            case "blue":
                return BACKGROUND_BLUE;
            case "magenta":
                return BACKGROUND_MAGENTA;
            case "cyan":
                return BACKGROUND_CYAN;
            case "white":
                return BACKGROUND_WHITE;
            default:
                System.err.println("The color selected is not available. The colors available are: black, red ,green, yellow, blue, magenta, cyan and white.");
                System.exit(1);
                return "error";
        }
        
    }

    private static String setFontColor(String fontColor) {
        switch (fontColor) {
            case "black":
                return BLACK;
            case "red":
                return RED;
            case "green":
                return GREEN;
            case "yellow":
                return YELLOW;
            case "blue":
                return BLUE;
            case "magenta":
                return MAGENTA;
            case "cyan":
                return CYAN;
            case "white":
                return WHITE;
            default:
                System.err.println("The color selected is not available. The colors available are: black, red ,green, yellow, blue, magenta, cyan and white.");
                System.exit(1);
                return "error";
        }
        
    }


    public static String readLine(String requestMessage){
        Console console = System.console();
        String input = console.readLine("Choose your move : ");
        return input;
    }

    public static void executeCommand(String command){
        ProcessBuilder processBuilder = new ProcessBuilder();
	    processBuilder.command("sh", "-c", command);
        try {
           Process process = processBuilder.start(); 
           printResults(process);
        } catch(IOException e){
            System.exit(1);
        }
        
    }

    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        executeCommand("clear");
    }
}
