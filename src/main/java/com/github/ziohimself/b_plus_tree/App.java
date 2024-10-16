package com.github.ziohimself.b_plus_tree;

public class App {
    public static void main(String[] args) {
        BPlusTree<Integer, String> tree = new BPlusTree<>(3); // Create a B+ tree with order 3

        // Insert some key-value pairs
        tree.insert(5, "Five");
        tree.insert(3, "Three");
        tree.insert(7, "Seven");
        tree.insert(1, "One");
        tree.insert(9, "Nine");

        // Search for values
        System.out.println("Value for key 3: " + tree.search(3));
        System.out.println("Value for key 7: " + tree.search(7));
        System.out.println("Value for key 10: " + tree.search(10)); // Should return null
    }
}
