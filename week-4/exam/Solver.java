import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> mainBoard;
    private boolean isSolvable;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Bard is null");
        moves = 0;
        isSolvable = false;
        mainBoard = new Stack<>();
        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        MinPQ<SearchNode> twinSearchNodes = new MinPQ<>();

        searchNodes.insert(new SearchNode(initial, null));
        twinSearchNodes.insert(new SearchNode(initial.twin(), null));

        SearchNode goalNode = null;
        while (true) {
            SearchNode minBoard = searchNodes.delMin();
            SearchNode twinMinBoard = twinSearchNodes.delMin();

            if (minBoard.board.isGoal()) {
                goalNode = minBoard;
                moves = minBoard.moves;
                isSolvable = true;
                break;
            }

            if (twinMinBoard.board.isGoal()) {
                break;
            }

            for (Board b : minBoard.board.neighbors())
                if (minBoard.prev == null || !minBoard.prev.board.equals(b))
                    searchNodes.insert(new SearchNode(b, minBoard));

            for (Board b : twinMinBoard.board.neighbors())
                if (twinMinBoard.prev == null || !twinMinBoard.prev.board.equals(b))
                    twinSearchNodes.insert(new SearchNode(b, twinMinBoard));
        }

        if (isSolvable && goalNode != null) {
            while (goalNode.prev != null) {
                mainBoard.push(goalNode.board);
                goalNode = goalNode.prev;
            }
            mainBoard.push(initial);
        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.isSolvable())
            return moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.isSolvable())
            return mainBoard;
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int priority;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            moves = (prev == null) ? 0 : prev.moves + 1;
            priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }
}
