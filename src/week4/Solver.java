package week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

    private Node lastNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(final Board initial) {
        final MinPQ<Node> mainQueue = new MinPQ<>();
        final MinPQ<Node> neighboursQueue = new MinPQ<>();
        mainQueue.insert(new Node(initial, null));
        neighboursQueue.insert(new Node(initial.twin(), null));

        boolean sSolvable = false;
        boolean solvable = false;
        while (!solvable && !sSolvable) { //!solvable &&
            lastNode = expand(mainQueue);
            solvable = (lastNode != null);
            sSolvable = (expand(neighboursQueue) != null);
        }
    }

    private Node expand(final MinPQ<Node> queue) {
        if (queue.isEmpty()) return null;
        final Node current = queue.delMin();
        if (current.board.isGoal()) return current;

        for (final Board b : current.board.neighbors()) {
            if (current.previous == null || !b.equals(current.previous.board)) {
                queue.insert(new Node(b, current));
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return lastNode != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        return lastNode != null ? lastNode.numMoves : -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (lastNode != null) {
            final Stack<Board> res = new Stack<Board>();
            for (Node tail = lastNode; tail != null; tail = tail.previous) {
                res.push(tail.board);
            }
            return res;
        } else {
            return null;
        }
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int numMoves;
        private final Node previous;

        public Node(final Board board, final Node previous) {
            this.board = board;
            this.previous = previous;
            this.numMoves = previous == null ? 0 : previous.numMoves + 1;
        }

        @Override
        public int compareTo(final Node otherNode) {
            return (this.board.manhattan() - otherNode.board.manhattan()) + (this.numMoves - otherNode.numMoves);
        }

    }

    // test client (see below)
    public static void main(final String[] args) {
        // create initial board from file
        final In in = new In(args[0]);
        final int n = in.readInt();
        final int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        final Board initial = new Board(tiles);

        // solve the puzzle
        final Solver solver = new Solver(initial);

        // print solution to standard output
        if (solver.isSolvable()) {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (final Board board : solver.solution()) {
                StdOut.println(board);
            }
        } else StdOut.println("No solution possible");
    }

}