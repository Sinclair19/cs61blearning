package deque;


import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        private Node(T i, Node p, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** initial with a given x
    public LinkedListDeque(T x) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        Node nextNode = sentinel.next;
        sentinel.next = new Node(x, sentinel, nextNode);
        nextNode.prev = sentinel.next;
        size = 1;
    }
     */

    @Override
    public void addFirst(T item) {
        Node nextNode = sentinel.next;
        sentinel.next = new Node(item, sentinel, nextNode);
        nextNode.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node lastNode = sentinel.prev;
        sentinel.prev = new Node(item, lastNode, sentinel);
        lastNode.next = sentinel.prev;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T first = sentinel.next.item;
        Node nextNode = sentinel.next;
        sentinel.next = nextNode.next;
        nextNode.next.prev = sentinel;
        nextNode = null;

        size -= 1;
        return first;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T last = sentinel.prev.item;
        Node prevNode = sentinel.prev;
        sentinel.prev = prevNode.prev;
        prevNode.prev.next = sentinel;
        prevNode = null;

        size -= 1;
        return last;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;

        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T get(int index) {
        Node p = sentinel.next;
        while (index != 0) {
            if (p == null) {
                return null;
            }
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, Node p) {
        if (p == null) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }

    public T getRecursive(int index) {
        Node p = sentinel.next;
        if (p == null) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index, p);
    }

    private class LinkedListIterator implements Iterator<T> {
        private int place;
        private Node current;

        private LinkedListIterator() {
            place = 0;
            current = sentinel.next;
        }

        public boolean hasNext() {
            return place < size;
        }

        public T next() {
            T returnItem = current.item;
            place += 1;
            current = current.next;
            return returnItem;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        int i = 0;
        for (T item : this) {
            if (!other.get(i).equals(item)) {
                return false;
            }
            i += 1;
        }
        return true;
    }
}
