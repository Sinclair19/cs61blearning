package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;

    private BSTNode root;

    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        //create leaf node
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        /* return a node with certain key
         * when this node exist, return this node
         * if it doesn't exist, return the previous node
         */
        public BSTNode findPlace(BSTNode cNode, BSTNode pNode,  K key) {
            if (cNode == null) {
                return pNode; //return previous node
            }
            if (key.compareTo(cNode.key) < 0) {
                return findPlace(cNode.left, cNode, key);
            }
            if (key.compareTo(cNode.key) > 0) {
                return findPlace(cNode.right, cNode, key);
            }
            return cNode; //return current node
        }

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private BSTNode findNode(K key) {
        return root.findPlace(root, null, key);
    }

    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        return findNode(key).key.equals(key);
    }

    public V get(K key) {
        if (root == null) {
            return null;
        }
        BSTNode tempNode = findNode(key);
        if (tempNode.key.equals(key)) {
            return tempNode.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public void put(K key, V value) {
        BSTNode temp = new BSTNode(key, value);
        if (root == null) {
            root = temp;
            size += 1;
            return;
        }
        BSTNode find = findNode(key);
        if (find.key == key) {
            find.value = value;
        }
        else {
           if (key.compareTo(find.key) <= 0) {
               find.left = temp;
           }
           else {
               find.right = temp;
           }
           size += 1;
        }
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.println(node.key.toString() + " - " + node.value.toString());
        printInOrder(node.right);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
