package org.example;

import java.util.ArrayList;

/**
 * Representa un nodo dentro de un árbol multi-camino.
 *
 * Cada nodo almacena un dato y una lista de hijos,
 * con un límite máximo definido.
 */
public class MNode<T> {

    private T data;
    private ArrayList<MNode<T>> children;
    private int maxChildren;

    /**
     * Crea un nodo con un valor y límite de hijos.
     */
    public MNode(T data, int maxChildren) {
        this.data = data;
        this.children = new ArrayList<>();
        this.maxChildren = maxChildren;
    }

    /**
     * Agrega un hijo al nodo.
     * Lanza error si se supera el límite.
     */
    public void addChild(MNode<T> child) {
        if (children.size() >= maxChildren) {
            throw new IllegalStateException("Máximo de hijos alcanzado");
        }
        children.add(child);
    }

    /**
     * Devuelve la lista de hijos del nodo.
     */
    public ArrayList<MNode<T>> getChildren() {
        return children;
    }

    /**
     * Devuelve el dato almacenado.
     */
    public T getData() {
        return data;
    }

    /**
     * Permite cambiar el dato del nodo.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Devuelve el número máximo de hijos permitidos.
     */
    public int getMaxChildren() {
        return maxChildren;
    }

    /**
     * Devuelve la cantidad actual de hijos.
     */
    public int getCurrentChildrenCount() {
        return children.size();
    }
}