import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(final Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points arr cant be null");
        }

        final Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicate(sortedPoints);

        final int n = points.length;
        final List<LineSegment> maxLineSegments = new LinkedList<>();

        for (int i = 0; i < n; i++) {

            final Point p = sortedPoints[i];
            final Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int x = 1;
            while (x < n) {

                final LinkedList<Point> candidates = new LinkedList<>();
                final double origin = p.slopeTo(pointsBySlope[x]);
                do {
                    candidates.add(pointsBySlope[x++]);
                } while (x < n && Double.valueOf(p.slopeTo(pointsBySlope[x])).equals(origin));
                if (candidates.size() >= 3 && p.compareTo(candidates.peek()) < 0) {
                    maxLineSegments.add(new LineSegment(p, candidates.removeLast()));
                }
            }
        }
        segments = maxLineSegments.toArray(new LineSegment[0]);
    }

    private static void checkDuplicate(final Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(final String[] args) {
        // read the n points from a file
        final In in = new In("week3/input8.txt");
        final int n = in.readInt();
        final Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            final int x = in.readInt();
            final int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (final Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        final FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (final LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
