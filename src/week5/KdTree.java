package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * PointSET represents a set of 2d points in a unit square, using a 2d-tree.
 */
public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    /**
     * Node class to build the tree
     */
    private class Node {
        Point2D value;
        Node left;
        Node right;
        boolean division;

        Node(final Point2D p) {
            value = p;
            left = null;
            right = null;
        }
    }

    private Node root;
    private List<Point2D> contained;
    private Point2D champion;
    private Point2D comparison;
    private int size;

    /**
     * Constructor, creates a SET of points
     */
    public KdTree() {
        root = null;
    }

    /**
     * Returns true when there are no points in the set
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of points in the set
     */
    public int size() {
        return root == null ? 0 : size;
    }

    /**
     * Used to insert a new point into the set
     */
    public void insert(final Point2D p) {
        final Node newPoint = new Node(p);
        Node currPos = root;

        if (root == null) {
            newPoint.division = VERTICAL;
            root = newPoint;
            size++;
            return;
        }

        while (true) {
            if (p.equals(currPos.value)) {
                return;
            }

            //Use first bit to determine if level is even or odd
            if (currPos.division == VERTICAL) {
                //Slice vertically
                if (p.x() < currPos.value.x()) {
                    if (currPos.left == null) {
                        newPoint.division = HORIZONTAL;
                        currPos.left = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.left;
                    }
                } else {
                    if (currPos.right == null) {
                        newPoint.division = HORIZONTAL;
                        currPos.right = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.right;
                    }
                }

            } else {
                //Slice horizontally
                if (p.y() < currPos.value.y()) {
                    if (currPos.left == null) {
                        newPoint.division = VERTICAL;
                        currPos.left = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.left;
                    }
                } else {
                    if (currPos.right == null) {
                        newPoint.division = VERTICAL;
                        currPos.right = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.right;
                    }
                }
            }
        }
    }

    /**
     * Returns true if the point is contained within the set
     */
    public boolean contains(final Point2D p) {
        Node currPos = root;

        while (currPos != null) {
            if (p.equals(currPos.value)) {
                return true;
            }

            //Use first bit to determine if level is even or odd
            if (currPos.division == VERTICAL) {
                //Vertical slice
                currPos = p.x() < currPos.value.x() ? currPos.left : currPos.right;

            } else {
                //Horizontal slice
                currPos = p.y() < currPos.value.y() ? currPos.left : currPos.right;
            }
        }

        return false;
    }

    /**
     * Uses StdDraw to draw the points in the set on screen
     */
    public void draw() {
        if (root != null) {
            drawNodes(root);
        }
    }

    private void drawNodes(final Node node) {
        node.value.draw();

        if (node.right != null) {
            drawNodes(node.right);
        }

        if (node.left != null) {
            drawNodes(node.left);
        }
    }

    /**
     * Returns a set of points held within a rectangle
     */
    public Iterable<Point2D> range(final RectHV rect) {
        contained = new LinkedList<>();
        checkPointsInRange(root, rect);
        return Collections.unmodifiableList(contained);
    }

    private void checkPointsInRange(final Node node, final RectHV rect) {
        if (node == null) {
            return;
        }
        if (node.division == VERTICAL) {
            if (node.value.x() > rect.xmax()) {
                checkPointsInRange(node.left, rect);
            } else if (node.value.x() < rect.xmin()) {
                checkPointsInRange(node.right, rect);
            } else {
                checkPointsInRange(node.left, rect);
                checkPointsInRange(node.right, rect);
                if (rect.contains(node.value)) {
                    contained.add(node.value);
                }
            }
        } else {
            if (node.value.y() > rect.ymax()) {
                checkPointsInRange(node.left, rect);
            } else if (node.value.y() < rect.ymin()) {
                checkPointsInRange(node.right, rect);
            } else {
                checkPointsInRange(node.left, rect);
                checkPointsInRange(node.right, rect);
                if (rect.contains(node.value)) {
                    contained.add(node.value);
                }
            }
        }
    }

    /**
     * Returns the nearest point to the provided Point2D
     */
    public Point2D nearest(final Point2D p) {
        champion = null;
        comparison = p;

        checkNearest(root);

        return champion;
    }

    private void checkNearest(final Node node) {
        if (node == null) {
            return;
        }
        if (champion == null) {
            champion = node.value;
        } else if (comparison.distanceTo(champion) > comparison.distanceTo(node.value)) {
            champion = node.value;
        }
        if (node.division == VERTICAL) {
            if (comparison.distanceTo(champion) > comparison.distanceTo(node.value)) {
                if (node.value.x() >= comparison.x()) {
                    checkNearest(node.left);
                    checkNearest(node.right);
                } else {
                    checkNearest(node.right);
                    checkNearest(node.left);
                }
            } else {
                if (node.value.x() > comparison.x()) {
                    checkNearest(node.left);
                } else if (node.value.x() < comparison.x()) {
                    checkNearest(node.right);
                } else {
                    checkNearest(node.left);
                    checkNearest(node.right);
                }
            }
        } else {
            if (comparison.distanceTo(champion) > comparison.distanceTo(node.value)) {
                if (node.value.y() >= comparison.y()) {
                    checkNearest(node.left);
                    checkNearest(node.right);
                } else {
                    checkNearest(node.right);
                    checkNearest(node.left);
                }
            } else {
                if (node.value.y() > comparison.y()) {
                    checkNearest(node.left);
                } else if (node.value.y() < comparison.y()) {
                    checkNearest(node.right);
                } else {
                    checkNearest(node.left);
                    checkNearest(node.right);
                }
            }
        }
    }

    public static void main(final String[] args) {
        final KdTree tree = new KdTree();
        tree.insert(new Point2D(0.4, 0.2));
        tree.insert(new Point2D(0.2, 0.1));
        tree.insert(new Point2D(0.7, 0.3));
        tree.insert(new Point2D(0.3, 0.6));

        StdOut.println("Find existing point: " + tree.contains(new Point2D(0.7, 0.3)));
        StdOut.println("Find non-existant p: " + !tree.contains(new Point2D(0.2, 0.6)));
        StdOut.println("Count of nodes = 4 : " + tree.size());

        tree.insert(new Point2D(0.3, 0.6));

        StdOut.println("Cannot insert same : " + tree.size());

        StdDraw.setPenRadius(0.01);
        tree.draw();

        final RectHV rect = new RectHV(0.3, 0.1, 0.9, 0.9);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.002);
        rect.draw();

        for (final Point2D point : tree.range(rect)) {
            StdOut.println(point.toString());
        }

        StdOut.println();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        final Point2D comp = new Point2D(0.4, 0.5);
        comp.draw();

        StdOut.println(tree.nearest(comp).toString());
    }
}