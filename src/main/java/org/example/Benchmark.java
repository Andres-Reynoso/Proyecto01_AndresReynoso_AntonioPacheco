package org.example;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Ejecuta pruebas de rendimiento sobre las estructuras del proyecto.
 *
 * Se evalúan principalmente:
 * - Inserción y búsqueda en BST y AVL
 * - Comparación de alturas y operaciones internas
 * - Comportamiento en peor caso (BST degenerado)
 * - Rendimiento de Heap vs lista ordenada
 *
 * Los resultados se guardan en un archivo CSV.
 */
public class Benchmark {

    // Tamaños de entrada a evaluar
    private static final int[] N_VALUES = {1000, 10000, 50000, 100000};

    // Cantidad de repeticiones para promediar resultados
    private static final int REPETICIONES = 10;

    private static final Random random = new Random();

    /**
     * Genera un evento aleatorio para pruebas del heap.
     */
    private static Evento generarEvento(int id) {
        return new Evento(
                id,
                random.nextInt(10),
                random.nextInt(100),
                System.currentTimeMillis(),
                random.nextInt(10)
        );
    }

    /**
     * Ejecuta todo el benchmark y escribe los resultados en un archivo CSV.
     *
     * Para cada tamaño N:
     * - Inserta datos aleatorios en BST y AVL
     * - Mide tiempos de inserción y búsqueda
     * - Registra comparaciones, rotaciones y altura
     * - Evalúa peor caso del BST (datos ordenados)
     * - Mide extracción en heap
     * - Compara heap contra ordenamiento de lista
     */
    public static void ejecutar() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {

            writer.println("N,Operacion,Tiempo_ns,Valor,Estado,Descripcion");

            for (int n : N_VALUES) {

                System.out.println("Procesando N = " + n);

                long totalBSTInsert = 0, totalAVLInsert = 0;
                long totalBSTSearch = 0, totalAVLSearch = 0;
                int totalBSTComp = 0, totalAVLComp = 0, totalAVLRot = 0;
                int totalBSTHeight = 0, totalAVLHeight = 0;

                for (int r = 0; r < REPETICIONES; r++) {

                    // Generación de datos aleatorios
                    List<Integer> datos = new ArrayList<>();
                    for (int i = 0; i < n; i++) datos.add(i);
                    Collections.shuffle(datos);

                    BST<Integer> bst = new BST<>(Integer::compareTo);
                    AVL<Integer> avl = new AVL<>(Integer::compareTo);

                    // ========================
                    // INSERCIÓN
                    // ========================

                    bst.resetStats();
                    avl.comparisons = 0;
                    avl.rotations = 0;

                    long iniBST = System.nanoTime();
                    for (int val : datos) bst.insert(val);
                    long finBST = System.nanoTime();

                    long iniAVL = System.nanoTime();
                    for (int val : datos) avl.insert(val);
                    long finAVL = System.nanoTime();

                    totalBSTInsert += (finBST - iniBST);
                    totalAVLInsert += (finAVL - iniAVL);

                    totalBSTComp += bst.comparisons;
                    totalAVLComp += avl.comparisons;
                    totalAVLRot += avl.rotations;

                    totalBSTHeight += bst.height();
                    totalAVLHeight += avl.height();

                    // ========================
                    // BÚSQUEDA
                    // ========================

                    int target = datos.get(random.nextInt(n));

                    bst.resetStats();
                    avl.comparisons = 0;

                    long iniSB = System.nanoTime();
                    bst.search(target);
                    long finSB = System.nanoTime();

                    long iniSA = System.nanoTime();
                    avl.search(target);
                    long finSA = System.nanoTime();

                    totalBSTSearch += (finSB - iniSB);
                    totalAVLSearch += (finSA - iniSA);
                }

                // Promedios
                writer.println(n + ",BST_INSERT," + (totalBSTInsert / REPETICIONES) + "," + (totalBSTComp / REPETICIONES) + ",OK,Insercion promedio");
                writer.println(n + ",AVL_INSERT," + (totalAVLInsert / REPETICIONES) + "," + (totalAVLComp / REPETICIONES) + ",OK,Insercion promedio");

                writer.println(n + ",BST_SEARCH," + (totalBSTSearch / REPETICIONES) + ",-,OK,Busqueda promedio");
                writer.println(n + ",AVL_SEARCH," + (totalAVLSearch / REPETICIONES) + ",-,OK,Busqueda promedio");

                writer.println(n + ",AVL_ROTACIONES,-," + (totalAVLRot / REPETICIONES) + ",OK,Rotaciones promedio");

                writer.println(n + ",BST_ALTURA,-," + (totalBSTHeight / REPETICIONES) + ",OK,Altura promedio");
                writer.println(n + ",AVL_ALTURA,-," + (totalAVLHeight / REPETICIONES) + ",OK,Altura promedio");

                // ========================
                // PEOR CASO BST
                // ========================

                BST<Integer> bstWorst = new BST<>(Integer::compareTo);

                long iniBSTWorst = System.nanoTime();
                for (int i = 0; i < n; i++) bstWorst.insert(i);
                long finBSTWorst = System.nanoTime();

                writer.println(n + ",BST_INSERT_PEOR," + (finBSTWorst - iniBSTWorst) + "," + bstWorst.comparisons + ",OK,Insercion ordenada");
                writer.println(n + ",BST_ALTURA_PEOR,-," + bstWorst.height() + ",OK,Altura degenerada");

                // ========================
                // HEAP
                // ========================

                Heap<Evento> heap = new Heap<>(10000, EventoComparators.porPrioridad);

                for (int i = 0; i < 10000; i++) {
                    heap.insert(generarEvento(i));
                }

                heap.resetSwaps();

                long totalExtractTime = 0;

                for (int i = 0; i < 10000; i++) {
                    long start = System.nanoTime();
                    heap.extract();
                    long end = System.nanoTime();

                    totalExtractTime += (end - start);
                }

                writer.println(n + ",HEAP_EXTRACT_TIME," + (totalExtractTime / 10000) + ",-,OK,Tiempo promedio extraccion");
                writer.println(n + ",HEAP_SWAPS,-," + (heap.getSwaps() / 10000) + ",OK,Swaps promedio por extraccion");

                // ========================
                // LISTA VS HEAP
                // ========================

                List<Evento> lista = new ArrayList<>();

                long iniLista = System.nanoTime();

                for (int i = 0; i < 10000; i++) {
                    lista.add(generarEvento(i));
                }

                Collections.sort(lista, EventoComparators.porPrioridad);

                long finLista = System.nanoTime();

                writer.println(n + ",LISTA_SORT," + (finLista - iniLista) + ",-,OK,Ordenamiento total de lista");
            }

            System.out.println("Benchmark completado correctamente.");

        } catch (Exception e) {
            System.out.println("Error en benchmark: " + e.getMessage());
        }
    }
}