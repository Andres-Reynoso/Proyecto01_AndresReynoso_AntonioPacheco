package org.example;

/**
 * Nodo utilizado en el Árbol Binario de Búsqueda (BST).
 *
 * Contiene el dato almacenado y referencias a los hijos
 * izquierdo y derecho.
 */
public class BSTNode<T> {

    private T data;
    private BSTNode<T> left;
    private BSTNode<T> right;

    /**
     * Crea un nodo con un valor inicial.
     */
    public BSTNode(T data) {
        this.data = data;
    }

    /**
     * Devuelve el dato almacenado.
     */
    public T getData() {
        return data;
    }

    /**
     * Devuelve el hijo izquierdo.
     */
    public BSTNode<T> getLeft() {
        return left;
    }

    /**
     * Devuelve el hijo derecho.
     */
    public BSTNode<T> getRight() {
        return right;
    }

    /**
     * Asigna el hijo izquierdo.
     */
    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    /**
     * Asigna el hijo derecho.
     */
    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    /**
     * Permite cambiar el dato del nodo.
     */
    public void setData(T data) {
        this.data = data;
    }
}