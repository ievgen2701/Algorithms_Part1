package quiz1_3;

import java.util.Arrays;
import java.util.Random;

public class Successor {

    private final int[] src;

    public Successor(final int n) {
        this.src = new int[n];
        for (int i = 0; i < this.src.length; i++) { // N
            this.src[i] = i;
        }
    }

    public void removeAndReplaceSuccessor(final int toRemove) {
        if (toRemove < this.src.length) {
            int low = 0;
            int high = this.src.length - 1;
            int key = -1;
            while (low <= high) {
                final int mid = (low + high) >>> 1;
                final int midVal = this.src[mid];
                final int cmp = midVal - toRemove;
                if (cmp < 0) {
                    low = mid + 1;
                } else if (cmp > 0) {
                    high = mid - 1;
                } else {
                    key = mid;
                    break;
                }
            }
            if (key >= 0) {
                // we found the key
                if (key != this.src.length - 1) { // not the last element
                    int next = this.src[key + 1];
                    while (key < this.src.length && this.src[key] == next) {
                        next = this.src[key];
                        key++;
                    }
                    if (key < this.src.length) {
                        this.src[key] = next;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Successor{" +
                "src=" + Arrays.toString(this.src) +
                '}';
    }

    public static void main(final String[] args) throws InterruptedException {
        final Successor successor = new Successor(25);
        final Random random = new Random();
        while (true) {
            successor.removeAndReplaceSuccessor(random.nextInt(25));
            System.out.println(successor);
        }

    }
}
