package deque;

import java.util.Iterator;

public class ArrayDequecircle<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int end;
    private int backsize;
    private int start;

    public ArrayDequecircle() {
        items = (T[]) new Object[8];
        start = 0;
        size = 0;
        end = items.length - 1;
        backsize = 0;
    }

    public void resize(int capacity) {
        capacity = Math.max(capacity, 8);
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(items, end, temp, 0, backsize);
        System.arraycopy(items, start, temp, backsize, size - backsize);
        start = 0;
        end = temp.length - 1;
        backsize = 0;
        items = temp;
    }

    public void dresize() {
        if ((double) size / items.length < 0.25 && items.length > 8) {
            resize(size * 4);
        }
    }

    @Override
    public void addFirst(T item) {
        if (end == (size - backsize - 1) || size == items.length) {
            resize((int) Math.round((size + 1) * 1.2));
            // use math.round before cast to int to avoid loss bit
        }
        if (items[0] != null && backsize >= 0) {
            if (items[end] != null) {
                end -= 1;
            }
            backsize += 1;
        }
        if (start > 0) {
            start -= 1;
        }
        items[getFirstIndex()] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (end == getLastIndex() || size == items.length) {
            resize((int) Math.round((size + 1) * 1.2));
        }
        items[getLastIndex() + 1] = item;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = getFirst();
        items[getFirstIndex()] = null;
        if (backsize == 0) {
            start += 1;
        }
        if (backsize > 0) {
            if (end + 1 < items.length) { //check if end + 1 is bigger than length
                end += 1;
            }
            backsize -= 1;
        }

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
        items[getLastIndex()] = null;
        size -= 1;
        
        dresize();
        return x;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (backsize > 0) {
            if (index < backsize) {
                return items[end + index];
            }
            return items[index - backsize];
        }
        return items[start + index];
    }

    private T getFirst() {
        return items[getFirstIndex()];
    }

    private int getFirstIndex() {
        if (backsize > 0) {
            return end;
        }
        return start;
    }
    private T getLast() {
        return items[getLastIndex()];
    }

    private int getLastIndex() {
        return start + size - backsize - 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = end; i < items.length; i += 1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        for (int i = start; i < size - end; i += 1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    private class ArrayDequecircleIterator implements Iterator<T> {
        private int place;

        private ArrayDequecircleIterator() {
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
        return new ArrayDequecircleIterator();
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
