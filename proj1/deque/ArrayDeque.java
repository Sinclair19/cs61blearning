package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int start;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        start = 0;
        size = 0;
    }

    private void resize(int capacity) {
        capacity = Math.max(capacity, 8);
        T[] a = (T[]) new Object[capacity];
        int frontHalf = (int) Math.round((capacity - size) / 2.0) ;
        System.arraycopy(items, start, a, frontHalf, size);
        start = frontHalf;
        items = a;
    }

    private void dresize() {
        if ((double) size / items.length < 0.25 && items.length > 8) {
            resize(size * 4);
        }
    }

    private void checkFirstNoNull() {
        int half = (int) Math.round(items.length / 2.0);
        while (start < half && items[start] == null) {
            start += 1;
        }
    }

    @Override
    public void addFirst(T item) {
        checkFirstNoNull();
        if (start == 0) {
            resize((int) Math.round((size + 1) * 1.2));
            // use math.round before cast to int to avoid loss bit
        }
        items[start - 1] = item;
        start -= 1;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (start + size == items.length) {
            resize((int) Math.round((size + 1) * 1.2));
        }
        items[start + size] = item;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = getFirst();
        items[start] = null;

        start += 1;
        size -= 1;

        dresize();

        return x;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = getLast();
        items[start + size - 1] = null;
        size -= 1;
        
        dresize();

        return x;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[start + index];
    }

    private T getFirst() {
        return items[start];
    }
    private T getLast() {
        return items[start + size - 1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = start; i < start + size; i += 1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int place;

        private ArrayDequeIterator() {
            place = 0;
        }

        public boolean hasNext() {
            return place < size;
        }

        public T next() {
            T returnItem = get(place);
            place += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
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
