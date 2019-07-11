package week2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class RandomizedQueue<Item> implements Iterable<Item> {

    private int cursor = 0;
    private Item[] arr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[16];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return cursor == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return cursor;
    }

    // add the item
    public void enqueue(final Item item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("cant add null");
        } else {
            if (cursor == arr.length) {
                resize(2 * arr.length);
            }
            arr[cursor++] = item;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("deq is empty");
        } else {
            final int randIndex = StdRandom.uniform(cursor);
            final Item val = arr[randIndex];
            arr[randIndex] = arr[cursor - 1];
            arr[cursor - 1] = null;
            cursor--;
            if (cursor > 0 && cursor == arr.length / 4) {
                resize(arr.length / 2);
            }
            return val;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("deq is empty");
        } else {
            return arr[StdRandom.uniform(cursor)];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomItr();
    }

    @Override
    public String toString() {
        return StreamSupport.stream(spliterator(), false)
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    private void resize(final int newSize) {
        final Item[] newArr = (Item[]) new Object[newSize];
        for (int i = 0; i < cursor; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    private final class RandomItr implements Iterator<Item> {

        private int cur = 0;
        private final Item[] itrArr;

        public RandomItr() {
            itrArr = (Item[]) new Object[cursor];
            for (int i = 0; i < cursor; i++) {
                itrArr[i] = arr[i];
            }
            StdRandom.shuffle(itrArr);
        }

        @Override
        public boolean hasNext() {
            return cur < cursor;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return itrArr[cur++];
            } else {
                throw new NoSuchElementException("deq is empty");
            }
        }


    }

    // unit testing (required)
    public static void main(final String[] args) {
        final RandomizedQueue<String> rdeq = new RandomizedQueue<>();

        for (int i = 0; i < 100; i++) {
            rdeq.enqueue(String.valueOf(i));
        }
        StdOut.println(rdeq);
        StdOut.println(rdeq.size());

        rdeq.dequeue();
        rdeq.dequeue();
        StdOut.println(rdeq);
        StdOut.println(rdeq.size());

        StdOut.println(rdeq.sample());
        StdOut.println(rdeq.sample());
    }

}