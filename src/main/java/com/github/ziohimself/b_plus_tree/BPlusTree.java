package com.github.ziohimself.b_plus_tree;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree<K extends Comparable<K>, V> {
    private Node root;
    private int order;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new LeafNode();
    }

    /**
     * Inserts a key-value pair into the B+ tree.
     * @param key The key to insert.
     * @param value The value associated with the key.
     */
    public void insert(K key, V value) {
        // Attempt to insert the key-value pair into the root
        Node newNode = root.insert(key, value);
        
        // If a new node was returned, the root was split
        if (newNode != null) {
            // Create a new root
            InternalNode newRoot = new InternalNode();
            // Add the first key of the new node as the splitting key
            newRoot.keys.add(newNode.keys.get(0));
            // Add the old root as the left child
            newRoot.children.add(root);
            // Add the new node as the right child
            newRoot.children.add(newNode);
            // Update the root
            root = newRoot;
        }
    }

    /**
     * Searches for a value associated with the given key.
     * @param key The key to search for.
     * @return The value associated with the key, or null if not found.
     */
    public V search(K key) {
        // Delegate the search to the root node
        return root.search(key);
    }

    abstract class Node {
        List<K> keys;

        Node() {
            this.keys = new ArrayList<>();
        }

        abstract Node insert(K key, V value);
        abstract V search(K key);
    }

    class InternalNode extends Node {
        List<Node> children;

        InternalNode() {
            super();
            this.children = new ArrayList<>();
        }

        @Override
        Node insert(K key, V value) {
            int index = 0;
            // Find the correct child to insert into
            while (index < keys.size() && key.compareTo(keys.get(index)) >= 0) {
                index++;
            }
            // Recursively insert into the appropriate child
            Node newNode = children.get(index).insert(key, value);
            
            // If no split occurred in the child, we're done
            if (newNode == null) {
                return null;
            }
            
            // Otherwise, insert the new key and child
            keys.add(index, newNode.keys.get(0));
            children.add(index + 1, newNode);
            
            // Check if this node now needs to be split
            if (keys.size() > order - 1) {
                return split();
            }
            return null;
        }

        @Override
        V search(K key) {
            int index = 0;
            // Find the correct child to search in
            while (index < keys.size() && key.compareTo(keys.get(index)) >= 0) {
                index++;
            }
            // Recursively search in the appropriate child
            return children.get(index).search(key);
        }

        Node split() {
            int mid = keys.size() / 2;
            InternalNode newNode = new InternalNode();
            // Move the right half of keys to the new node
            newNode.keys = new ArrayList<>(keys.subList(mid + 1, keys.size()));
            // Move the right half of children to the new node
            newNode.children = new ArrayList<>(children.subList(mid + 1, children.size()));
            // Keep the left half of keys in this node
            keys = new ArrayList<>(keys.subList(0, mid));
            // Keep the left half of children in this node
            children = new ArrayList<>(children.subList(0, mid + 1));
            return newNode;
        }
    }

    class LeafNode extends Node {
        List<V> values;
        LeafNode next;

        LeafNode() {
            super();
            this.values = new ArrayList<>();
            this.next = null;
        }

        @Override
        Node insert(K key, V value) {
            int index = 0;
            // Find the correct position to insert the new key
            while (index < keys.size() && key.compareTo(keys.get(index)) > 0) {
                index++;
            }
            // Insert the key and value at the correct position
            keys.add(index, key);
            values.add(index, value);
            
            // Check if this node needs to be split
            if (keys.size() > order - 1) {
                return split();
            }
            return null;
        }

        @Override
        V search(K key) {
            int index = 0;
            // Find the key in this leaf node
            while (index < keys.size() && !key.equals(keys.get(index))) {
                index++;
            }
            // Return the value if found, null otherwise
            return index == keys.size() ? null : values.get(index);
        }

        Node split() {
            int mid = keys.size() / 2;
            LeafNode newNode = new LeafNode();
            // Move the right half of keys to the new node
            newNode.keys = new ArrayList<>(keys.subList(mid, keys.size()));
            // Move the right half of values to the new node
            newNode.values = new ArrayList<>(values.subList(mid, values.size()));
            // Keep the left half of keys in this node
            keys = new ArrayList<>(keys.subList(0, mid));
            // Keep the left half of values in this node
            values = new ArrayList<>(values.subList(0, mid));
            // Update the next pointers to maintain the linked list of leaf nodes
            newNode.next = this.next;
            this.next = newNode;
            return newNode;
        }
    }

    // Add this method to expose the root node
    Node getRoot() {
        return root;
    }

    // Add this method for visualization
    public String visualize() {
        return BPlusTreeVisualizer.visualize(this);
    }
}
