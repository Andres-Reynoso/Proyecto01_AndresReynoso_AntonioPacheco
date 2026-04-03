package org.example;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA INTEGRAL DEL SISTEMA ===\n");

        // 1. M-TREE (JERARQUÍA URBANA)

        System.out.println(">>> PRUEBA M-TREE");

        MTree<String> ciudad = new MTree<>("Ciudad");

        MNode<String> distrito = new MNode<>("Distrito Central");
        ciudad.getRoot().addChild(distrito);

        MNode<String> zona = new MNode<>("Zona 1");
        distrito.addChild(zona);

        MNode<String> avenida = new MNode<>("Avenida Reforma");
        zona.addChild(avenida);

        avenida.addChild(new MNode<>("Intersección A"));
        avenida.addChild(new MNode<>("Intersección B"));

        System.out.print("Recorrido BFS: ");
        ciudad.levelOrder();

        System.out.println("Profundidad máxima: " + ciudad.maxDepth());
        System.out.println("Hojas: " + ciudad.countLeaves());
        System.out.println("Nodos internos: " + ciudad.countInternalNodes());
        System.out.println("Factor ramificación: " + ciudad.branchingFactor());

        System.out.println("Intersecciones en Avenida: " + ciudad.countNodesFrom(avenida));
        System.out.println();

        // 2. BST vs AVL

        System.out.println(">>> PRUEBA BST vs AVL");

        BST<Integer> bst = new BST<>(Integer::compareTo);
        AVL<Integer> avl = new AVL<>(Integer::compareTo);

        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int val = rand.nextInt(10000);
            bst.insert(val);
            avl.insert(val);
        }

        System.out.println("Altura BST: " + bst.height());
        System.out.println("Comparaciones BST: " + bst.comparisons);

        System.out.println("Altura AVL: " + avl.height());
        System.out.println("Comparaciones AVL: " + avl.comparisons);
        System.out.println("Rotaciones AVL: " + avl.rotations);

        System.out.println();

        // 3. HEAP (COLA DE PRIORIDAD)

        System.out.println(">>> PRUEBA HEAP");

        Heap<Evento> heap = new Heap<>(10, EventoComparators.porPrioridad.reversed());

        Evento e1 = new Evento(1, 5, 50, System.currentTimeMillis(), 7);
        Evento e2 = new Evento(2, 2, 30, System.currentTimeMillis(), 3);
        Evento e3 = new Evento(3, 9, 80, System.currentTimeMillis(), 10);

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        System.out.println("Extrayendo eventos por prioridad:");
        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        //Cambio de prioridad dinámico
        System.out.println("\n>>> Cambio de prioridad dinámico");

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        e2.setPrioridad(15);
        heap.updatePriority(e2);

        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        System.out.println();


        // 4. CAMBIO DE CRITERIO (LAMBDA / COMPARATOR)

        System.out.println(">>> CAMBIO DE CRITERIO DE ORDEN");

        heap = new Heap<>(10, EventoComparators.porCongestion.reversed());

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        System.out.println("Orden por congestión:");
        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        System.out.println();

        // 5. BENCHMARK

        System.out.println(">>> EJECUTANDO BENCHMARK");

        Benchmark.ejecutar();

        System.out.println("\nArchivo 'resultados.csv' generado correctamente.");

        System.out.println("\n=== FIN DE PRUEBA ===");
    }
}