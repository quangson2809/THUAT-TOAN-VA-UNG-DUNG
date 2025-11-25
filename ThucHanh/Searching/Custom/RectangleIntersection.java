package Custom;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class RectangleIntersection {
    private IntervalTree1d intervalTree;
    private MinPQ <EventRectangle2d> eventPQ;

    public RectangleIntersection() {
        intervalTree = new IntervalTree1d();
        eventPQ = new MinPQ<>();
    }
    public RectangleIntersection(String filename) {
        intervalTree = new IntervalTree1d();
        eventPQ = new MinPQ<>();

        Scanner scanner = null;
        try{
            scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Rectangle2d rectangle = new Rectangle2d(line);
                EventRectangle2d startEvent = new EventRectangle2d(rectangle.getMinX(), rectangle);
                eventPQ.insert(startEvent);
                EventRectangle2d endEvent = new EventRectangle2d(rectangle.getMaxX(),rectangle);
                eventPQ.insert(endEvent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            scanner.close();
        }
    }
    public void sweep() {
        while (!eventPQ.isEmpty()) {
            EventRectangle2d event = eventPQ.delMin();
            Interval1d interval = event.getInterval1d();
            if(event.getType() == 0) {
                intervalTree.put(interval, event.getRectangle());
            } else {
                intervalTree.delete(interval);
                checkIntersection(event);
            }
        }
    }
    public void checkIntersection(EventRectangle2d event) {
        // Implementation for checking intersections at the given event
        if(intervalTree.size() == 0 ) return;

        Queue<Interval1d> list = (Queue<Interval1d>)intervalTree.searchInterSection(event.getInterval1d());

        if(list.isEmpty()) return;
        else StdOut.println();

        for(Interval1d interval : list){
            StdOut.println( event.getRectangle()+" -intersect- " + intervalTree.get(interval));
        }
    }

    public static void main(String[] args) {
        RectangleIntersection rectangleIntersection = new RectangleIntersection("Custom/rectangles.txt");
        rectangleIntersection.sweep();
    }
}
