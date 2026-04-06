package org.example;

import java.util.ArrayList;

public class MNode<T> {

    T data;
    ArrayList<MNode<T>> children;
    private int maxChildren;

    public MNode(T data, int maxChildren) {
        this.data = data;
        this.children = new ArrayList<>();
        this.maxChildren = maxChildren;
    }

    public void addChild(MNode<T> child) {
        if (children.size() >= maxChildren) {
            throw new IllegalStateException("Máximo de hijos alcanzado");
        }
        children.add(child);
    }
}