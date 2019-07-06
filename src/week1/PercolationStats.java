package week1;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public final class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double[] n;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials must be greater than zero");
        }
        this.trials = trials;
        this.n = new double[trials];
        for (int i = 0; i < trials; i++) {
            int openedSites = 0;
            final Percolation pc = new Percolation(n);
            while (!pc.percolates()) {
                final int randomRow = StdRandom.uniform(1, n + 1);
                final int randomCol = StdRandom.uniform(1, n + 1);
                if (!pc.isOpen(randomRow, randomCol) && !pc.isFull(randomRow, randomCol)) {
                    pc.open(randomRow, randomCol);
                    openedSites++;
                }
            }
            this.n[i] = (double) openedSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.n);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.n);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(final String[] args) {
        final PercolationStats ps = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        );
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
