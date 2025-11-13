import java.util.Scanner;

public class Point2d {
    private int x,y;
    private boolean isleft;

    public void setIsleft(boolean isleft) {
        this.isleft = isleft;
    }

    public Point2d(String str) throws Exception {
        Scanner strScanner = new Scanner(str);
        if(strScanner.hasNextInt()){
            this.x = strScanner.nextInt();
            this.y = strScanner.nextInt();
            strScanner.close();
        }
        else throw new Exception("error structure an new point2d");
    }

    public Point2d(int x, int y){
        this.x= x;
        this.y= y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean Isleft() {
        return isleft;
    }

    @Override
    public String toString() {
        return "Point2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
