package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Benchmark {

    private static final int[] N_VALUES = {1000, 10000, 50000, 100000};
    private static final int REPETICIONES = 10;
    private static final Random random = new Random();

    public static void ejecutar() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {

            writer.println("N,Estructura,Tiempo_ns,Estado");

            for (int n : N_VALUES) {

                System.out.println("Procesando N = " + n);

                long totalBSTInsert = 0;
                long totalAVLInsert = 0;
                long totalBSTSearch = 0;
                long totalAVLSearch = 0;

                for (int r = 0; r < REPETICIONES; r++) {

                    List<Integer> datos = new ArrayList<>();
                    for (int i = 0; i < n; i++) datos.add(i);
                    Collections.shuffle(datos);

                    BST<Integer> bst = new BST<>(Integer::compareTo);
                    AVL<Integer> avl = new AVL<>(Integer::compareTo);

                    // INSERT
                    long iniBST = System.nanoTime();
                    for (int val : datos) bst.insert(val);
                    long finBST = System.nanoTime();

                    long iniAVL = System.nanoTime();
                    for (int val : datos) avl.insert(val);
                    long finAVL = System.nanoTime();

                    totalBSTInsert += (finBST - iniBST);
                    totalAVLInsert += (finAVL - iniAVL);

                    // SEARCH
                    int target = datos.get(random.nextInt(n));

                    long iniSB = System.nanoTime();
                    bst.search(target);
                    long finSB = System.nanoTime();

                    long iniSA = System.nanoTime();
                    avl.search(target);
                    long finSA = System.nanoTime();

                    totalBSTSearch += (finSB - iniSB);
                    totalAVLSearch += (finSA - iniSA);
                }

                writer.println(n + ",BST_INSERT," + (totalBSTInsert / REPETICIONES) + ",OK");
                writer.println(n + ",AVL_INSERT," + (totalAVLInsert / REPETICIONES) + ",OK");

                writer.println(n + ",BST_SEARCH," + (totalBSTSearch / REPETICIONES) + ",OK");
                writer.println(n + ",AVL_SEARCH," + (totalAVLSearch / REPETICIONES) + ",OK");

                // PEOR CASO BST
                BST<Integer> worstBST = new BST<>(Integer::compareTo);

                long iniWorst = System.nanoTime();
                long finWorst;

                try {
                    for (int i = 0; i < n; i++) {
                        worstBST.insert(i);
                    }
                    finWorst = System.nanoTime();
                    writer.println(n + ",BST_PEOR_CASO," + (finWorst - iniWorst) + ",OK");

                } catch (StackOverflowError e) {
                    finWorst = System.nanoTime();
                    writer.println(n + ",BST_PEOR_CASO," + (finWorst - iniWorst) + ",STACK_OVERFLOW");
                }

                // LISTA ORDENADA
                List<Integer> lista = new ArrayList<>();

                long iniList = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    lista.add(i);
                    Collections.sort(lista);
                }
                long finList = System.nanoTime();

                writer.println(n + ",LISTA_ORDENADA," + (finList - iniList) + ",OK");

                // HEAP (medición real extracción)
                Heap<Integer> heap = new Heap<>(10000, Integer::compareTo);

                for (int i = 0; i < 10000; i++) {
                    heap.insert(random.nextInt());
                }

                long totalExtract = 0;

                for (int i = 0; i < 10000; i++) {
                    totalExtract += heap.extractWithTime();
                }

                writer.println(n + ",HEAP_EXTRACT," + (totalExtract / 10000) + ",OK");
            }

            System.out.println("Benchmark completado.");

        } catch (IOException e) {
            System.err.println("Error CSV: " + e.getMessage());
        }
    }
}