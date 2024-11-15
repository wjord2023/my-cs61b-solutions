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
    private double loadFactor;
    private int numBuckets;
    private int numElements;

    /**
     * Constructors
     */
    public MyHashMap() {
        numBuckets = 16;
        loadFactor = 0.75;
        numElements = 0;
        buckets = createTable(numBuckets);
    }

    public MyHashMap(int initialSize) {
        numBuckets = initialSize;
        loadFactor = 0.75;
        numElements = 0;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        numBuckets = initialSize;
        loadFactor = maxLoad;
        numElements = 0;
        buckets = createTable(numBuckets);
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
        Collection<Node>[] buckets = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            buckets[i] = createBucket();
        }
        return buckets;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int hash(K key) {
        return Math.abs(key.hashCode()) % numBuckets;
    }

    private void resize() {
        int newNumBuckets = numBuckets * 2;
        Collection<Node>[] newBuckets = createTable(newNumBuckets);

        for (int i = 0; i < numBuckets; i++) {
           newBuckets[i] = createBucket();
        }

        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int newIndex = hash(node.key);
                newBuckets[newIndex].add(node);
            }
        }
        buckets = newBuckets;
    }

    @Override
    public void clear() {
        buckets = new Collection[numBuckets];
        numElements = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key);
        Collection<Node> bucket = buckets[index];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        numElements++;
        bucket.add(createNode(key, value));
        if (numElements >= loadFactor * numBuckets) {
            resize();
        }
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
        int index = hash(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                bucket.remove(node);
                numElements--;
                return node.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                if (node.value == value) {
                    bucket.remove(node);
                    numElements--;
                    return node.value;
                }
            }
        }
        return null;
    }

    private class HashMapIter implements Iterator<K> {
        int currentBucket;
        Iterator<Node> bucketIterator;

        public HashMapIter() {
            currentBucket = 0;
            bucketIterator = buckets[currentBucket].iterator();
        }

        @Override
        public boolean hasNext() {
            while (currentBucket < numBuckets && !bucketIterator.hasNext()) {
                currentBucket++;
                if (currentBucket < numBuckets) {
                    bucketIterator = buckets[currentBucket].iterator();
                }
            }
            return currentBucket < numBuckets;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return bucketIterator.next().key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIter();
    }
}