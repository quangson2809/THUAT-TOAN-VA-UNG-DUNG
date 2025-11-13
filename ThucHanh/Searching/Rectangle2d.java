public class Rectangle2d {
//    private int maxX;
//    private int maxY;
//    private int minX;
//    private int minY;

    private Point2d LeftButton;
    private Point2d RightTop;

    public Rectangle2d(int maxX, int minX, int maxY, int minY){
//        this.maxX = maxX;
//        this.minX=minX;
//        this.maxY = maxY;
//        this.minY = minY;
        this.LeftButton = new Point2d(minX,minY);
        this.RightTop = new Point2d(maxX,maxY);
    }

}
