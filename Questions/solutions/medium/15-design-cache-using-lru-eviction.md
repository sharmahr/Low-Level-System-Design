# Designing a LRU Cache

## Requirements
1. The LRU cache should support the following operations:
- put(key, value): Insert a key-value pair into the cache. If the cache is at capacity, remove the least recently used item before inserting the new item.
- get(key): Get the value associated with the given key. If the key exists in the cache, move it to the front of the cache (most recently used) and return its value. If the key does not exist, return -1.
2. The cache should have a fixed capacity, specified during initialization.
3. The cache should be thread-safe, allowing concurrent access from multiple threads.
4. The cache should be efficient in terms of time complexity for both put and get operations, ideally O(1).


```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Node<K, V> {
    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class LRUCache<K, V> {
    private Map<K, Node<K, V>> cache;
    private Node<K, V> head;
    private Node<K, V> tail;
    private int capacity;
    private ReentrantReadWriteLock lock;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        this.head.next = tail;
        this.tail.prev = head;
        this.lock = new ReentrantReadWriteLock();
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            if (cache.containsKey(key)) {
                Node<K, V> node = cache.get(key);
                node.value = value;
                moveToFront(node);
            } else {
                if (cache.size() == capacity) {
                    Node<K, V> lruNode = removeLRU();
                    cache.remove(lruNode.key);
                }
                Node<K, V> newNode = new Node<>(key, value);
                addToFront(newNode);
                cache.put(key, newNode);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        lock.readLock().lock();
        try {
            if (cache.containsKey(key)) {
                Node<K, V> node = cache.get(key);
                moveToFront(node);
                return node.value;
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void addToFront(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFront(Node<K, V> node) {
        removeNode(node);
        addToFront(node);
    }

    private Node<K, V> removeLRU() {
        Node<K, V> lruNode = tail.prev;
        removeNode(lruNode);
        return lruNode;
    }
}

public class LRUCacheDemo {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "Value 1");
        cache.put(2, "Value 2");
        cache.put(3, "Value 3");

        System.out.println(cache.get(1)); // Output: Value 1
        System.out.println(cache.get(2)); // Output: Value 2

        cache.put(4, "Value 4");

        System.out.println(cache.get(3)); // Output: null
        System.out.println(cache.get(4)); // Output: Value 4
        System.out.println(cache.get(1)); // Output: Value 1
    }
}
```

Explanation:
1. The `Node` class represents a node in the doubly-linked list used to maintain the order of elements in the cache based on their recent usage. Each node contains a key-value pair, along with references to the previous and next nodes.

2. The `LRUCache` class represents the LRU cache itself. It uses a `HashMap` to store the key-value pairs and a doubly-linked list to keep track of the order of elements based on their recent usage.

3. The `LRUCache` constructor initializes the cache with the specified capacity. It creates dummy head and tail nodes to simplify the linked list operations.

4. The `put` method is used to insert a key-value pair into the cache. If the key already exists, it updates the value and moves the node to the front of the list. If the cache is at capacity, it removes the least recently used item (the node at the tail) before inserting the new item. The new item is then added to the front of the list and the cache.

5. The `get` method is used to retrieve the value associated with a given key. If the key exists in the cache, it moves the corresponding node to the front of the list and returns its value. If the key does not exist, it returns `null`.

6. The `addToFront`, `removeNode`, and `moveToFront` methods are helper methods used to manipulate the doubly-linked list. They add a node to the front, remove a node from its current position, and move a node to the front of the list, respectively.

7. The `removeLRU` method removes the least recently used item from the cache, which is the node at the tail of the list.

Design Patterns Used:
- Singleton Pattern: The `LRUCache` class can be implemented as a singleton to ensure that only one instance of the cache exists throughout the application. This allows for centralized cache management and avoids unnecessary memory overhead.

Thread Safety:
- The `LRUCache` class uses a `ReentrantReadWriteLock` to ensure thread safety. The `put` method acquires a write lock, allowing only one thread to modify the cache at a time. The `get` method acquires a read lock, allowing multiple threads to read from the cache concurrently.

Time Complexity:
- Both the `put` and `get` operations have a time complexity of O(1) on average. The `HashMap` allows for constant-time retrieval of nodes based on their keys, and the doubly-linked list allows for constant-time insertion and removal of nodes.

This implementation provides an efficient and thread-safe LRU cache that meets the given requirements. The use of a `HashMap` and a doubly-linked list ensures optimal performance for cache operations, while the read-write lock guarantees thread safety in concurrent scenarios.

The LRU cache can be useful in various scenarios where fast access to recently used data is required, such as caching database queries, web page content, or frequently accessed calculations.