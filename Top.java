import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Top {

    private static final String defaultName = "0";
    private static final String defaultDate = "0000/00/00";
    private static final String defaultTime = "00:00:00";
    private static final String defaultPuntuaction = "999999";

    private static final int MaxTopSize = 500;
    private static String[] top = new String[MaxTopSize*4];
    private static int[] topValues = new int[MaxTopSize];

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String HIGH_INTENSITY = "\u001B[1m";

    private static void setTops(){
        for (int i=0; i<topValues.length;i++){
            topValues[i]=0;
            for (int j=0; j<4;j++){
                top[i*4+j]="0";
            }
        }
    }

    private static void readTop(String difficulty){
        try{
        Scanner scanner = new Scanner(new File("top"+difficulty+".txt"));
        scanner.useDelimiter(",");
        int topIndex = 0;
        while(scanner.hasNext()){
            top[topIndex]=scanner.next();
            topIndex++;
        }
        if(topIndex==0){
            setDefaultValues();
        }
        scanner.close();
        } catch (FileNotFoundException e){
            System.err.println("Error: top"+difficulty+".txt not found");
            System.exit(1);
        }
    }

    private static void setDefaultValues(){
        top[0] = defaultName;
        top[1] = defaultDate;
        top[2] = defaultTime;
        top[3] = defaultPuntuaction;
    }

    private static void getTopValues(){
        for (int i=0;i<topValues.length;i++ ){
            topValues[i] = Integer.parseInt(top[3+i*4]);
        }
    }

    private static int introduceInTop(String name, String dateNow, int puntuaction) {
        for (int i=0; i<topValues.length;i++){
            if (puntuaction<topValues[i]){
                move(name,dateNow,puntuaction,i);
                return i+1;
            }
        }
        return 0;
    }

    private static void move(String name, String dateNow, int puntuaction, int index) {
        if (index<topValues.length-1){
            String oldName = top[index*4];
            String oldDate = top[1+index*4];
            int oldPuntuaction = topValues[index];
            top[index*4] = name;
            top[1+index*4] = dateNow;
            top[2+index*4] = calculateTime(puntuaction);
            top[3+index*4] = Integer.toString(puntuaction);
            topValues[index] = puntuaction;
            move(oldName,oldDate,oldPuntuaction,index+1);
        } else {
            top[index*4] = name;
            top[1+index*4] = dateNow;
            top[2+index*4] = calculateTime(puntuaction);
            top[3+index*4] = Integer.toString(puntuaction);
            topValues[index] = puntuaction;
        }
    }

    private static String calculateTime(int puntuaction) {
        int hour = puntuaction / 3600;
        int min = (puntuaction%3600) / 60;
        int sec = puntuaction % 60;
        String finalTime = setTime(hour)+":"+setTime(min)+":"+setTime(sec);
        return finalTime;
    }

    public static String setTime(int time){
        if (time<10){
            return "0"+time;
        }    
        return ""+time;
    }

    private static void printTop(int topVisible) {
        System.out.println();
        System.out.println(HIGH_INTENSITY+"Top\tName\t\tDate\t\tTime"+ANSI_RESET);
        for(int i=0; i<topVisible;i++){
            if (!top[i*4].equals("0")){
                if (top[i*4].length()>8){
                    System.out.print((i+1)+".\t"+top[i*4]+"\t");
                } else {
                    System.out.print((i+1)+".\t"+top[i*4]+"\t\t");
                }
                
                System.out.print(top[i*4+1]+"\t");
                System.out.print(top[i*4+2]); 
            }
            System.out.println();
            
        }
    }

    private static void rewriteTop(String difficulty){
        try {
            FileWriter myWriter = new FileWriter("top"+difficulty+".txt");
            for (int i=0; i< top.length;i++){
                myWriter.write(top[i]+",");
            }
            myWriter.close();
          } catch (IOException e) {
            System.out.println("Could not rewrite top"+difficulty+".txt");
            System.exit(1);
          }
    }

    private static void printTopPosition(int positionInTop){
        if (positionInTop==0){
            System.out.println("You are out of the top");
        }
        System.out.println("You are top "+positionInTop);
    }

    public static void inTop(String name, String dateNow, int puntuaction,String difficulty){
        setTops();
        readTop(difficulty);
        getTopValues();
        int positionInTop = introduceInTop(name,dateNow,puntuaction);
        printTopPosition(positionInTop);
        rewriteTop(difficulty);
    }

    private static void checkDifficulty(String difficulty){
        if (!difficulty.equals("Easy") && !difficulty.equals("Medium") && !difficulty.equals("Hard")){
            System.err.println("The difficulties available are: Easy, Medium and Hard.");
            System.exit(1);
        }
    }

    private static int setVisibleTop(){
        Console console = System.console();
        String inputTopSize = console.readLine("Choose the size of the top displayed : ");
        int topSize =0;
        try{
            topSize = Integer.parseInt(inputTopSize);
        } catch (Exception e) {
            System.err.println("Introduce a number");
            System.exit(1);
        }
        if ( topSize <= 0 || topSize>=MaxTopSize){
            System.err.println("Introduce a number between 1-"+MaxTopSize);
            System.exit(1);
        }
        return topSize;
    }


    public static void main(String[] args) {
        int topSize = setVisibleTop();
        String difficulty = HumanPlayer.setDifficulty();
        checkDifficulty(difficulty);
        setTops();
        readTop(difficulty);
        printTop(topSize);
    }
    
}
