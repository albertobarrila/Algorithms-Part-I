import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private boolean[] grid;
    private final int dimension;
    private int opens;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater then zero");

        opens = 0;
        dimension = n;
        grid = new boolean[n * n];
        for (int i = 0; i < n * n; i++)
            grid[i] = false;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        for (int i = 0; i < n; i++) {
            uf.union(0, i + 1);
            uf.union((n * n) + 1, (n * (n - 1)) + i + 1);
        }

    }

    private int twoDToOneD1(int row, int col) {
        return (row - 1) * dimension + col;
    }

    private int twoDToOneD0(int row, int col) {
        return ((row - 1) * dimension + col) - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension)
            throw new IllegalArgumentException("row and col must be greater then zero");

        if (!isOpen(row, col)) {
            opens++;
            grid[twoDToOneD0(row, col)] = true;


            if (row > 1 && isOpen(row - 1, col))
                uf.union(twoDToOneD1(row, col), twoDToOneD1(row - 1, col));

            if (row < dimension && isOpen(row + 1, col))
                uf.union(twoDToOneD1(row, col), twoDToOneD1(row + 1, col));

            if (col < dimension && isOpen(row, col + 1))
                uf.union(twoDToOneD1(row, col), twoDToOneD1(row, col + 1));

            if (col > 1 && isOpen(row, col - 1))
                uf.union(twoDToOneD1(row, col), twoDToOneD1(row, col - 1));
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension)
            throw new IllegalArgumentException("row and col must be greater then zero");

        return grid[twoDToOneD0(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension)
            throw new IllegalArgumentException("row and col must be greater then zero");

        return isOpen(row, col) && uf.find(0) == uf.find(twoDToOneD1(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opens;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find((dimension * dimension) + 1) && opens > 0;
    }
}
