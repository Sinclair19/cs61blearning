package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;

    private BSTNode root;

    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode parent;

        //create leaf node
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public boolean twoChild() {
            return (this.left != null && this.right != null);
        }

        public boolean oneChild() {
            return !this.isLeaf() && (this.left == null || this.right == null);
        }  // not leaf and have one child is null

        public boolean isLeaf() {
            return (this.left == null && this.right == null);
        }

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* return a node with certain key
     * when this node exist, return this node
     * if it doesn't exist, return the previous node
     */
    private BSTNode findPlace(BSTNode cNode, BSTNode pNode, K key) {
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

    private BSTNode leftMax(BSTNode node) {
        if (node.right == null) {
            return node;
        }
        return leftMax(node.right);
    }

    private BSTNode rightMin(BSTNode node) { // chose one between leftMax and rightMin
        if (node == null || node.left == null) {
            return node;
        }
        return rightMin(node.left);
    }

    private BSTNode findNode(K key) {
        return findPlace(root, null, key);
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
        } else {
            if (key.compareTo(find.key) < 0) {
                find.left = temp;
            } else {
                find.right = temp;
            }
            temp.parent = find;
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

    private class BSTMapIterator implements Iterator<K> {
        private final Stack<BSTNode> stack;

        private BSTMapIterator() {
            stack = new Stack<>();
            push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            BSTNode temp = stack.pop();
            push(temp.right);
            return temp.key;
        }

        private void push(BSTNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        Iterable<K> iterable = this;
        for (K key : iterable) {
            keySet.add(key);
        }
        return keySet;
    }

    private void removeRoot() {
        if (root.isLeaf()) {
            root = null;
        } else if (root.oneChild()) {
            root = root.left == null ? root.right : root.left;
            root.parent = null; // fully set node to root
        } else {
            BSTNode sNode = leftMax(root.left);
            if (!sNode.parent.key.equals(root.key)) {
                if (sNode.left != null) {
                    sNode.left.parent = sNode.parent;
                    sNode.parent.right = sNode.left;
                } else {
                    sNode.parent.right = null;
                }
            } else {
                if (sNode.left != null) {
                    sNode.left.parent = sNode.parent;
                    sNode.parent.left = sNode.left;
                } else {
                    sNode.parent.left = null;
                }
            }

            root.key = sNode.key;
            root.value = sNode.value;
        }
    }

    private void remove(BSTNode cNode) {
        BSTNode pNode = cNode.parent;
        if (pNode == null) {
            removeRoot(); // remove root case
        } else {
            if (cNode.isLeaf()) {
                if (cNode.parent.left != null && cNode.parent.left.key.equals(cNode.key)) {
                    cNode.parent.left = null;
                } else {
                    cNode.parent.right = null;
                }
            } else if (cNode.oneChild()) {
                BSTNode child = cNode.left == null ? cNode.right : cNode.left;
                if (cNode.parent.left != null && cNode.parent.left.key.equals(cNode.key)) {
                    cNode.parent.left = child;
                } else {
                    cNode.parent.right = child;
                }
                child.parent = cNode.parent;
            } else if (cNode.twoChild()) {
                BSTNode sNode = leftMax(cNode.left);
                cNode.parent.right = sNode.left; //only this situation can happen
                cNode.key = sNode.key;
                cNode.value = sNode.value;
            }
        }
    }

    @Override
    public V remove(K key) {
        BSTNode cNode = findNode(key);
        V value = cNode.value;
        remove(cNode);
        size -= 1;
        return value;
    }

    @Override
    public V remove(K key, V value) {
        BSTNode cNode = findNode(key);
        if (!cNode.key.equals(key)) {
            return null;
        }
        if (value == null || cNode.value == value) {
            remove(cNode);
        }
        size -= 1;
        return cNode.value;
    }
}
