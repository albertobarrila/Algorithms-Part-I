import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;

        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    private int correct(int row, int col) {
        int val = row * n + col + 1;
        if (val == n * n) val = 0;
        return val;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = tiles[i][j];
                int correct = correct(i, j);
                if (val != 0 && val != correct)
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distances = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = tiles[i][j];
                int correct = correct(i, j);
                if (val != 0 && val != correct) {
                    int row = (val - 1) / n;
                    distances += Math.abs(i - row);
                    int col = (val - 1) % n;
                    distances += Math.abs(j - col);
                }
            }
        }
        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = tiles[i][j];
                int correct = correct(i, j);
                if (val != correct)
                    return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board other = (Board) y;
        if (other.n != n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean valid(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < n;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();

        int emptyi = 0;
        int emptyj = 0;


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyi = i;
                    emptyj = j;
                }
            }
        }

        // up
        if (valid(emptyi - 1, emptyj)) {
            exch(tiles, emptyi, emptyj, emptyi - 1, emptyj);
            neighbors.enqueue(new Board(tiles));
            exch(tiles, emptyi, emptyj, emptyi - 1, emptyj);
        }

        // down
        if (valid(emptyi + 1, emptyj)) {
            exch(tiles, emptyi, emptyj, emptyi + 1, emptyj);
            neighbors.enqueue(new Board(tiles));
            exch(tiles, emptyi, emptyj, emptyi + 1, emptyj);
        }

        // left
        if (valid(emptyi, emptyj - 1)) {
            exch(tiles, emptyi, emptyj, emptyi, emptyj - 1);
            neighbors.enqueue(new Board(tiles));
            exch(tiles, emptyi, emptyj, emptyi, emptyj - 1);
        }

        // right
        if (valid(emptyi, emptyj + 1)) {
            exch(tiles, emptyi, emptyj, emptyi, emptyj + 1);
            neighbors.enqueue(new Board(tiles));
            exch(tiles, emptyi, emptyj, emptyi, emptyj + 1);
        }
        return neighbors;
    }

    private void exch(int[][] a, int i, int j, int k, int y) {
        int t = a[i][j];
        a[i][j] = a[k][y];
        a[k][y] = t;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }
        
        if (twinTiles[0][0] != 0 && twinTiles[1][0] != 0)
            exch(twinTiles, 0, 0, 1, 0);
        else
            exch(twinTiles, 0, 1, 1, 1);

        return new Board(twinTiles);
    }

    public static void main(String[] args) {
        Board b = new Board(new int[][] {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        });
        Board b1 = new Board(new int[][] {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        });
        StdOut.println(b.toString());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.isGoal());
        Board p = new Board(new int[][] {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        });
        StdOut.println(p.toString());
        StdOut.println(p.hamming());
        StdOut.println(p.manhattan());
        StdOut.println(p.isGoal());
        StdOut.println(p.equals(b));
        StdOut.println(b.equals(b));
        StdOut.println(b.equals(b1));
        Board t = b.twin();
        StdOut.println(t.toString());
    }

}
