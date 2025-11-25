package Custom;

public class EventRectangle2d implements  Comparable<EventRectangle2d> {
    private int type; // start =0 , end =1
    private int x;
    private Rectangle2d rectangle;

    public EventRectangle2d(int x, Rectangle2d rectangle){
        this.type = (x == rectangle.getMinX()) ? 0 : 1;
        this.x=x;
        this.rectangle= rectangle;
    }
    public int getType() {
        return type;
    }
    public int getX() {
        return x;
    }
    public Rectangle2d getRectangle() {
        return rectangle;
    }
    public Interval1d getInterval1d() {
        return new Interval1d(rectangle.getMinY(), rectangle.getMaxY());
    }

    @Override
    public int compareTo(EventRectangle2d other) {
        return Integer.compare(this.x, other.x);
    }
}
