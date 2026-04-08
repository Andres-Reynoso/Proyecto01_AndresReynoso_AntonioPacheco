package org.example;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementación de un Heap genérico basado en arreglo dinámico.
 *
 * Mantiene sus elementos ordenados según un Comparator,
 * lo que permite definir si funciona como min-heap o max-heap.
 *
 * Incluye métricas (swaps) y funcionalidades extra como:
 * - cambio dinámico de criterio
 * - evaluación por criterio externo
 * - actualización de prioridad
 */
public class Heap<T> {

    private ArrayList<T> heap;
    private Comparator<T> comparator;

    // Cantidad de intercambios realizados (útil para análisis)
    private int swaps = 0;

    // Criterio adicional para evaluar elementos
    private CriterioTrafico<T> criterio;

    /**
     * Crea un heap con capacidad inicial y criterio de orden.
     */
    public Heap(int capacidad, Comparator<T> comparator) {
        this.heap = new ArrayList<>(capacidad);
        this.comparator = comparator;
    }

    /**
     * Inserta un elemento y lo posiciona correctamente.
     */
    public void insert(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    /**
     * Extrae el elemento raíz (mayor o menor según el comparator).
     */
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

    /**
     * Indica si el heap está vacío.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Reinicia el contador de swaps.
     */
    public void resetSwaps() {
        swaps = 0;
    }

    /**
     * Devuelve la cantidad de intercambios realizados.
     */
    public int getSwaps() {
        return swaps;
    }

    /**
     * Cambia el criterio de orden del heap en tiempo de ejecución.
     * Reorganiza toda la estructura.
     */
    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        rebuildHeap();
    }

    /**
     * Reconstruye el heap completo.
     * Se usa cuando cambia el comparator.
     */
    private void rebuildHeap() {
        ArrayList<T> copia = new ArrayList<>(heap);
        heap.clear();
        for (T item : copia) {
            insert(item);
        }
    }

    /**
     * Define un criterio externo para evaluar elementos.
     */
    public void setCriterio(CriterioTrafico<T> criterio) {
        this.criterio = criterio;
    }

    /**
     * Recorre el heap y muestra los elementos que cumplen el criterio.
     */
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

    /**
     * Actualiza la prioridad de un elemento ya existente.
     * Luego reordena el heap.
     */
    public void updatePriority(T oldValue, T newValue) {
        int index = heap.indexOf(oldValue);
        if (index == -1) return;

        heap.set(index, newValue);
        heapifyUp(index);
        heapifyDown(index);
    }

    /**
     * Ajusta el elemento hacia arriba hasta restaurar el orden.
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    /**
     * Ajusta el elemento hacia abajo hasta restaurar el orden.
     */
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

    /**
     * Intercambia dos elementos y registra el swap.
     */
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        swaps++;
    }
}