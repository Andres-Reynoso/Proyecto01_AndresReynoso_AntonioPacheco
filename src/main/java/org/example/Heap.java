package org.example;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {

    private ArrayList<T> heap;
    private Comparator<T> comparator;

    // Métricas
    private int swaps = 0;

    // Criterio dinámico — usa la interfaz funcional propia del proyecto
    private CriterioTrafico<T> criterio;

    public Heap(int capacidad, Comparator<T> comparator) {
        this.heap = new ArrayList<>(capacidad);
        this.comparator = comparator;
    }

    // =====================
    // OPERACIONES BÁSICAS
    // =====================

    public void insert(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    public T extract() {
        if (heap.isEmpty()) return null;

        T root = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return root;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // =====================
    // TIEMPO DE EXTRACCIÓN (Benchmark)
    // =====================

    public T extractWithTime() {
        long start = System.nanoTime();
        T result = extract();
        long end = System.nanoTime();
        System.out.println("Tiempo de extracción (ns): " + (end - start));
        return result;
    }

    // =====================
    // MÉTRICAS
    // =====================

    public int getSwaps() {
        return swaps;
    }

    // =====================
    // COMPARATOR DINÁMICO
    // =====================

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        rebuildHeap();
    }

    private void rebuildHeap() {
        ArrayList<T> copia = new ArrayList<>(heap);
        heap.clear();
        for (T item : copia) {
            insert(item);
        }
    }

    // =====================
    // CRITERIO (interfaz funcional propia)
    // =====================

    public void setCriterio(CriterioTrafico<T> criterio) {
        this.criterio = criterio;
    }

    public void procesarConCriterio(int umbral) {
        if (criterio == null) {
            throw new IllegalStateException("Criterio no definido");
        }
        for (T item : heap) {
            if (criterio.evaluar(item, umbral)) {
                System.out.println("Cumple criterio: " + item);
            }
        }
    }

    // =====================
    // UPDATE PRIORITY
    // =====================

    public void updatePriority(T oldValue, T newValue) {
        int index = heap.indexOf(oldValue);
        if (index == -1) return;

        heap.set(index, newValue);
        heapifyUp(index);
        heapifyDown(index);
    }

    // =====================
    // HEAPIFY
    // =====================

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int best = index;

            if (left < size && comparator.compare(heap.get(left), heap.get(best)) < 0) {
                best = left;
            }
            if (right < size && comparator.compare(heap.get(right), heap.get(best)) < 0) {
                best = right;
            }
            if (best == index) break;

            swap(index, best);
            index = best;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        swaps++;
    }
}