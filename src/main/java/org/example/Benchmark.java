package org.example;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Benchmark {

    private static final int[] N_VALUES = {1000, 10000, 50000, 100000};
    private static final int REPETICIONES = 10;
    private static final Random random = new Random();

    private static Evento generarEvento(int id) {
        return new Evento(
                id,
                random.nextInt(10),     // prioridad
                random.nextInt(100),    // congestion
                System.currentTimeMillis(),
                random.nextInt(10)      // riesgo
        );
    }

    public static void ejecutar() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {

            // 🔥 Header claro (importante)
            writer.println("N,Operacion,Tiempo_ns,Valor,Estado");

            for (int n : N_VALUES) {

                System.out.println("Procesando N = " + n);

                long totalBSTInsert = 0;
                long totalAVLInsert = 0;
                long totalBSTSearch = 0;
                long totalAVLSearch = 0;

                int totalBSTComp = 0;
                int totalAVLComp = 0;
                int totalAVLRot = 0;

                for (int r = 0; r < REPETICIONES; r++) {

                    List<Integer> datos = new ArrayList<>();
                    for (int i = 0; i < n; i++) datos.add(i);
                    Collections.shuffle(datos);

                    BST<Integer> bst = new BST<>(Integer::compareTo);
                    AVL<Integer> avl = new AVL<>(Integer::compareTo);

                    // 🔹 INSERT
                    long iniBST = System.nanoTime();
                    for (int val : datos) bst.insert(val);
                    long finBST = System.nanoTime();

                    long iniAVL = System.nanoTime();
                    for (int val : datos) avl.insert(val);
                    long finAVL = System.nanoTime();

                    totalBSTInsert += (finBST - iniBST);
                    totalAVLInsert += (finAVL - iniAVL);

                    // 🔹 SEARCH
                    int target = datos.get(random.nextInt(n));

                    long iniSB = System.nanoTime();
                    bst.search(target);
                    long finSB = System.nanoTime();

                    long iniSA = System.nanoTime();
                    avl.search(target);
                    long finSA = System.nanoTime();

                    totalBSTSearch += (finSB - iniSB);
                    totalAVLSearch += (finSA - iniSA);

                    // 🔹 MÉTRICAS
                    totalBSTComp += bst.comparisons;
                    totalAVLComp += avl.comparisons;
                    totalAVLRot += avl.rotations;
                }

                // 🔥 INSERT
                writer.println(n + ",BST_INSERT," + (totalBSTInsert / REPETICIONES) + "," + (totalBSTComp / REPETICIONES) + ",OK");
                writer.println(n + ",AVL_INSERT," + (totalAVLInsert / REPETICIONES) + "," + (totalAVLComp / REPETICIONES) + ",OK");

                // 🔥 SEARCH
                writer.println(n + ",BST_SEARCH," + (totalBSTSearch / REPETICIONES) + ",- ,OK");
                writer.println(n + ",AVL_SEARCH," + (totalAVLSearch / REPETICIONES) + ",- ,OK");

                // 🔥 ROTACIONES (separado correctamente)
                writer.println(n + ",AVL_ROTACIONES,-," + (totalAVLRot / REPETICIONES) + ",OK");

                // 🔥 HEAP EVENTOS
                Heap<Evento> heap = new Heap<>(10000, EventoComparators.porPrioridad);

                for (int i = 0; i < 10000; i++) {
                    heap.insert(generarEvento(i));
                }

                long totalExtract = 0;
                for (int i = 0; i < 10000; i++) {
                    totalExtract += heap.extractWithTime();
                }

                writer.println(n + ",HEAP_EVENTOS," + (totalExtract / 10000) + ",- ,OK");

                // 🔥 LISTA EVENTOS
                List<Evento> lista = new ArrayList<>();

                long iniLista = System.nanoTime();
                for (int i = 0; i < 10000; i++) {
                    lista.add(generarEvento(i));
                    lista.sort(EventoComparators.porPrioridad);
                }
                long finLista = System.nanoTime();

                writer.println(n + ",LISTA_EVENTOS," + (finLista - iniLista) + ",- ,OK");
            }

            System.out.println("Benchmark completado.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}