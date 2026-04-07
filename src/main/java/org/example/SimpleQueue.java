package org.example;

public class SimpleQueue<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front, rear;

    public void enqueue(T data) {
        Node<T> node = new Node<>(data);
        if (rear != null) rear.next = node;
        rear = node;
        if (front == null) front = rear;
    }

    public T dequeue() {
        if (front == null) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }
}