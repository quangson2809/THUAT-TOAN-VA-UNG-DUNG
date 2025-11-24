package Custom;

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
        this.LB = new Point2d(minX,minY);
        this.RT = new Point2d(maxX,maxY);
    }

    public Point2d getRT() {
        return RT;
    }

    public Point2d getLB() {
        return LB;
    }
}
