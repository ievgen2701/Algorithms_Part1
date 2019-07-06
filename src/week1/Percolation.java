package week1;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public final class Percolation {

    private final boolean[] openedSites;
    private final WeightedQuickUnionUF uf;
    private final int gridSize;
    private final int top;
    private final int bottom;
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n bust be greater than zero");
        } else {
            // count virtual top and virtual bottom nodes
            final int gridItemsSize = n * n + 2;
            this.uf = new WeightedQuickUnionUF(gridItemsSize);
            this.openedSites = new boolean[gridItemsSize];
            this.gridSize = n;
            // index for virtual top node, it's not counted in grid, but we keep it in mind
            this.top = n * n;
            // index for virtual bottom node, it's not counted in grid, but we keep it in mind
            this.bottom = n * n + 1;
            this.openedSites[this.top] = true;
            this.openedSites[this.bottom] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            this.openedSites[gridValue(row, col)] = true;
            this.numberOfOpenSites++;
            // check top border
            if (row == 1) {
                this.uf.union(col - 1, this.top);
            }
            // check bottom border
            if (row == this.gridSize) {
                this.uf.union(gridValue(row, col), this.bottom);
            }
            // check above neighbour
            if (row > 1 && isOpen(row - 1, col)) {
                this.uf.union(gridValue(row, col), gridValue(row - 1, col));
            }
            // check below neighbour
            if (row < this.gridSize && isOpen(row + 1, col)) {
                this.uf.union(gridValue(row, col), gridValue(row + 1, col));
            }
            // check left neighbour
            if (col > 1 && isOpen(row, col - 1)) {
                this.uf.union(gridValue(row, col), gridValue(row, col - 1));
            }
            // check right neighbour
            if (col < this.gridSize && isOpen(row, col + 1)) {
                this.uf.union(gridValue(row, col), gridValue(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        checkBounds(row, col);
        return this.openedSites[gridValue(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        checkBounds(row, col);
        return isOpen(row, col) && this.uf.connected(this.top, gridValue(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(this.top, this.bottom);
    }

    private void checkBounds(final int row, final int col) {
        if (row < 1 || col < 1 || row > this.gridSize || col > this.gridSize) {
            throw new IllegalArgumentException("row or col is out of bounds: " + row + ", " + col);
        }
    }

    private int gridValue(final int row, final int col) {
        return (row - 1) * this.gridSize + col - 1;
    }
}
