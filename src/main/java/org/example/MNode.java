package org.example;

import java.util.ArrayList;

public class MNode<T> {

    private T data;
    private ArrayList<MNode<T>> children;
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

    public ArrayList<MNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    public int getCurrentChildrenCount() {
        return children.size();
    }
}