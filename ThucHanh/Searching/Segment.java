import java.awt.geom.Point2D;

public class Segment {
    public enum  SegmentType {  HORIZONTAL, VERTICAL }


    private Point2d left,right;
    private SegmentType type;

    public Segment(Point2d left, Point2d right) {
        this.left = left;
        this.right = right;
        if(left.getX() == right.getX())
            this.setType(SegmentType.VERTICAL);
        else
            this.setType(SegmentType.HORIZONTAL);
    }
    public int getY(){
        return left.getY();
    }

    public Point2d getLeft() {
        return left;
    }

    public Point2d getRight() {
        return right;
    }

    public SegmentType getType() {
        return type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "left=" + left +
                ", right=" + right +
                ", type=" + type +
                '}';
    }

    public static void main(String[] args) {
        Point2d left1 = new Point2d(1,2);
        Point2d right1 = new Point2d(2,2);
        Segment s1 = new Segment(left1 ,right1);
        System.out.println(s1);

        Point2d left2 = new Point2d(1,2);
        Point2d right2 = new Point2d(1,3);
        Segment s2 = new Segment(left2 ,right2);
        System.out.println(s2);
    }
}
