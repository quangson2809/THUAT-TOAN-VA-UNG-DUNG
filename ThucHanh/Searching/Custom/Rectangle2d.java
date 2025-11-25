package Custom;

import edu.princeton.cs.algs4.StdOut;

public class Rectangle2d {
//    private int maxX;
//    private int maxY;
//    private int minX;
//    private int minY;

    private Point2d LB;//LeftButton
    private Point2d RT;//RightTop

    public Rectangle2d(int minX, int minY, int maxX, int maxY){
//        this.maxX = maxX;
//        this.minX=minX;
//        this.maxY = maxY;
//        this.minY = minY;
        if(minX > maxX || minY > maxY){
            throw new IllegalArgumentException("sai toaj độ hình chữ nhật: " + minX + " " + minY + " " + maxX + " " + maxY);
        }
        if(minX == maxX || minY == maxY){
            throw new IllegalArgumentException("cạnh hình chữ nhật không the là 1 điểm: " + minX + " " + minY + " " + maxX + " " + maxY);
        }
        this.LB = new Point2d(minX,minY);
        this.RT = new Point2d(maxX,maxY);
    }
    public Rectangle2d (String str){
        String[] parts = str.split(" ");
        int minX = Integer.parseInt(parts[0]);
        int minY = Integer.parseInt(parts[1]);
        int maxX = Integer.parseInt(parts[2]);
        int maxY = Integer.parseInt(parts[3]);
        if(minX > maxX || minY > maxY){
            throw new IllegalArgumentException("sai toaj độ hình chữ nhật: "+ minX + " " + minY + " " + maxX + " " + maxY );
        }
        if(minX == maxX || minY == maxY){
            throw new IllegalArgumentException("cạnh hình chữ nhật không the là 1 điểm: " + minX + " " + minY + " " + maxX + " " + maxY);
        }
        this.LB = new Point2d(minX,minY);
        this.RT = new Point2d(maxX,maxY);
    }

    public Point2d getRT() {
        return RT;
    }

    public Point2d getLB() {
        return LB;
    }
    public int getMinX(){
        return LB.getX();
    }
    public int getMinY() {
        return LB.getY();
    }
    public int getMaxX(){
        return RT.getX();
    }
    public int getMaxY() {
        return RT.getY();
    }

    @Override
    public String toString() {
        return "Rectangle2d{" +"minX=" + getMinX() +
                ", maxX=" + getMaxX() +
                ", minY=" + getMinY() +
                ", maxY=" + getMaxY() +
                '}';
    }

    public static void main(String[] args) {
        Rectangle2d r1 = new Rectangle2d(1,2,3,4);
        StdOut.println(r1);
        Rectangle2d r2 = new Rectangle2d("5 6 6 8");
        StdOut.println(r2);
    }
}
