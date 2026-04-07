package org.example;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== MOTOR INTELIGENTE DE GESTIÓN DE TRÁFICO ===\n");

        //  Cargar ciudad en MTree
        MTree<String> ciudad = cargarCiudadDesdeArchivo("datos_ciudad.txt");

        System.out.println(">> Recorrido por niveles:");
        ciudad.levelOrder();

        System.out.println("Profundidad máxima: " + ciudad.maxDepth());
        System.out.println("Hojas (intersecciones): " + ciudad.countLeaves());
        System.out.println("Nodos internos: " + ciudad.countInternalNodes());
        System.out.println("Factor de ramificación: " + ciudad.branchingFactor());

        //  FIX 2: contar intersecciones por distrito
        System.out.println("Intersecciones en Distrito1: " +
                ciudad.countInterseccionesPorDistrito("Distrito1"));

        //  FIX 7: elegir estructura e indexar desde archivo
        System.out.println("\n¿Usar AVL o BST para indexación? (avl/bst)");
        Scanner sc = new Scanner(System.in);
        String eleccion = sc.nextLine().trim().toLowerCase();

        List<Interseccion> intersecciones = cargarIntersecciones("datos_ciudad.txt");

        if (eleccion.equals("avl")) {
            AVL<Interseccion> avl = new AVL<>(Comparator.comparingInt(Interseccion::getId));
            for (Interseccion i : intersecciones) avl.insert(i);
            System.out.println("AVL altura: " + avl.height());
            System.out.println("AVL rotaciones: " + avl.rotations);
            System.out.println("AVL comparaciones: " + avl.comparisons);
        } else {
            BST<Interseccion> bst = new BST<>(Comparator.comparingInt(Interseccion::getId));
            for (Interseccion i : intersecciones) bst.insert(i);
            System.out.println("BST altura: " + bst.height());
            System.out.println("BST comparaciones: " + bst.comparisons);
        }

        // BST vs AVL numérico
        System.out.println("\n>>> BST vs AVL (1000 elementos aleatorios)");
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
        System.out.println("Balance AVL root: " + avl.getBalanceFactorRoot());
        System.out.println("Comparaciones BST: " + bst.comparisons);
        System.out.println("Comparaciones AVL: " + avl.comparisons);
        System.out.println("Rotaciones AVL: " + avl.rotations);

        // HEAP
        System.out.println("\n>>> HEAP DE EVENTOS");
        Heap<Evento> heap = new Heap<>(10, EventoComparators.porPrioridad.reversed());

        Evento e1 = new Evento(1, 5, 50, System.currentTimeMillis(), 7);
        Evento e2 = new Evento(2, 2, 30, System.currentTimeMillis(), 3);
        Evento e3 = new Evento(3, 9, 80, System.currentTimeMillis(), 10);

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        while (!heap.isEmpty()) System.out.println(heap.extract());

        //  uso de CriterioTrafico (inyección de comportamiento)
        System.out.println("\n>>> CRITERIO DE TRÁFICO (congestion > umbral)");
        Heap<Evento> heap2 = new Heap<>(10, EventoComparators.porPrioridad);
        heap2.setCriterio((evento, umbral) -> evento.getCongestion() > umbral);
        heap2.insert(e1);
        heap2.insert(e2);
        heap2.insert(e3);
        heap2.procesarConCriterio(40);

        // updatePriority
        System.out.println("\n>>> UPDATE PRIORITY");
        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);
        e1.setPrioridad(100);
        heap.updatePriority(e1, e1);
        while (!heap.isEmpty()) System.out.println(heap.extract());

        // cambio de criterio
        System.out.println("\n>>> CAMBIO DE CRITERIO (RIESGO)");
        heap.setComparator(EventoComparators.porRiesgo);
        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);
        while (!heap.isEmpty()) System.out.println(heap.extract());

        // BENCHMARK
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

        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // header

            while (scanner.hasNextLine()) {
                String[] partes = scanner.nextLine().split(",");
                if (partes.length < 5) continue;

                String distrito = partes[1].trim();
                String zona     = partes[2].trim();
                String avenida  = partes[3].trim();
                String inter    = partes[4].trim();

                mapa.putIfAbsent(distrito, new MNode<>(distrito, maxChildren));
                if (!root.getChildren().contains(mapa.get(distrito)))
                    root.addChild(mapa.get(distrito));

                mapa.putIfAbsent(zona, new MNode<>(zona, maxChildren));
                if (!mapa.get(distrito).getChildren().contains(mapa.get(zona)))
                    mapa.get(distrito).addChild(mapa.get(zona));

                mapa.putIfAbsent(avenida, new MNode<>(avenida, maxChildren));
                if (!mapa.get(zona).getChildren().contains(mapa.get(avenida)))
                    mapa.get(zona).addChild(mapa.get(avenida));

                mapa.putIfAbsent(inter, new MNode<>(inter, maxChildren));
                if (!mapa.get(avenida).getChildren().contains(mapa.get(inter)))
                    mapa.get(avenida).addChild(mapa.get(inter));
            }

        } catch (Exception e) {
            System.out.println("Error cargando ciudad: " + e.getMessage());
        }

        return tree;
    }

    // carga intersecciones para BST/AVL
    private static List<Interseccion> cargarIntersecciones(String path) {
        List<Interseccion> lista = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // header
            int id = 1;

            while (scanner.hasNextLine()) {
                String[] partes = scanner.nextLine().split(",");
                if (partes.length < 5) continue;

                String distrito = partes[1].trim();
                String zona     = partes[2].trim();
                String avenida  = partes[3].trim();
                String inter    = partes[4].trim();
                int congestion  = partes.length > 5 ? Integer.parseInt(partes[5].trim()) : 0;

                lista.add(new Interseccion(id++, inter, congestion, distrito, zona, avenida));
            }

        } catch (Exception e) {
            System.out.println("Error cargando intersecciones: " + e.getMessage());
        }

        return lista;
    }
}