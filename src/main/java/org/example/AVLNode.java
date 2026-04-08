package org.example;

/**
 * Nodo utilizado en el árbol AVL.
 *
 * Guarda el dato, referencias a los hijos y la altura,
 * que es necesaria para calcular el balance del árbol.
 */
public class AVLNode<T> {

    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;

    /**
     * Crea un nodo con un valor inicial.
     * La altura comienza en 1.
     */
    public AVLNode(T data) {
        this.data = data;
        this.height = 1;
    }

    /**
     * Devuelve el dato almacenado.
     */
    public T getData() { return data; }

    /**
     * Devuelve el hijo izquierdo.
     */
    public AVLNode<T> getLeft() { return left; }

    /**
     * Devuelve el hijo derecho.
     */
    public AVLNode<T> getRight() { return right; }

    /**
     * Devuelve la altura del nodo.
     */
    public int getHeight() { return height; }

    /**
     * Permite cambiar el dato del nodo.
     */
    public void setData(T data) { this.data = data; }

    /**
     * Asigna el hijo izquierdo.
     */
    public void setLeft(AVLNode<T> left) { this.left = left; }

    /**
     * Asigna el hijo derecho.
     */
    public void setRight(AVLNode<T> right) { this.right = right; }

    /**
     * Actualiza la altura del nodo.
     */
    public void setHeight(int height) { this.height = height; }
}