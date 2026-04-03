package org.example;

import java.util.ArrayList;


public class MNode<T> {

    T data;
    ArrayList<MNode<T>> children;

    public MNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(MNode<T> child) {
        children.add(child);
    }
}