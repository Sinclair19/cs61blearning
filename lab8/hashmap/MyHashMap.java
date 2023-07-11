package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int bucketsSize;
    private int size;
    private double loadFactor;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.bucketsSize = initialSize;
        this.loadFactor = maxLoad;
        this.size = 0;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!


    private int returnHash(K key) {
        int hashcode = key.hashCode();
        int num = hashcode % bucketsSize;
        while (num < 0) {
            num += bucketsSize;
        }
        return num;
    }

    @Override
    public void clear() {
        buckets = null;
        size = 0;
        loadFactor = 0.0;
        bucketsSize = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    @Override
    public V get(K key) {
        if (buckets == null) {
            return null;
        }
        int num = returnHash(key);
        if (buckets[num] == null) {
            return null;
        }
        for (Node node : buckets[num]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    private Node getNode(K key) {
        if (buckets == null) {
            return null;
        }
        int num = returnHash(key);
        if (buckets[num] == null) {
            return null;
        }
        for (Node node : buckets[num]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkLoadFactor() {
        double load = ((double) this.size) / this.bucketsSize;
        if (load > loadFactor) {
            resize(bucketsSize * 2);
        }
    }

    private void resize(int capacity) {
        MyHashMap<K, V> temp = new MyHashMap<>(capacity, this.loadFactor);
        for (K key : this) {
            temp.put(key, this.get(key));
        }
        this.buckets = temp.buckets;
        bucketsSize = capacity;

    }

    @Override
    public void put(K key, V value) {
        Node tempNode = createNode(key, value);
        int num = returnHash(key);
        if (buckets[num] == null) {
            buckets[num] = createBucket();
        }
        Node get = getNode(key);
        if (get != null) {
            get.value = value;
        } else {
            buckets[num].add(tempNode);
            size += 1;
            checkLoadFactor();
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (K key : this) {
            keySet.add(key);
        }
        return keySet;
    }

    /*
    private class HashMapIterator implements Iterator<K> {

        private Iterator<Collection<Node>> outerIterator;
        private Iterator<Node> innerIterator;

        public HashMapIterator() {
            outerIterator = Arrays.stream(buckets).iterator();
            innerIterator = nextInner();
        }

        @Override
        public boolean hasNext() {
            if (innerIterator == null) {
                return false;
            }
            return innerIterator.hasNext();
        }

        private Iterator<Node> nextInner() {
            Iterator<Node> result = null;
            while (outerIterator != null && outerIterator.hasNext()) {
                result = outerIterator.next().iterator();
                if (result.hasNext()) {
                    return result;
                }
            }
            return result;
        }

        @Override
        public K next() {
            if (outerIterator == null || innerIterator == null) {
                return null;
            }
            if (!innerIterator.hasNext()) {
                innerIterator = nextInner();
                if (innerIterator == null) {
                    return null;
                }
            }
            return innerIterator.next().key;
        }

        @Override
        public K next() {
            if (!outerIterator.hasNext()) {
                return null;
            }
            if (isInnerFinished) {
                innerIterator = outerIterator.next().iterator();
                isInnerFinished = false;
            }
            if (!innerIterator.hasNext()) {
                isInnerFinished = true;
                return next();
            }
            //K result = innerIterator.next().key;
            return innerIterator.next().key;
        }

    }
    */

    private class HashMapIterator implements Iterator<K> {

        private Iterator<Node> innerIterator;

        private int position;

        public HashMapIterator() {
            position = findPosition(0);
            innerIterator = buckets[position].iterator();
        }

        private int findPosition(int pos) {
            while (pos < bucketsSize && buckets[pos] == null) {
                pos += 1;
            }
            return pos;
        }

        @Override
        public boolean hasNext() {
            return findPosition(position + 1) < bucketsSize || innerIterator.hasNext();
        }

        @Override
        public K next() {
            if (!innerIterator.hasNext()) {
                int temp = findPosition(position + 1);
                if (temp > bucketsSize - 1) { // when temp bigger than bucket size
                    throw new NoSuchElementException();
                }
                innerIterator = buckets[temp].iterator();
                position = temp;
            }
            return innerIterator.next().key;
        }

    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private V remove(Node rmNode) {
        if (rmNode != null) {
            buckets[returnHash(rmNode.key)].remove(rmNode);
            return rmNode.value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        return remove(node);
    }

    @Override
    public V remove(K key, V value) {
        Node node = getNode(key);
        if (node != null && node.value == value) {
            return remove(node);
        }
        return null;
    }
}
