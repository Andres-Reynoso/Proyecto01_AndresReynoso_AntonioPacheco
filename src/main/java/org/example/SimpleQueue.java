package org.example;

/**
 * Implementación simple de una cola (FIFO).
 *
 * Usa una lista enlazada para almacenar los elementos.
 * Se utiliza principalmente en recorridos por niveles (BFS).
 */
public class SimpleQueue<T> {

    /**
     * Nodo interno de la cola.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front, rear;

    /**
     * Inserta un elemento al final de la cola.
     */
    public void enqueue(T data) {
        Node<T> node = new Node<>(data);
        if (rear != null) rear.next = node;
        rear = node;
        if (front == null) front = rear;
    }

    /**
     * Elimina y devuelve el elemento al inicio de la cola.
     */
    public T dequeue() {
        if (front == null) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }

    /**
     * Indica si la cola está vacía.
     */
    public boolean isEmpty() {
        return front == null;
    }
}