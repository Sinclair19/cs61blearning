package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int start;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        start = 0;
        size = 0;
    }

    private void resizer(int capacity) {
        T[] a = (T[]) new Object[start + capacity];
        System.arraycopy(items, start, a, start, size);
        items = a;
    }

    private void resizel(int capacity) {
        int right_space = items.length - start;
        int resized_size = capacity + right_space;
        T[] a = (T[]) new Object[resized_size];
        System.arraycopy(items, start, a, capacity, size);

        start = capacity;
        items = a;
    }

    private void dresizer() {
        int length = items.length - start;
        if ((double) size / (length) < 0.25) {
            resizer(size * 4); // let right size / length = 0.25
        }
    }

    private void dresizel() {
        int length = start;
        if ((double) size / (length) < 0.25) {
            resizer(size * 3); // let right size / length = 0.25
        }
    }

    /**
    private void resizer(int capacity, int position) {
        // overload with position argument
        T[] temp = (T[]) new Object[items.length + 1]; // to allow at least one position to be empty
        if ((double) size / items.length > 0.25){
            temp = (T[]) new Object[capacity + 1];
        }
        System.arraycopy(items, 0, temp, 0, position); // separately copy
        System.arraycopy(items, position, temp, position + 1, size - position);
        items = temp;
    }
     */

    public void addFirst(T item) {
        if (start == 0) {
            resizel((int) ((size + 1) * 1.2));
        }
        items[start - 1] = item;
        start -= 1;
        size += 1;
    }

    public void addLast(T item) {
        if (start + size == items.length) {
            resizer((int) ((size + 1) * 1.2));
        }
        items[start + size] = item;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = getFirst();
        items[start] = null;

        start += 1;
        dresizel();

        size -= 1;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = getLast();
        items[start + size - 1] = null;
        dresizer();

        size -= 1;
        return x;
    }

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

    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = start; i < start + size; i += 1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

}
