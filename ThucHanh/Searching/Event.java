public class Event implements Comparable<Event> {

    public enum EventType { START, END }

    private EventType type;
    private int x;
    private Segment segment;

    public Event(int x, EventType type, Segment segment){
        this.x=x;
        this.type=type;
        this.segment= segment;
    }

    public EventType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public Segment getSegment() {
        return segment;
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.x, other.x);
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", x=" + x +
                ", segment=" + segment +
                '}';
    }
}
