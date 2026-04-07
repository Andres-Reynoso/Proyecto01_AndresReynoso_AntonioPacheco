package org.example;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T> {

    private BSTNode<T> root;
    private Comparator<T> comparator;

    public int comparisons = 0;

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void resetStats() {
        comparisons = 0;
    }

    // INSERT (iterativo)

    public void insert(T data) {
        if (root == null) {
            root = new BSTNode<>(data);
            return;
        }

        BSTNode<T> current = root;
        BSTNode<T> parent = null;

        while (current != null) {
            parent = current;
            comparisons++;
            int cmp = comparator.compare(data, current.getData());

            if (cmp < 0) {
                current = current.getLeft();
            } else if (cmp > 0) {
                current = current.getRight();
            } else {
                // Duplicado: no insertar
                return;
            }
        }

        int cmp = comparator.compare(data, parent.getData());
        if (cmp < 0) {
            parent.setLeft(new BSTNode<>(data));
        } else {
            parent.setRight(new BSTNode<>(data));
        }
    }

    // SEARCH (iterativo)

    public boolean search(T data) {
        BSTNode<T> current = root;
        while (current != null) {
            comparisons++;
            int cmp = comparator.compare(data, current.getData());
            if (cmp == 0) return true;
            else if (cmp < 0) current = current.getLeft();
            else current = current.getRight();
        }
        return false;
    }

    // DELETE (recursivo)

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private BSTNode<T> deleteRec(BSTNode<T> node, T data) {
        if (node == null) return null;

        comparisons++;
        int cmp = comparator.compare(data, node.getData());

        if (cmp < 0) {
            node.setLeft(deleteRec(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(deleteRec(node.getRight(), data));
        } else {
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            T min = minValue(node.getRight());
            node.setData(min);
            node.setRight(deleteRec(node.getRight(), min));
        }

        return node;
    }

    private T minValue(BSTNode<T> node) {
        while (node.getLeft() != null) {
            comparisons++;
            node = node.getLeft();
        }
        return node.getData();
    }

    // RECORRIDOS


    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(BSTNode<T> node) {
        if (node != null) {
            inOrderRec(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrderRec(node.getRight());
        }
    }

    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }

    private void preOrderRec(BSTNode<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preOrderRec(node.getLeft());
            preOrderRec(node.getRight());
        }
    }

    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }

    private void postOrderRec(BSTNode<T> node) {
        if (node != null) {
            postOrderRec(node.getLeft());
            postOrderRec(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }

    // HEIGHT (iterativo con BFS)

    public int height() {
        if (root == null) return 0;

        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                BSTNode<T> node = queue.poll();
                if (node.getLeft() != null) queue.add(node.getLeft());
                if (node.getRight() != null) queue.add(node.getRight());
            }
            height++;
        }
        return height;
    }

    public BSTNode<T> getRoot() {
        return root;
    }
}