package deque;


public class LinkedListDeque<T> {
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(T i, Node p, Node n) {
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

    public LinkedListDeque(T x) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        Node Nextnode = sentinel.next;
        sentinel.next = new Node(x, sentinel, Nextnode);
        Nextnode.prev = sentinel.next;
        size = 1;
    }

    public void addFirst(T item) {
        Node Nextnode = sentinel.next;
        sentinel.next = new Node(item, sentinel, Nextnode);
        Nextnode.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        Node LastNode = sentinel.prev;
        sentinel.prev = new Node(item, LastNode, sentinel);
        LastNode.next = sentinel.prev;
        size += 1;
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T first = sentinel.next.item;
        Node NextNode = sentinel.next;
        sentinel.next = NextNode.next;
        NextNode.next.prev = sentinel;
        NextNode = null;

        size -= 1;
        return first;
    }

    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T last = sentinel.prev.item;
        Node PrevNode = sentinel.prev;
        sentinel.prev = PrevNode.prev;
        PrevNode.prev.next = sentinel;
        PrevNode = null;

        size -= 1;
        return last;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;

        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println();
    }

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

    public T getRecursive(int index) {
        Node p = sentinel.next;
        if (p == null) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursive(index - 1);
    }

}
