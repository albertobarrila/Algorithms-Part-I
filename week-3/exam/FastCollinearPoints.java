import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] ls;

    public FastCollinearPoints(Point[] points) {

        checkNullOrRepeatedPoints(points);
        Point[] pointsCloned = points.clone();
        Arrays.sort(pointsCloned);
        int n = pointsCloned.length;

        List<LineSegment> list = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            
            Point p = pointsCloned[i];
            Point[] pointsSortedBySlope = pointsCloned.clone();
            Arrays.sort(pointsSortedBySlope, p.slopeOrder());

            int j = 1;
            while (j < n) {
                LinkedList<Point> candidateSegment = new LinkedList<>();
                double slopeBenchmark = p.slopeTo(pointsSortedBySlope[j]);
                do {
                    // aggiungo il punto alla lista dei candidati e incremento lo step
                    candidateSegment.add(pointsSortedBySlope[j++]);
                } while (j < n && p.slopeTo(pointsSortedBySlope[j]) == slopeBenchmark);

                /* Esiste una maximal line se:
                   1- i segmenti sono Collineari
                   2- ci sono almeno 4 punti: quindi altri 3 punti oltre a p
                   3- il maximal line è creato fra p e gli altri punti candidati, quindi p è il più piccolo
                 */
                if (candidateSegment.size() >= 3 && p.compareTo(candidateSegment.peek()) < 0) {
                    Point min = p;
                    Point max = candidateSegment.removeLast();
                    list.add(new LineSegment(min, max));
                }
            }
        }
        ls = list.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return ls.length;
    }

    public LineSegment[] segments() {
        return ls.clone();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
