package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMapunfinished<K extends Comparable<K>, V> implements Map61B<K, V> {
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
    private BSTNode findPlace(BSTNode cNode, BSTNode pNode, K key, boolean returnParent) {
        if (cNode == null) {
            return pNode; //return previous node
        }
        if (key.compareTo(cNode.key) < 0) {
            return findPlace(cNode.left, cNode, key, returnParent);
        }
        if (key.compareTo(cNode.key) > 0) {
            return findPlace(cNode.right, cNode, key, returnParent);
        }
        if (returnParent) {
            return pNode;
        }
        return cNode; //return current node
    }

    private BSTNode leftMax(BSTNode node) {
        if (node.right == null) {
            return node;
        }
        return leftMax(node.right);
    }

    private BSTNode rightMin(BSTNode node) {
        if (node.left == null) {
            return node;
        }
        return rightMin(node.left);
    }

    private String findDirection(BSTNode node, K key) {
        if (node == null) { // if node is root
            return null;
        }
        if (node.left != null && node.left.key.equals(key)) {
            return "left";
        }
        return "right";
    }

    private BSTNode findChild(BSTNode pNode, String direction) {
        if (pNode == null) {
            return root;
        }
        if (direction == null) {
            return (pNode.left != null) ? pNode.left : pNode.right;
        }
        if (direction.equals("left")) {
            return pNode.left;
        }
        return pNode.right;
    }

    private BSTNode findNode(K key, Boolean returnParent) {
        return findPlace(root, null, key, returnParent);
    }

    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        return findNode(key, false).key.equals(key);
    }

    public V get(K key) {
        if (root == null) {
            return null;
        }
        BSTNode tempNode = findNode(key, false);
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
        BSTNode find = findNode(key, false);
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
        for (K key: iterable) {
            keySet.add(key);
        }
        return keySet;
    }

    private void remove(BSTNode cNode, BSTNode pNode, String direction) {
        if (pNode == null || pNode.twoChild()) { // cNode is root or has two children
            BSTNode sNode = leftMax(cNode);
            root = new BSTNode(sNode.key, sNode.value, cNode.left, cNode.right);
            sNode = null;
        }
        if (cNode.isLeaf()) { //child node is leaf
            if (direction.equals("left")) {
                pNode.left = null;
            }
            else {
                pNode.right = null;
            }
        }
        if (cNode.oneChild()) { //child node has one child
            BSTNode ccNode = findChild(cNode, null); // find child node of cNode
            if (direction.equals("left")) {
                pNode.left = ccNode;
            }
            else {
                pNode.right = ccNode;
            }
        }
    }

    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    @Override
    public V remove(K key, V value) {
        BSTNode pNode = findNode(key,true);
        String direction = findDirection(pNode, key);
        BSTNode cNode = findChild(pNode, direction);

        if (!cNode.key.equals(key)) {
            return null;
        }
        if (value == null || cNode.value == value) {
            remove(cNode, pNode, direction);
        }
        size -= 1;
        return cNode.value;
    }
}
