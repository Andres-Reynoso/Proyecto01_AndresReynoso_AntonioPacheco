package org.example;

public class AVLNode<T> {

    T data;
    AVLNode<T> left;
    AVLNode<T> right;
    int height;

    public AVLNode(T data) {
        this.data = data;
        this.height = 1;
    }
}