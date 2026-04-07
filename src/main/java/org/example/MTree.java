package org.example;

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

    public void levelOrder() {
        if (root == null) return;

        SimpleQueue<MNode<T>> queue = new SimpleQueue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            MNode<T> current = queue.dequeue();
            System.out.print(current.getData() + " ");

            for (MNode<T> child : current.getChildren()) {
                queue.enqueue(child);
            }
        }
        System.out.println();
    }

    public int maxDepth() {
        return maxDepthRec(root);
    }

    private int maxDepthRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;

        int max = 0;
        for (MNode<T> child : node.getChildren()) {
            max = Math.max(max, maxDepthRec(child));
        }
        return max + 1;
    }

    public int countLeaves() {
        return countLeavesRec(root);
    }

    private int countLeavesRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;

        int count = 0;
        for (MNode<T> child : node.getChildren()) {
            count += countLeavesRec(child);
        }
        return count;
    }

    public int countInternalNodes() {
        return countInternalRec(root);
    }

    private int countInternalRec(MNode<T> node) {
        if (node == null || node.getChildren().isEmpty()) return 0;

        int count = 1;
        for (MNode<T> child : node.getChildren()) {
            count += countInternalRec(child);
        }
        return count;
    }

    public double branchingFactor() {
        int totalChildren = countAllChildren(root);
        int totalNodes = countNodes(root);

        return (double) totalChildren / totalNodes;
    }

    private int countAllChildren(MNode<T> node) {
        if (node == null) return 0;

        int count = node.getChildren().size();
        for (MNode<T> child : node.getChildren()) {
            count += countAllChildren(child);
        }
        return count;
    }

    private int countNodes(MNode<T> node) {
        if (node == null) return 0;

        int count = 1;
        for (MNode<T> child : node.getChildren()) {
            count += countNodes(child);
        }
        return count;
    }
}