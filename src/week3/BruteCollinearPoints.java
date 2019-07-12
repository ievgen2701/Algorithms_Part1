import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(final Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points arr cant be null");
        }
        checkForDuplicates(points);
        final List<LineSegment> tmpSegments = new ArrayList<>();
        for (int p = 0; p < points.length - 3; p++) {
            if (points[p] == null) {
                throw new IllegalArgumentException("point cant be null");
            }
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (Double.valueOf(points[p].slopeTo(points[q])).equals(points[p].slopeTo(points[r]))
                                && Double.valueOf(points[p].slopeTo(points[q])).equals(points[p].slopeTo(points[s]))) {
                            tmpSegments.add(new LineSegment(points[p], points[q]));
                        }
                    }
                }
            }
        }
        segments = tmpSegments.toArray(new LineSegment[tmpSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    private void checkForDuplicates(final Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
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
        final BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (final LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
