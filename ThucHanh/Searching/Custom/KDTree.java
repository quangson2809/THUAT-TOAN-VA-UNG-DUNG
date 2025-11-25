package Custom;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KDTree {
    // Dimension of the space
    private static final int K = 2;

    static class Node {
        Point2d point;
        Node left, right; // Left and right child

        public Node(Point2d point)
        {
            // Copy the point array
            this.point = point;
            // Initialize children
            this.left = this.right = null;
        }
    }

    Node root; // Root of the K-D Tree

    KDTree()
    {
        root = null; // Initialize the root
    }

    // Recursive method to insert a new point in the subtree
    // rooted with the given node
    Node InsertRecursive(Node root, Point2d point, int depth)
    {
        // Base case: If the tree is empty, return a new
        // node
        if (root == null) {
            return new Node(point);
        }

        // Calculate current dimension (cd) of comparison
        int cd = depth % K;

        // Compare the new point with the root on current
        // dimension and decide the left or right subtree
        if (point.get(cd) < root.point.get(cd)) {
            root.left = InsertRecursive(root.left, point, depth + 1);
//            System.out.println("left");
        }
        else {
            root.right = InsertRecursive(root.right, point, depth + 1);
//            System.out.println("right");
        }

        return root;
    }

    // Method to insert a new point in the K-D Tree
    void insert(Point2d point)
    {
        root = InsertRecursive(root, point, 0);
    }

    // Recursive method to search a point in the subtree
    // rooted with the given node
    boolean searchRec(Node root, Point2d point, int depth)
    {
        // Base case: The tree is empty or the point is
        // present at root
        if (root == null) {
            return false;
        }
        if (root.point.equals(point)) {
            return true;
        }

        // Calculate current dimension (cd)
        int cd = depth % K;

        // Compare the point with the root on current
        // dimension and decide the left or right subtree
        if (point.get(cd) < root.point.get(cd)) {
            return searchRec(root.left, point, depth + 1);
        }
        else {
            return searchRec(root.right, point, depth + 1);
        }
    }

    // Method to search a point in the K-D Tree
    boolean search(Point2d point)
    {
        return searchRec(root, point, 0);
    }

    // Recursive method for range search in the subtree
    // rooted with the given node
// Java
// Add these members into `Custom/KDTree.java` (inside the KDTree class)

    private static class Best {
        Point2d point;
        double dist; // squared distance
        Best(Point2d p, double d) { this.point = p; this.dist = d; }
    }

    void nearestNeighbor(Point2d point)
    {
        if (root == null) return ;
        Best best = new Best(null, Double.POSITIVE_INFINITY);
        nearestNeighborRec(root, point, 0, best);
        System.out.println("Taget: " + point + " -->point: " + best.point + " distance " + best.dist);
    }

    private void nearestNeighborRec(Node node, Point2d target, int depth, Best best) {
        if (node == null) return;

        int cd = depth % K;

        // Decide near and far child relative to split
        Node near = (target.get(cd) < node.point.get(cd)) ? node.left : node.right;
        Node far  = (near == node.left) ? node.right : node.left;

        // Explore nearer side first
        if (near != null) nearestNeighborRec(near, target, depth + 1, best);

        // Check current node
        double distSq = target.distanceTo(node.point);
        if (distSq < best.dist) {
            best.dist = distSq;
            best.point = node.point;
        }

        // Distance from target to splitting plane (squared)
        double diff = target.get(cd) - node.point.get(cd);
        double diffSq = diff * diff;

        // Prune far side if split distance is greater than best distance
        if (diffSq <= best.dist) {
            if (far != null) nearestNeighborRec(far, target, depth + 1, best);
        }
    }

    void rangeSearchRec(Node root, Point2d lower, Point2d upper, int depth)
    {
        if (root == null) {
            return ;
        }
        // Check if the point of root is in range
        boolean inside = true;
        for (int i = 0; i < K; i++) {
            if (root.point.get(i) < lower.get(i)
                    || root.point.get(i) > upper.get(i)) {
                inside = false;
                break;
            }
        }

        // If the point is in range, print it
        if (inside) {
            System.out.println(root.point);
        }

        // Calculate current dimension (cd)
        int cd = depth % K;

        // Check subtrees if they can have points within the
        // range
        if (lower.get(cd) <= root.point.get(cd)) {
            rangeSearchRec(root.left, lower, upper,
                    depth + 1);
        }
        if (upper.get(cd) >= root.point.get(cd)) {
            rangeSearchRec(root.right, lower, upper,
                    depth + 1);
        }

    }

    // Method for range search
    void rangeSearch(Point2d lower, Point2d upper)
    {
        rangeSearchRec(root, lower, upper, 0);
    }

    public static void main(String[] args)
    {
        KDTree tree = new KDTree();

        Point2d[] points = {
                new Point2d(3, 6),
                new Point2d(17, 15),
                new Point2d(13, 15),
                new Point2d(6, 12),
                new Point2d(9, 1),
                new Point2d(2, 7),
                new Point2d(10, 19),
                new Point2d(12, 15)
        };

        // Insert points into the K-D Tree
        for (Point2d point : points) {
            tree.insert(point);
        }

        // Search for specific points in the K-D Tree
        StdOut.println(
                tree.search(new Point2d (10,19))); // true
        StdOut.println(
                tree.search(new Point2d(12,19))); // false

        // Range search for points within the specified
        // range
        Rectangle2d rectangle2d = new Rectangle2d(0,0,10,10);
        // Print points within range
        tree.rangeSearch(rectangle2d.getLB(), rectangle2d.getRT());

        // Nearest neighbor search
        Point2d target = new Point2d(12, 14);
        tree.nearestNeighbor(target);
    }

}
