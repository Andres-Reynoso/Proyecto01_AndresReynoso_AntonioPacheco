package org.example;

import java.util.*;

public class MTree<T> {

    private MNode<T> root;
    private int maxChildren;

    public MTree(T rootData, int maxChildren) {
        this.maxChildren = maxChildren;
        this.root = new MNode<>(rootData, maxChildren);
    }

    public MNode<T> getRoot() {
        return root;
    }

    public void addChild(MNode<T> parent, T data) {
        if (parent != null) {
            parent.addChild(new MNode<>(data, maxChildren));
        }
    }

    // 🔹 NUEVO: contar intersecciones por nodo
    public int countFromNode(MNode<T> node) {
        if (node == null) return 0;

        int count = 1;
        for (MNode<T> child : node.children) {
            count += countFromNode(child);
        }
        return count;
    }

    // BFS
    public void levelOrder() {
        if (root == null) return;

        Queue<MNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            MNode<T> current = queue.poll();
            System.out.print(current.data + " ");

            for (MNode<T> child : current.children) {
                queue.add(child);
            }
        }
        System.out.println();
    }

    public int maxDepth() {
        return maxDepthRec(root);
    }

    private int maxDepthRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;

        int max = 0;
        for (MNode<T> child : node.children) {
            max = Math.max(max, maxDepthRec(child));
        }
        return max + 1;
    }

    public int countLeaves() {
        return countLeavesRec(root);
    }

    private int countLeavesRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;

        int count = 0;
        for (MNode<T> child : node.children) {
            count += countLeavesRec(child);
        }
        return count;
    }

    public int countInternalNodes() {
        return countInternalRec(root);
    }

    private int countInternalRec(MNode<T> node) {
        if (node == null || node.children.isEmpty()) return 0;

        int count = 1;
        for (MNode<T> child : node.children) {
            count += countInternalRec(child);
        }
        return count;
    }

    public double branchingFactor() {
        int totalChildren = countAllChildren(root);
        int totalNodes = countNodes(root);

        return (totalNodes == 0) ? 0 : (double) totalChildren / totalNodes;
    }

    private int countAllChildren(MNode<T> node) {
        if (node == null) return 0;

        int count = node.children.size();
        for (MNode<T> child : node.children) {
            count += countAllChildren(child);
        }
        return count;
    }

    private int countNodes(MNode<T> node) {
        if (node == null) return 0;

        int count = 1;
        for (MNode<T> child : node.children) {
            count += countNodes(child);
        }
        return count;
    }
}