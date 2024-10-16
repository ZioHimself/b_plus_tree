package com.github.ziohimself.b_plus_tree;

public class Main {
    public static void main(String[] args) {
        BPlusTree<Integer, String> tree = new BPlusTree<>(3);
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        tree.insert(4, "Four");
        tree.insert(5, "Five");
        tree.insert(6, "Six");

        System.out.println(tree.visualize());
    }
}
