package week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public final class Permutation {

    public static void main(String[] args) {
        final RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        int k = Integer.parseInt(args[0]);
        while (k > 0) {
            StdOut.println(rq.dequeue());
            k--;
        }
    }

}
