package org.example;

import java.util.Comparator;

/**
 * Implementación de un árbol AVL (auto-balanceado).
 *
 * Mantiene el árbol balanceado después de cada inserción o eliminación,
 * garantizando operaciones en tiempo logarítmico.
 *
 * Usa un Comparator para definir el orden de los elementos.
 */
public class AVL<T> {

    private AVLNode<T> root;
    private Comparator<T> comparator;

    // Métricas para análisis
    public int comparisons = 0;
    public int rotations = 0;

    /**
     * Crea el árbol AVL con un criterio de comparación.
     */
    public AVL(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Inserta un elemento en el árbol.
     */
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private AVLNode<T> insertRec(AVLNode<T> node, T data) {
        if (node == null) return new AVLNode<>(data);

        comparisons++;
        int cmp = comparator.compare(data, node.getData());

        if (cmp < 0) node.setLeft(insertRec(node.getLeft(), data));
        else if (cmp > 0) node.setRight(insertRec(node.getRight(), data));
        else return node;

        updateHeight(node);
        return balance(node);
    }

    /**
     * Elimina un elemento del árbol.
     */
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private AVLNode<T> deleteRec(AVLNode<T> node, T data) {
        if (node == null) return null;

        int cmp = comparator.compare(data, node.getData());

        if (cmp < 0) {
            node.setLeft(deleteRec(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(deleteRec(node.getRight(), data));
        } else {
            // Nodo encontrado
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            // Dos hijos: se toma el menor del subárbol derecho
            AVLNode<T> temp = minValueNode(node.getRight());
            node.setData(temp.getData());
            node.setRight(deleteRec(node.getRight(), temp.getData()));
        }

        updateHeight(node);
        return balance(node);
    }

    /**
     * Encuentra el nodo con el valor mínimo.
     */
    private AVLNode<T> minValueNode(AVLNode<T> node) {
        while (node.getLeft() != null) node = node.getLeft();
        return node;
    }

    /**
     * Actualiza la altura de un nodo.
     */
    private void updateHeight(AVLNode<T> node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }

    private int getHeight(AVLNode<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    /**
     * Calcula el factor de balance.
     */
    private int getBalanceFactor(AVLNode<T> node) {
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    /**
     * Devuelve el factor de balance de la raíz.
     */
    public int getBalanceFactorRoot() {
        return getBalanceFactor(root);
    }

    /**
     * Aplica rotaciones si el nodo está desbalanceado.
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        int bf = getBalanceFactor(node);

        // Left Left
        if (bf > 1 && getBalanceFactor(node.getLeft()) >= 0)
            return rightRotate(node);

        // Left Right
        if (bf > 1) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Right
        if (bf < -1 && getBalanceFactor(node.getRight()) <= 0)
            return leftRotate(node);

        // Right Left
        if (bf < -1) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Rotación simple a la derecha.
     */
    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = y.getLeft();
        AVLNode<T> t2 = x.getRight();

        x.setRight(y);
        y.setLeft(t2);

        updateHeight(y);
        updateHeight(x);

        rotations++;
        return x;
    }

    /**
     * Rotación simple a la izquierda.
     */
    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = x.getRight();
        AVLNode<T> t2 = y.getLeft();

        y.setLeft(x);
        x.setRight(t2);

        updateHeight(x);
        updateHeight(y);

        rotations++;
        return y;
    }

    /**
     * Busca un elemento en el árbol.
     */
    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(AVLNode<T> node, T data) {
        if (node == null) return false;

        comparisons++;
        int cmp = comparator.compare(data, node.getData());

        if (cmp == 0) return true;
        return (cmp < 0) ? searchRec(node.getLeft(), data)
                : searchRec(node.getRight(), data);
    }

    // RECORRIDOS

    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }

    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(AVLNode<T> node) {
        if (node != null) {
            inOrderRec(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrderRec(node.getRight());
        }
    }

    private void preOrderRec(AVLNode<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preOrderRec(node.getLeft());
            preOrderRec(node.getRight());
        }
    }

    private void postOrderRec(AVLNode<T> node) {
        if (node != null) {
            postOrderRec(node.getLeft());
            postOrderRec(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }

    /**
     * Devuelve la altura del árbol.
     */
    public int height() {
        return getHeight(root);
    }
}