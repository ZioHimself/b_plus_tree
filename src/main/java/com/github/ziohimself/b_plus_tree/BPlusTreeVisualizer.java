package com.github.ziohimself.b_plus_tree;

import java.util.ArrayList;
import java.util.List;

public class BPlusTreeVisualizer {
    private static final String HORIZONTAL_LINE = "─";
    private static final String VERTICAL_LINE = "│";
    private static final String TOP_LEFT_CORNER = "┌";
    private static final String TOP_RIGHT_CORNER = "┐";
    private static final String BOTTOM_LEFT_CORNER = "└";
    private static final String BOTTOM_RIGHT_CORNER = "┘";
    private static final String JUNCTION = "├";
    private static final String RIGHT_JUNCTION = "┤";

    public static <K extends Comparable<K>, V> String visualize(BPlusTree<K, V> tree) {
        StringBuilder sb = new StringBuilder();
        visualizeNode(tree.getRoot(), "", true, sb);
        return sb.toString();
    }

    private static <K extends Comparable<K>, V> void visualizeNode(BPlusTree<K, V>.Node node, String prefix, boolean isTail, StringBuilder sb) {
        sb.append(prefix);
        sb.append(isTail ? BOTTOM_LEFT_CORNER : TOP_LEFT_CORNER);
        sb.append(HORIZONTAL_LINE);

        String nodeContent = String.join(", ", node.keys.stream().map(Object::toString).toArray(String[]::new));
        sb.append(nodeContent);
        sb.append("\n");

        if (node instanceof BPlusTree<K, V>.InternalNode) {
            BPlusTree<K, V>.InternalNode internalNode = (BPlusTree<K, V>.InternalNode) node;
            List<BPlusTree<K, V>.Node> children = internalNode.children;

            for (int i = 0; i < children.size() - 1; i++) {
                visualizeNode(children.get(i), prefix + (isTail ? "    " : VERTICAL_LINE + "   "), false, sb);
            }

            if (children.size() > 0) {
                visualizeNode(children.get(children.size() - 1), prefix + (isTail ? "    " : VERTICAL_LINE + "   "), true, sb);
            }
        }
    }
}
