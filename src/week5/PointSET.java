package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Collection;
import java.util.LinkedList;


/**
 * PointSET represents a set of 2d points in a unit square.
 */
public class PointSET {

    private final SET<Point2D> points;

    /**
     * Constructor, creates a SET of points
     */
    public PointSET() {
        points = new SET<>();
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
        return points.size();
    }

    /**
     * Used to insert a new point into the set
     */
    public void insert(final Point2D p) {
        points.add(p);
    }

    /**
     * Returns true if the point is contained within the set
     */
    public boolean contains(final Point2D p) {
        return points.contains(p);
    }

    /**
     * Uses StdDraw to draw the points in the set on screen
     */
    public void draw() {
        points.forEach(Point2D::draw);
    }

    /**
     * Returns a set of points held within a rectangle
     */
    public Iterable<Point2D> range(final RectHV rect) {
        final Collection<Point2D> bounded = new LinkedList<>();
        for (final Point2D point : points) {
            if (rect.contains(point)) {
                bounded.add(point);
            }
        }
        return bounded;
    }

    /**
     * Returns the nearest point to the provided Point2D
     */
    public Point2D nearest(final Point2D p) {
        Point2D champion = null;
        for (final Point2D point : points) {
            if (champion == null) {
                champion = point;
            } else {
                if (p.distanceTo(point) < p.distanceTo(champion)) {
                    champion = point;
                }
            }
        }
        return champion;
    }
}