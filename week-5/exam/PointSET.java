import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");

        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument is null");

        Stack<Point2D> stack = new Stack<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }

        return stack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");

        if (this.isEmpty())
            return null;

        Point2D neighbor = null;

        for (Point2D point : points) {
            if (neighbor == null) {
                neighbor = point;
            }

            if (point.distanceSquaredTo(p) < neighbor.distanceSquaredTo(p)) {
                neighbor = point;
            }
        }

        return neighbor;
    }
}
