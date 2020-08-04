import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkNullOrRepeatedPoints(points);

        Point[] pointsCloned = points.clone();
        Arrays.sort(pointsCloned);
        List<LineSegment> list = new ArrayList<LineSegment>();
        int n = pointsCloned.length;
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        double s1 = pointsCloned[p].slopeTo(pointsCloned[q]);
                        double s2 = pointsCloned[p].slopeTo(pointsCloned[r]);
                        double s3 = pointsCloned[p].slopeTo(pointsCloned[s]);
                        if (s1 == s2 && s1 == s3)
                            list.add(new LineSegment(pointsCloned[p], pointsCloned[s]));

                    }
                }
            }
        }

        segments = list.toArray(new LineSegment[list.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    private void checkNullOrRepeatedPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points are null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("One point is null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("One point is null");
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("There is a duplicate point");
            }

        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
