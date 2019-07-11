package week2;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("cant add null");
        } else {
            final Node f = first;
            final Node newNode = new Node(item, f, null);
            first = newNode;
            if (f == null) {
                last = newNode;
            } else {
                f.prev = newNode;
            }
            size++;
        }
    }

    // add the item to the back
    public void addLast(final Item item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("cant add null");
        } else {
            final Node l = last;
            final Node newNode = new Node(item, null, l);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deq is empty");
        } else {
            final Node f = first;
            final Item val = f.item;
            final Node next = f.next;
            f.item = null;
            f.next = null;
            first = next;
            if (next == null) {
                last = null;
            } else {
                next.prev = null;
            }
            size--;
            return val;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deq is empty");
        } else {
            final Node l = this.last;
            final Item val = l.item;
            final Node prev = l.prev;
            l.item = null;
            l.prev = null;
            last = prev;
            if (prev == null) {
                first = null;
            } else {
                prev.next = null;
            }
            size--;
            return val;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<>() {

            private Node f = first;

            @Override
            public boolean hasNext() {
                return f != null;
            }

            @Override
            public Item next() {
                if (hasNext()) {
                    final Item next = f.item;
                    f = f.next;
                    return next;
                } else {
                    throw new NoSuchElementException("deq is empty");
                }
            }
        };
    }

    @Override
    public String toString() {
        return StreamSupport.stream(spliterator(), false)
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    private final class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(final Item item, final Node next, final Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    // unit testing (required)
    public static void main(final String[] args) {
        final Deque<String> deq = new Deque<>();

        deq.addFirst("01");
        deq.addFirst("02");
        deq.addLast("03");
        deq.addLast("04");
        StdOut.println(deq);
        StdOut.println(deq.size());

        deq.removeFirst();
        deq.removeFirst();
        StdOut.println(deq);
        StdOut.println(deq.size());

        deq.removeLast();
        deq.removeLast();
        StdOut.println(deq);
        StdOut.println(deq.size());
    }

}
