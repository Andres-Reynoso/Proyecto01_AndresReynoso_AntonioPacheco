package org.example;

import java.util.Comparator;

public class AVL<T> {

    private AVLNode<T> root;
    private Comparator<T> comparator;

    public int comparisons = 0;
    public int rotations = 0;

    public AVL(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private AVLNode<T> insertRec(AVLNode<T> node, T data) {
        if (node == null) return new AVLNode<>(data);

        comparisons++;
        int cmp = comparator.compare(data, node.data);

        if (cmp < 0) node.left = insertRec(node.left, data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        else return node;

        updateHeight(node);
        return balance(node);
    }

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private AVLNode<T> deleteRec(AVLNode<T> node, T data) {
        if (node == null) return null;

        int cmp = comparator.compare(data, node.data);

        if (cmp < 0) node.left = deleteRec(node.left, data);
        else if (cmp > 0) node.right = deleteRec(node.right, data);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            AVLNode<T> temp = minValueNode(node.right);
            node.data = temp.data;
            node.right = deleteRec(node.right, temp.data);
        }

        updateHeight(node);
        return balance(node);
    }

    private AVLNode<T> minValueNode(AVLNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getHeight(AVLNode<T> node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalanceFactor(AVLNode<T> node) {
        return getHeight(node.left) - getHeight(node.right);
    }

    public int getBalanceFactorRoot() {
        return getBalanceFactor(root);
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        int bf = getBalanceFactor(node);

        if (bf > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);

        if (bf > 1) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (bf < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        if (bf < -1) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        rotations++;
        return x;
    }

    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        rotations++;
        return y;
    }

    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(AVLNode<T> node, T data) {
        if (node == null) return false;

        comparisons++;
        int cmp = comparator.compare(data, node.data);

        if (cmp == 0) return true;
        return (cmp < 0) ? searchRec(node.left, data) : searchRec(node.right, data);
    }

    // RECORRIDOS
    public void inOrder() { inOrderRec(root); System.out.println(); }
    public void preOrder() { preOrderRec(root); System.out.println(); }
    public void postOrder() { postOrderRec(root); System.out.println(); }

    private void inOrderRec(AVLNode<T> node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.data + " ");
            inOrderRec(node.right);
        }
    }

    private void preOrderRec(AVLNode<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderRec(node.left);
            preOrderRec(node.right);
        }
    }

    private void postOrderRec(AVLNode<T> node) {
        if (node != null) {
            postOrderRec(node.left);
            postOrderRec(node.right);
            System.out.print(node.data + " ");
        }
    }

    public int height() {
        return getHeight(root);
    }
}