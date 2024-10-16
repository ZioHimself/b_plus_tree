package com.github.ziohimself.b_plus_tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BPlusTreeTest {

    private BPlusTree<Integer, String> tree;

    @BeforeEach
    void setUp() {
        // Create a B+ tree with order 4 (max 3 keys per node)
        tree = new BPlusTree<>(4);
    }

    @Test
    void testInsertAndSearchSingleItem() {
        tree.insert(1, "One");
        assertEquals("One", tree.search(1));
    }

    @Test
    void testInsertAndSearchMultipleItems() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        
        assertEquals("One", tree.search(1));
        assertEquals("Two", tree.search(2));
        assertEquals("Three", tree.search(3));
    }

    @Test
    void testInsertCausingLeafSplit() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        tree.insert(4, "Four");

        assertEquals("One", tree.search(1));
        assertEquals("Two", tree.search(2));
        assertEquals("Three", tree.search(3));
        assertEquals("Four", tree.search(4));
    }

    @Test
    void testInsertCausingMultipleSplits() {
        for (int i = 1; i <= 10; i++) {
            tree.insert(i, "Value" + i);
        }

        for (int i = 1; i <= 10; i++) {
            assertEquals("Value" + i, tree.search(i));
        }
    }

    @Test
    void testInsertDuplicateKeys() {
        tree.insert(1, "One");
        tree.insert(1, "One Updated");

        assertEquals("One Updated", tree.search(1));
    }

    @Test
    void testSearchNonExistentKey() {
        tree.insert(1, "One");
        assertNull(tree.search(2));
    }

    @Test
    void testInsertLargeNumberOfItems() {
        int numItems = 1000;
        for (int i = 0; i < numItems; i++) {
            tree.insert(i, "Value" + i);
        }

        for (int i = 0; i < numItems; i++) {
            assertEquals("Value" + i, tree.search(i));
        }
    }

    @Test
    void testInsertReverseOrder() {
        int numItems = 100;
        for (int i = numItems - 1; i >= 0; i--) {
            tree.insert(i, "Value" + i);
        }

        for (int i = 0; i < numItems; i++) {
            assertEquals("Value" + i, tree.search(i));
        }
    }

    @Test
    void testInsertRandomOrder() {
        int[] keys = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        for (int key : keys) {
            tree.insert(key, "Value" + key);
        }

        for (int key : keys) {
            assertEquals("Value" + key, tree.search(key));
        }
    }

    @Test
    void testPerformanceInsertAndSearch() {
        int numItems = 100000;
        long startTime = System.nanoTime();

        for (int i = 0; i < numItems; i++) {
            tree.insert(i, "Value" + i);
        }

        long endTime = System.nanoTime();
        long insertDuration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        startTime = System.nanoTime();

        for (int i = 0; i < numItems; i++) {
            assertEquals("Value" + i, tree.search(i));
        }

        endTime = System.nanoTime();
        long searchDuration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        System.out.println("Insert time for " + numItems + " items: " + insertDuration + "ms");
        System.out.println("Search time for " + numItems + " items: " + searchDuration + "ms");

        // Add assertions to ensure performance is within acceptable limits
        assertTrue(insertDuration < 5000, "Insert operation took too long");
        assertTrue(searchDuration < 1000, "Search operation took too long");
    }
}

