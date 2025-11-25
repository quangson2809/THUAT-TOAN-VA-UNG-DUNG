package Custom;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SegmentIntersection {
    private BST<Integer, Segment> st_y;
    private MinPQ<EventSegment> pq_x;
    private List<Point2d> intersectionPoints;

    public SegmentIntersection() {}

    public SegmentIntersection(String filename) throws FileNotFoundException {
        st_y = new BST<>();
        pq_x = new MinPQ<>();
        intersectionPoints = new ArrayList<>();

        Scanner scanner = new Scanner(new File(filename));

        try {
            while (scanner.hasNextLine()){
                String str = scanner.nextLine();
                if (str.startsWith("#")) continue;
                String [] points = str.split(",");

                Point2d start = new Point2d(points[0]);
                Point2d end = new Point2d(points[1]);

                Segment segment = new Segment(start,end);

                EventSegment eventstart = new EventSegment(start.getX(), EventSegment.EventType.START,segment);
                pq_x.insert(eventstart);

                //loại bỏ điểm end của đoạn doọc
                if (start.getX() == end.getX())
                    continue;

                EventSegment eventend = new EventSegment(end.getX(), EventSegment.EventType.END, segment);
                pq_x.insert(eventend);
            }
        }catch (Exception e){
            StdOut.println(e.getMessage());
        }
        finally {
            scanner.close();
        }

    }

    public void sweep(){
        for(EventSegment e : pq_x){
            //kiểm tra là đoạn dọc
            if(e.getSegment().getType() == Segment.SegmentType.VERTICAL){
                checkIntersection(e);
            }
            //là đoạn ngang
            else if(e.getSegment().getType() == Segment.SegmentType.HORIZONTAL){
                Segment segment =e.getSegment();
                int y = segment.getY();
                //là điểm cuôis của đoạn
                if(e.getType() == EventSegment.EventType.END){
                    st_y.delete(y);

                }
                //là điểm đầu
                else{
                    st_y.put(y,segment);
                }
            }
        }
    }
    private void checkIntersection(EventSegment e){

        if(st_y.isEmpty()) return;

        for(int y : st_y.keys()){
            Segment segment1 = st_y.get(y);
            int x = e.getX();
            Segment segment2 = e.getSegment();
            if(x > segment1.getRight().getX())
                continue;
            if(e.getSegment().getLeft().getY() <= y && e.getSegment().getRight().getY() >= y){
                Point2d point = new Point2d(x,y);
                StdOut.println(point + "\n-" + segment1 + "\n-" + segment2);

                intersectionPoints.add(point);
            }

        }
    }
    public void Show(){
        StdOut.println("size of intersection Points : " + intersectionPoints.size());
        for(Point2d p : intersectionPoints){
            StdOut.println(p);
        }
    }
    public static  void main(String[] args) {
        try {
            SegmentIntersection si = new SegmentIntersection("Custom/segments.txt");
            StdOut.println("size of events : "+ si.pq_x.size());
            StdOut.println("min of events : "+ si.pq_x.min());
            si.sweep();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
