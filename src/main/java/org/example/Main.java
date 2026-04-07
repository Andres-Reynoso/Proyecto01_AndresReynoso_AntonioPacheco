package org.example;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA INTEGRAL ===\n");

        MTree<String> ciudad = cargarCiudadDesdeArchivo("datos_ciudad.txt");

        ciudad.levelOrder();

        System.out.println("Profundidad: " + ciudad.maxDepth());
        System.out.println("Hojas: " + ciudad.countLeaves());
        System.out.println("Internos: " + ciudad.countInternalNodes());
        System.out.println("Factor: " + ciudad.branchingFactor());

        System.out.println("\n>>> BST vs AVL");

        BST<Integer> bst = new BST<>(Integer::compareTo);
        AVL<Integer> avl = new AVL<>(Integer::compareTo);

        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int val = rand.nextInt(10000);
            bst.insert(val);
            avl.insert(val);
        }

        System.out.println("Altura BST: " + bst.height());
        System.out.println("Altura AVL: " + avl.height());
        System.out.println("Balance AVL: " + avl.getBalanceFactorRoot());

        // 🔥 NUEVO
        System.out.println("Comparaciones BST: " + bst.comparisons);
        System.out.println("Comparaciones AVL: " + avl.comparisons);
        System.out.println("Rotaciones AVL: " + avl.rotations);

        System.out.println("\n>>> HEAP");

        Heap<Evento> heap = new Heap<>(10, EventoComparators.porPrioridad.reversed());

        Evento e1 = new Evento(1, 5, 50, System.currentTimeMillis(), 7);
        Evento e2 = new Evento(2, 2, 30, System.currentTimeMillis(), 3);
        Evento e3 = new Evento(3, 9, 80, System.currentTimeMillis(), 10);

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        // 🔥 updatePriority demo
        System.out.println("\n>>> UPDATE PRIORITY");
        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        e1.setPrioridad(100);
        heap.updatePriority(e1, e1);

        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        // 🔥 cambio de criterio
        System.out.println("\n>>> CAMBIO DE CRITERIO (RIESGO)");
        heap.setComparator(EventoComparators.porRiesgo);

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }

        System.out.println("\n>>> BENCHMARK");
        Benchmark.ejecutar();

        System.out.println("\n=== FIN ===");
    }

    private static MTree<String> cargarCiudadDesdeArchivo(String path) {

        int maxChildren = 5;
        MTree<String> tree = new MTree<>("Ciudad", maxChildren);
        MNode<String> root = tree.getRoot();

        Map<String, MNode<String>> mapa = new HashMap<>();
        mapa.put("Ciudad", root);

        try (Scanner sc = new Scanner(new File(path))) {

            if (sc.hasNextLine()) sc.nextLine();

            while (sc.hasNextLine()) {

                String[] partes = sc.nextLine().split(",");

                String distrito = partes[1];
                String zona = partes[2];
                String avenida = partes[3];
                String inter = partes[4];

                mapa.putIfAbsent(distrito, new MNode<>(distrito, maxChildren));
                if (!root.getChildren().contains(mapa.get(distrito))) {
                    root.addChild(mapa.get(distrito));
                }

                mapa.putIfAbsent(zona, new MNode<>(zona, maxChildren));
                if (!mapa.get(distrito).getChildren().contains(mapa.get(zona))) {
                    mapa.get(distrito).addChild(mapa.get(zona));
                }

                mapa.putIfAbsent(avenida, new MNode<>(avenida, maxChildren));
                if (!mapa.get(zona).getChildren().contains(mapa.get(avenida))) {
                    mapa.get(zona).addChild(mapa.get(avenida));
                }

                // 🔥 corregido (sin duplicados)
                mapa.putIfAbsent(inter, new MNode<>(inter, maxChildren));
                if (!mapa.get(avenida).getChildren().contains(mapa.get(inter))) {
                    mapa.get(avenida).addChild(mapa.get(inter));
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return tree;
    }
}