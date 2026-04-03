package org.example;

import java.util.Comparator;

public class BST<T> {

    private BSTNode<T> root;
    private Comparator<T> comparator;


    public int comparisons = 0;

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    // 1.Insert
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private BSTNode<T> insertRec(BSTNode<T> node, T data) {
        if (node == null) return new BSTNode<>(data);

        comparisons++; // Conteo para análisis de complejidad
        if (comparator.compare(data, node.data) < 0)
            node.left = insertRec(node.left, data);
        else
            node.right = insertRec(node.right, data);

        return node;
    }

    // 2. Search
    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(BSTNode<T> node, T data) {
        if (node == null) return false;

        comparisons++;
        int cmp = comparator.compare(data, node.data);

        if (cmp == 0) return true;
        if (cmp < 0) return searchRec(node.left, data);
        return searchRec(node.right, data);
    }

    // 3. Delete
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private BSTNode<T> deleteRec(BSTNode<T> node, T data) {
        if (node == null) return null;

        int cmp = comparator.compare(data, node.data);

        if (cmp < 0) {
            node.left = deleteRec(node.left, data);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, data);
        } else {
            // Caso 1 & 2: Nodo hoja o con un solo hijo
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Caso 3: Nodo con dos hijos (Sucesor in-order)
            node.data = minValue(node.right);
            node.right = deleteRec(node.right, node.data);
        }

        return node;
    }

    private T minValue(BSTNode<T> node) {
        T minv = node.data;
        while (node.left != null) {
            minv = node.left.data;
            node = node.left;
        }
        return minv;
    }

    // 4.  Recorridos (In-Order)
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(BSTNode<T> node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.data + " ");
            inOrderRec(node.right);
        }
    }

    // 5.  Cálculo de Altura
    public int height() {
        return heightRec(root);
    }

    private int heightRec(BSTNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }
}