package Custom;

import java.util.Objects;
import java.util.Scanner;

public class Point2d implements Comparable<Point2d> {

    private int x,y;

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


    @Override
    public String toString() {
        return "Custom.Point2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public int get(int index){
        if(index ==0) return this.x;
        else return this.y;
    }

    @Override
    public int compareTo(Point2d that) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point2d point2d = (Point2d) o;
        return x == point2d.x && y == point2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public  double distanceTo(Point2d that){
        int dx = this.x - that.x;
        int dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
