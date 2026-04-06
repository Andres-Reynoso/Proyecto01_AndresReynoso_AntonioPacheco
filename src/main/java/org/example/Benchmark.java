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

            // Nuevo encabezado más completo
            writer.println("N,Estructura,Tiempo_ns,Estado");

            for (int n : N_VALUES) {

                System.out.println("Procesando N = " + n);

                long totalBST = 0;
                long totalAVL = 0;

                // CASO PROMEDIO

                for (int r = 0; r < REPETICIONES; r++) {

                    List<Integer> datos = new ArrayList<>();
                    for (int i = 0; i < n; i++) datos.add(i);
                    Collections.shuffle(datos);

                    BST<Integer> bst = new BST<>(Integer::compareTo);
                    AVL<Integer> avl = new AVL<>(Integer::compareTo);

                    long iniBST = System.nanoTime();
                    for (int val : datos) bst.insert(val);
                    long finBST = System.nanoTime();

                    long iniAVL = System.nanoTime();
                    for (int val : datos) avl.insert(val);
                    long finAVL = System.nanoTime();

                    totalBST += (finBST - iniBST);
                    totalAVL += (finAVL - iniAVL);
                }

                writer.println(n + ",BST_PROMEDIO," + (totalBST / REPETICIONES) + ",OK");
                writer.println(n + ",AVL_PROMEDIO," + (totalAVL / REPETICIONES) + ",OK");


                // PEOR CASO BST
                BST<Integer> worstBST = new BST<>(Integer::compareTo);

                long iniWorst = System.nanoTime();
                long finWorst;

                try {
                    for (int i = 0; i < n; i++) {
                        worstBST.insert(i); // inserción ordenada
                    }

                    finWorst = System.nanoTime();
                    writer.println(n + ",BST_PEOR_CASO," + (finWorst - iniWorst) + ",OK");

                } catch (StackOverflowError e) {

                    finWorst = System.nanoTime();

                    writer.println(n + ",BST_PEOR_CASO," + (finWorst - iniWorst) + ",STACK_OVERFLOW");

                    System.out.println("⚠️ StackOverflow en BST con N = " + n + " (peor caso)");
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
            }

            System.out.println("Benchmark completado. Archivo generado: resultados.csv");

        } catch (IOException e) {
            System.err.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}