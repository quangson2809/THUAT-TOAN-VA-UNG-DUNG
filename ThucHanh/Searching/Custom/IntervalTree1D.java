package Custom;

import edu.princeton.cs.algs4.Queue;

import java.util.LinkedList;

public class IntervalTree1D {
    private Node root;

    private class Node{
        private Interval1D interval;
        private int max;
        private Node left, right;
        private int size;

        public Node(Interval1D interval, int size){
            this.interval = interval;
            this.max = interval.getHigh();
            this.left = null;
            this.right = null;
            this.size = size;
        }
    }

    public IntervalTree1D(){
        root = null;
    }

    public int size(){
        return size(root);
    }
    private int size(Node x){
        if (x == null) return 0;
        return x.size;
    }

    public boolean contains(Interval1D interval){
        return contains(root, interval);
    }
    private boolean contains(Node x, Interval1D interval){
        if (x == null) return false;
        return get(interval) >0;
    }

    public int get(Interval1D interval){
        return get(root, interval);
    }
    private int get(Node x, Interval1D interval){
        if (x == null) return -1;
        int cmp = interval.compareTo(x.interval);

        if (cmp == 0) return x.max;
        else if (cmp > 0) return get(x.right, interval);
        else return get(x.left,interval);
    }

    public void put(Interval1D interval){
        root = put(root, interval);
    }

    private Node put (Node x, Interval1D interval){
        if (x == null ) return new Node(interval,1);
        int cmp = interval.compareTo(x.interval);

        if(cmp > 0 ) {
            x.right = put (x.right, interval);
        }
        else if (cmp < 0) {
            x.left = put (x.left, interval);
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
    public Interval1D min(){
        if (root == null) return null;
        return min(root).interval;
    }
    private Node min(Node root){
        if (root.left == null) return root;
        return min(root.left);
    }

    public Interval1D max(){
        if(root == null) return null;
        return max(root).interval;
    }
    private Node max(Node root){
        if(root.right == null) return root;
        return max (root.right);
    }

    public Iterable<Interval1D> intervals(){
        if (root == null) return new Queue<Interval1D>();
        return intervals(min(),max());
    }
    public Iterable<Interval1D> intervals(Interval1D lo, Interval1D hi){
        if(root == null) return null;
        Queue<Interval1D> queue = new Queue<Interval1D>();
        intervals(root, queue,lo,hi);
        return queue;
    }

    public void intervals(Node x, Queue<Interval1D> queue,Interval1D lo, Interval1D hi){
        if(x == null) return;
        int cmpLo = lo.compareTo(x.interval);
        int cmpHi = hi.compareTo(x.interval);

        if(cmpLo < 0 ) intervals(x.left , queue, lo, hi);
        if(cmpHi > 0 ) intervals(x.right, queue, lo, hi);
        if(cmpLo <=0 && cmpHi >=0) queue.enqueue(x.interval);
    }

    public static void main(String[] args) {
        IntervalTree1D tree = new IntervalTree1D();
        tree.put(new Interval1D(5, 10));
        tree.put(new Interval1D(1, 20));
        tree.put(new Interval1D(25, 30));

        for(Interval1D key : tree.intervals()){
            System.out.print(key);
            System.out.println(" Max: " + tree.get(key));
        }
    }
}

