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
    public Heap(int initialCapacity, Comparator<T> comparator) {
        this.capacity = initialCapacity;
        this.size = 0;
        this.heap = (T[]) new Object[initialCapacity];
        this.comparator = comparator;
    }

    public void insert(T value) {
        if (size == capacity) grow();

        heap[size] = value;
        heapifyUp(size);
        size++;
    }

    public T extract() {
        if (size == 0) return null;

        T root = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        heapifyDown(0);
        return root;
    }

    // actualizar prioridad
    public void updatePriority(T element) {
        int index = findIndex(element);
        if (index == -1) return;

        heapifyUp(index);
        heapifyDown(index);
    }

    private int findIndex(T element) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(element)) return i;
        }
        return -1;
    }

    public void setComparator(Comparator<T> newComparator) {
        this.comparator = newComparator;
        for (int i = (size / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;

            if (comparator.compare(heap[i], heap[parent]) < 0) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    private void heapifyDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && comparator.compare(heap[left], heap[smallest]) < 0)
                smallest = left;

            if (right < size && comparator.compare(heap[right], heap[smallest]) < 0)
                smallest = right;

            if (smallest == i) break;

            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        swaps++;
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    public boolean isEmpty() {
        return size == 0;
    }
}