package org.example;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {

    private ArrayList<T> heap;
    private Comparator<T> comparator;

    // Métrica: número total de intercambios realizados
    private int swaps = 0;

    // Criterio dinámico para evaluación de elementos
    private CriterioTrafico<T> criterio;

    public Heap(int capacidad, Comparator<T> comparator) {
        this.heap = new ArrayList<>(capacidad);
        this.comparator = comparator;
    }

    // Inserta un elemento y mantiene la propiedad de heap
    public void insert(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    // Extrae el elemento con mayor prioridad según el comparator
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

    // Reinicia el contador de swaps para mediciones independientes
    public void resetSwaps() {
        swaps = 0;
    }

    // Devuelve el número total de intercambios realizados
    public int getSwaps() {
        return swaps;
    }

    // Permite cambiar el criterio de orden dinámicamente
    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        rebuildHeap();
    }

    // Reconstruye el heap cuando cambia el comparator
    private void rebuildHeap() {
        ArrayList<T> copia = new ArrayList<>(heap);
        heap.clear();
        for (T item : copia) {
            insert(item);
        }
    }

    public void setCriterio(CriterioTrafico<T> criterio) {
        this.criterio = criterio;
    }

    // Evalúa los elementos del heap según un criterio externo
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

    // Actualiza la prioridad de un elemento existente
    public void updatePriority(T oldValue, T newValue) {
        int index = heap.indexOf(oldValue);
        if (index == -1) return;

        heap.set(index, newValue);
        heapifyUp(index);
        heapifyDown(index);
    }

    // Reorganiza hacia arriba
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    // Reorganiza hacia abajo
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

    // Intercambia dos posiciones y contabiliza el swap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        swaps++;
    }
}