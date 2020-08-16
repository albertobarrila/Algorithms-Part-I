import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

    private class Node {
        private final Point2D value;
        private final RectHV rect;
        private Node right;
        private Node left;
        private final int level;

        public Node(Point2D value, RectHV rect, Node right, Node left, int level) {
            this.value = value;
            this.rect = rect;
            this.right = right;
            this.left = left;
            this.level = level;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    private Node insert(Point2D p, RectHV rect, Node n, int level) {

        if (n == null) {
            size++;
            level++;
            return new Node(p, rect, null, null, level);
        }

        if (n.value.equals(p))
            return n;

        // sono sulla x
        if (n.level % 2 != 0 && p.x() >= n.value.x()) {
            n.right = insert(p,
                             new RectHV(n.value.x(), rect.ymin(), rect.xmax(), rect.ymax()),
                             n.right, n.level);
        }

        if (n.level % 2 != 0 && p.x() < n.value.x()) {
            n.left = insert(p,
                            new RectHV(rect.xmin(), rect.ymin(), n.value.x(), rect.ymax()),
                            n.left, n.level);
        }

        // sono sulla y
        if (n.level % 2 == 0 && p.y() >= n.value.y()) {
            n.right = insert(p,
                             new RectHV(rect.xmin(), n.value.y(), rect.xmax(), rect.ymax()),
                             n.right, n.level);
        }

        if (n.level % 2 == 0 && p.y() < n.value.y()) {
            n.left = insert(p,
                            new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), n.value.y()),
                            n.left, n.level);
        }
        return n;
    }

    public void insert(Point2D p) {
        root = insert(p, new RectHV(0.0, 0.0, 1.0, 1.0), root, 0);
    }

    private boolean contains(Point2D p, Node n) {

        if (n == null)
            return false;

        if (n.value.equals(p))
            return true;

        if (n.level % 2 != 0 && n.value.x() <= p.x()) {
            return contains(p, n.right);
        }
        if (n.level % 2 != 0 && n.value.x() > p.x()) {
            return contains(p, n.left);
        }

        if (n.level % 2 == 0 && n.value.y() <= p.y()) {
            return contains(p, n.right);
        }
        if (n.level % 2 == 0 && n.value.y() > p.y()) {
            return contains(p, n.left);
        }

        return false;
    }

    public boolean contains(Point2D p) {
        return root != null && contains(p, root);
    }

    private void draw(Node n) {
        if (n == null)
            return;

        n.value.draw();

        if (n.right != null)
            draw(n.right);

        if (n.left != null)
            draw(n.left);
    }

    public void draw() {
        draw(root);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument is null");

        Stack<Point2D> stack = new Stack<Point2D>();

        range(rect, root, stack);

        return stack;
    }

    private void range(RectHV rect, Node n, Stack<Point2D> stack) {

        if (n == null || !rect.intersects(n.rect))
            return;

        if (rect.contains(n.value))
            stack.push(n.value);

        range(rect, n.left, stack);
        range(rect, n.right, stack);
    }

    private Point2D nearest(Point2D p, Node n, Point2D neighbor) {
        if (n == null || n.rect.distanceSquaredTo(p) > p.distanceSquaredTo(neighbor))
            return neighbor;

        if (p.distanceSquaredTo(neighbor) > p.distanceSquaredTo(n.value))
            neighbor = n.value;

        neighbor = nearest(p, n.left, neighbor);
        neighbor = nearest(p, n.right, neighbor);
        return neighbor;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");

        if (this.isEmpty())
            return null;


        Point2D neighbor = nearest(p, root, root.value);
        return neighbor;
    }

}
