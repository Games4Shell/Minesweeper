public class Pointer {
    public int[] position = {0,0};
    public String color;

    Pointer(String color){
        this.color = color;
    }

    public void moveTo(int x, int y) {
        this.position[0]=x;
        this.position[1]=y;
    }

}
