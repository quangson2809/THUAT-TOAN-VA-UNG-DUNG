package Custom;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class IntervalTree1d {
    private Node root;

    private class Node{
        private Interval1d interval;
        private Rectangle2d rectangle;
        private int max;
        private Node left, right;
        private int size;

        public Node(Interval1d interval,Rectangle2d rectangle, int size){
            this.interval = interval;
            this.max = interval.getHigh();
            this.rectangle = rectangle;
            this.left = null;
            this.right = null;
            this.size = size;
        }
    }

    public IntervalTree1d(){
        root = null;
    }

    public int size(){
        return size(root);
    }
    private int size(Node x){
        if (x == null) return 0;
        return x.size;
    }

    public boolean contains(Interval1d interval){
        return contains(root, interval);
    }
    private boolean contains(Node x, Interval1d interval){
        if (x == null) return false;
        return get(interval) != null;
    }

    public Rectangle2d get(Interval1d interval){
        return get(root, interval);
    }
    private Rectangle2d get(Node x, Interval1d interval){
        if (x == null) return null;
        int cmp = interval.compareTo(x.interval);

        if (cmp == 0) return x.rectangle;
        else if (cmp > 0) return get(x.right, interval);
        else return get(x.left,interval);
    }

    public void put(Interval1d interval, Rectangle2d rectangle){
        root = put(root, interval,rectangle);
    }

    private Node put (Node x, Interval1d interval,Rectangle2d rectangle){
        if (x == null ) return new Node(interval,rectangle,1);
        int cmp = interval.compareTo(x.interval);

        if(cmp > 0 ) {
            x.right = put (x.right, interval,rectangle);
        }
        else if (cmp < 0) {
            x.left = put (x.left, interval,rectangle);
        }
        else {
            x.interval = interval;
        }

        int leftMax = (x.left == null) ? Integer.MIN_VALUE : x.left.max;
        int rightMax = (x.right == null) ? Integer.MIN_VALUE : x.right.max;
        x.max = Math.max(x.interval.getHigh(), Math.max(leftMax, rightMax));

        x.size = 1 + size(x.left) + size (x.right);
        return x;
    }
    public Interval1d min(){
        if (root == null) return null;
        return min(root).interval;
    }
    private Node min(Node root){
        if (root.left == null) return root;
        return min(root.left);
    }

    public Interval1d max(){
        if(root == null) return null;
        return max(root).interval;
    }
    private Node max(Node root){
        if(root.right == null) return root;
        return max (root.right);
    }

    public Iterable<Interval1d> intervals(){
        if (root == null) return new Queue<Interval1d>();
        return intervals(min(),max());
    }
    public Iterable<Interval1d> intervals(Interval1d lo, Interval1d hi){
        if(root == null) return null;
        Queue<Interval1d> queue = new Queue<Interval1d>();
        intervals(root, queue,lo,hi);
        return queue;
    }

    public void intervals(Node x, Queue<Interval1d> queue, Interval1d lo, Interval1d hi){
        if(x == null) return;
        int cmpLo = lo.compareTo(x.interval);
        int cmpHi = hi.compareTo(x.interval);

        if(cmpLo < 0 ) intervals(x.left , queue, lo, hi);
        if(cmpHi > 0 ) intervals(x.right, queue, lo, hi);
        if(cmpLo <=0 && cmpHi >=0) queue.enqueue(x.interval);
    }
    public void delete(Interval1d interval) {
        if (interval == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, interval);

        if (root == null ) return;

        int leftMax = (root.left == null) ? Integer.MIN_VALUE : root.left.max;
        int rightMax = (root.right == null) ? Integer.MIN_VALUE : root.right.max;
        root.max = Math.max(root.interval.getHigh(), Math.max(leftMax, rightMax));
    }

    private Node delete(Node x, Interval1d interval) {
        if (x == null) return null;

        int cmp = interval.compareTo(x.interval);
        if      (cmp < 0) x.left  = delete(x.left,  interval);
        else if (cmp > 0) x.right = delete(x.right, interval);
        else {
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable <Interval1d> searchInterSection(Interval1d interval){
        Queue<Interval1d> queue = new Queue<Interval1d>();
        search(root, interval, queue);
        return queue;
    }
    public Iterable<Interval1d> search(Node x, Interval1d interval, Queue<Interval1d> queue){
        if(x== null ) return queue;

        if(interval.intersects(x.interval)){
            queue.enqueue(x.interval);
        }
        if(x.left != null && x.left.max >= interval.getLow()){
            search( x.left, interval, queue);
        }
        if(x.right != null && x.right.interval.getLow() <= interval.getHigh() ){
            search( x.right, interval, queue);
        }

        return queue;
    }
    public static void main(String[] args) {
        IntervalTree1d tree = new IntervalTree1d();
        tree.put(new Interval1d(10, 12), new Rectangle2d(1,5,5,10));
        tree.put(new Interval1d(11, 16), new Rectangle2d(1,11,1,16));
        tree.put(new Interval1d(6, 7), new Rectangle2d(2,6,2,7));
        tree.put(new Interval1d(2, 3), new Rectangle2d(2,2,3,3));
        tree.put(new Interval1d(8, 12), new Rectangle2d(2,8,2,12));
        tree.put(new Interval1d(7, 12), new Rectangle2d(7,7,12,12));
        tree.put(new Interval1d(18, 20), new Rectangle2d(1,18,20,20));
        tree.put(new Interval1d(16, 20), new Rectangle2d(1,16,2,20));
        tree.put(new Interval1d(19, 21), new Rectangle2d(2,19,2,21));

        StdOut.println("===SIZE: " + tree.size());


        for(Interval1d key : tree.intervals()){
            StdOut.print(key);
            StdOut.println( tree.get(key));
        }

        tree.delete(new Interval1d(1,20));
        StdOut.println("===After delete :(1,20) ");
        for(Interval1d key : tree.intervals()){
            StdOut.print(key);
            StdOut.println( tree.get(key));
        }
        Interval1d query = new Interval1d(20,21);
        StdOut.println("===search : "+ query);

        for(Interval1d interval1d: tree.searchInterSection(query)){
            StdOut.println(interval1d);
        }
    }
}

