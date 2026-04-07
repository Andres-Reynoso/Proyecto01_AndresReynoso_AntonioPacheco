package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class Heap<T> {

    private T[] heap;
    private int size;
    private int capacity;
    private Comparator<T> comparator;

    public int swaps = 0;

    @SuppressWarnings("unchecked")
    public Heap(int capacity, Comparator<T> comparator) {
        this.capacity = capacity;
        this.heap = (T[]) new Object[capacity];
        this.comparator = comparator;
    }

    public void insert(T value) {
        if (size == capacity) grow();
        heap[size] = value;
        heapifyUp(size++);
    }

    public T extract() {
        if (size == 0) return null;

        T root = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;

        heapifyDown(0);
        return root;
    }

    public long extractWithTime() {
        long ini = System.nanoTime();
        extract();
        return System.nanoTime() - ini;
    }

    // 🔥 CORREGIDO
    public void updatePriority(T element, T newElement) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(element)) {
                heap[i] = newElement;
                heapifyUp(i);
                heapifyDown(i);
                return;
            }
        }
    }

    // 🔥 NUEVO
    public void setComparator(Comparator<T> newComparator) {
        this.comparator = newComparator;
        rebuildHeap();
    }

    private void rebuildHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (comparator.compare(heap[i], heap[p]) < 0) {
                swap(i, p);
                i = p;
            } else break;
        }
    }

    private void heapifyDown(int i) {
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, s = i;

            if (l < size && comparator.compare(heap[l], heap[s]) < 0) s = l;
            if (r < size && comparator.compare(heap[r], heap[s]) < 0) s = r;

            if (s == i) break;

            swap(i, s);
            i = s;
        }
    }

    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
        swaps++;
    }

    private void grow() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    public boolean isEmpty() {
        return size == 0;
    }
}