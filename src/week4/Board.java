package week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Board {

    private final int[][] board;
    private final int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(final int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("cant be null");
        } else {
            dimension = tiles.length;
            board = cloneArray(tiles);
        }
    }

    // string representation of this board
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(dimension + "\n");
        for (final int[] ints : board) {
            for (int i1 = 0; i1 < dimension; i1++) {
                sb.append(ints[i1]).append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (board[row][col] != 0 && board[row][col] != valueInGrid(row, col)) hammingDistance++;
            }
        }
        return hammingDistance;
    }

    private int valueInGrid(final int row, final int col) {
        return row * dimension + col + 1; // +1 needs as we count from 1 not from 0
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            final int row = i / dimension;
            final int col = i % dimension;
            if (board[row][col] == 0) continue;
            final int nr = (this.board[row][col] - 1) / dimension;
            final int nc = (this.board[row][col] - 1) % dimension;
            manhattanDistance += Math.abs(nr - row) + Math.abs(nc - col);
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public boolean equals(final Object board) {
        if (this == board) return true;
        if (board == null || getClass() != board.getClass()) return false;
        final Board board1 = (Board) board;
        return Arrays.deepEquals(this.board, board1.board);
    }

    private int[] zeroPosition() {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (board[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        throw new IllegalArgumentException("board must contain 0 tail");
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        final int[] pos = zeroPosition();
        final int zeroRow = pos[0];
        final int zeroCol = pos[1];
        final Collection<Board> neighbors = new ArrayList<>(4);
        // check the top case
        if (zeroRow == 0) {
            // check middle case
            if (zeroCol > 0 && zeroCol < dimension - 1) {
                neighbors.add(creteBoardWithSwap(0, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
            }
            // check left corner case
            if (zeroCol == 0) {
                neighbors.add(creteBoardWithSwap(zeroRow, 0, zeroRow, zeroCol + 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
            }
            // check right corner case
            if (zeroCol == dimension - 1) {
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
            }
        }
        // check the bottom case
        if (zeroRow == dimension - 1) {
            // check middle case
            if (zeroCol > 0 && zeroCol < dimension - 1) {
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
            }
            // check left corner case
            if (zeroCol == 0) {
                neighbors.add(creteBoardWithSwap(zeroRow, 0, zeroRow, zeroCol));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
            }
            // check right corner case
            if (zeroCol == dimension - 1) {
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
            }
        }
        // check the middle case
        if (zeroRow > 0 && zeroRow < dimension - 1) {
            // check middle case
            if (zeroCol > 0 && zeroCol < dimension - 1) {
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
            }
            // check left side case
            if (zeroCol == 0) {
                neighbors.add(creteBoardWithSwap(zeroRow, 0, zeroRow, 1));
                neighbors.add(creteBoardWithSwap(zeroRow, 0, zeroRow - 1, 0));
                neighbors.add(creteBoardWithSwap(zeroRow, 0, zeroRow + 1, 0));
            }
            // check right side case
            if (zeroCol == dimension - 1) {
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
                neighbors.add(creteBoardWithSwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
            }
        }
        return neighbors;
    }

    private Board creteBoardWithSwap(final int initRow, final int initCol,
                                     final int rowSwap, final int colSwap) {
        final int[][] swappedBoard = cloneArray(board);
        swappedBoard[rowSwap][colSwap] = 0;
        swappedBoard[initRow][initCol] = board[rowSwap][colSwap];
        return new Board(swappedBoard);
    }

    private static int[][] cloneArray(final int[][] src) {
        final int length = src.length;
        final int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        final int[][] newBoard = cloneArray(this.board);
        boolean stopMoving = false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (newBoard[i][j] > 0 && newBoard[i][j + 1] > 0) {
                    final int tmp = newBoard[i][j];
                    newBoard[i][j] = newBoard[i][j + 1];
                    newBoard[i][j + 1] = tmp;
                    stopMoving = true;
                    break;
                }
            }
            if (stopMoving) break;
        }
        return new Board(newBoard);
    }

    // unit testing (not graded)
    public static void main(final String[] args) {
        final Board board = new Board(new int[][]{{1, 2, 3}, {7, 8, 0}, {4, 5, 6}});
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.neighbors());
        System.out.println(board.manhattan());

    }

}