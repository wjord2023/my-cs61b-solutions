package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    int size;
    Node root;

    private class Node {
        K key;
        V value;
        Node left, right;

        public Node(K k, V v) {
            key = k;
            value = v;
            left = right = null;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    public BSTMap(K key, V value) {
        size = 1;
        root = new Node(key, value);
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return keySet().contains(key);
    }

    @Override
    public V get(K key) {
        Node node = get(key, root);
        return node == null ? null : node.value;
    }

    private Node get(K key, Node node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return get(key, node.left);
        } else {
            return get(key, node.right);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
        size += 1;
    }

    private Node put(K key, V value, Node node) {
        if (node == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(key, value, node.left);
        } else {
            node.right = put(key, value, node.right);
        }
        return node;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (K key : this) {
            keys.add(key);
        }
        return keys;
    }

    @Override
    public V remove(K key) {
        Node node = get(key, root);
        root = remove(key, root);
        size -= 1;
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key, V value) {
        Node node = get(key, root);
        if (!node.value.equals(value)) {
            return null;
        }
        root = remove(key, root);
        size -= 1;
        return node.value;
    }

    private Node remove(K key, Node node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            node.key = min(node.right).key;
            node.value = min(node.right).value;
            node.right = remove(node.key, node.right);
        } else if (cmp < 0) {
            node.left = remove(key, node.left);
        } else {
            node.right = remove(key, node.right);
        }
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    private class BSTMapIter implements Iterator<K> {
        private Node curr;

        private Stack<Node> stack = new Stack<Node>();

        public BSTMapIter() {
            curr = root;
            leftPush(curr);
        }

        private void leftPush(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = stack.pop();
            leftPush(node.right);
            return node.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }
}
