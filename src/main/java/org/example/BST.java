package org.example;

import java.util.Comparator;

public class BST<T> {

    private BSTNode<T> root;
    private Comparator<T> comparator;

    public int comparisons = 0;

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // INSERT SIN DUPLICADOS
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private BSTNode<T> insertRec(BSTNode<T> node, T data) {
        if (node == null) return new BSTNode<>(data);

        comparisons++;
        int cmp = comparator.compare(data, node.data);

        if (cmp < 0) node.left = insertRec(node.left, data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        // si cmp == 0 → ignorar duplicado

        return node;
    }

    // SEARCH
    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(BSTNode<T> node, T data) {
        if (node == null) return false;

        comparisons++;
        int cmp = comparator.compare(data, node.data);

        if (cmp == 0) return true;
        return (cmp < 0) ? searchRec(node.left, data) : searchRec(node.right, data);
    }

    // DELETE
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private BSTNode<T> deleteRec(BSTNode<T> node, T data) {
        if (node == null) return null;

        int cmp = comparator.compare(data, node.data);

        if (cmp < 0) node.left = deleteRec(node.left, data);
        else if (cmp > 0) node.right = deleteRec(node.right, data);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            node.data = minValue(node.right);
            node.right = deleteRec(node.right, node.data);
        }

        return node;
    }

    private T minValue(BSTNode<T> node) {
        while (node.left != null) node = node.left;
        return node.data;
    }

    // RECORRIDOS
    public void inOrder() { inOrderRec(root); System.out.println(); }
    public void preOrder() { preOrderRec(root); System.out.println(); }
    public void postOrder() { postOrderRec(root); System.out.println(); }

    private void inOrderRec(BSTNode<T> node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.data + " ");
            inOrderRec(node.right);
        }
    }

    private void preOrderRec(BSTNode<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderRec(node.left);
            preOrderRec(node.right);
        }
    }

    private void postOrderRec(BSTNode<T> node) {
        if (node != null) {
            postOrderRec(node.left);
            postOrderRec(node.right);
            System.out.print(node.data + " ");
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(BSTNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }
}