package org.example;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== MOTOR INTELIGENTE DE GESTIÓN DE TRÁFICO ===\n");

        // ============================================================
        // 1. ÁRBOL MULTI-CAMINO (MTree) — Jerarquía urbana
        // ============================================================
        System.out.println("========================================");
        System.out.println(">>> ÁRBOL MULTI-CAMINO (M-ary Tree)");
        System.out.println("========================================");

        MTree<String> ciudad = cargarCiudadDesdeArchivo("datos_ciudad.txt");

        System.out.println(">> Recorrido por niveles:");
        ciudad.levelOrder();

        System.out.println("Profundidad máxima de la red: " + ciudad.maxDepth());
        System.out.println("Hojas (intersecciones): " + ciudad.countLeaves());
        System.out.println("Nodos internos: " + ciudad.countInternalNodes());
        System.out.println("Factor promedio de ramificación: " + String.format("%.2f", ciudad.branchingFactor()));

        System.out.println("Intersecciones en Distrito1: " +
                ciudad.countInterseccionesPorDistrito("Distrito1"));
        System.out.println("Intersecciones en Sur: " +
                ciudad.countInterseccionesPorDistrito("Sur"));
        System.out.println("Intersecciones en Norte: " +
                ciudad.countInterseccionesPorDistrito("Norte"));

        // ============================================================
        // 2. BST y AVL — Indexación de intersecciones desde archivo
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> INDEXACIÓN: ¿Usar AVL o BST? (avl/bst)");
        System.out.println("========================================");

        Scanner sc = new Scanner(System.in);
        String eleccion = sc.nextLine().trim().toLowerCase();

        List<Interseccion> intersecciones = cargarIntersecciones("datos_ciudad.txt");

        if (eleccion.equals("avl")) {
            AVL<Interseccion> avl = new AVL<>(Comparator.comparingInt(Interseccion::getId));
            for (Interseccion i : intersecciones) avl.insert(i);

            System.out.println("\n>> Recorridos AVL:");
            System.out.print("InOrder:   "); avl.inOrder();
            System.out.print("PreOrder:  "); avl.preOrder();
            System.out.print("PostOrder: "); avl.postOrder();

            System.out.println("AVL altura: " + avl.height());
            System.out.println("AVL rotaciones: " + avl.rotations);
            System.out.println("AVL comparaciones: " + avl.comparisons);
            System.out.println("AVL factor de balance (raíz): " + avl.getBalanceFactorRoot());

        } else {
            BST<Interseccion> bst = new BST<>(Comparator.comparingInt(Interseccion::getId));
            for (Interseccion i : intersecciones) bst.insert(i);

            System.out.println("\n>> Recorridos BST:");
            System.out.print("InOrder:   "); bst.inOrder();
            System.out.print("PreOrder:  "); bst.preOrder();
            System.out.print("PostOrder: "); bst.postOrder();

            System.out.println("BST altura: " + bst.height());
            System.out.println("BST comparaciones: " + bst.comparisons);
        }

        // ============================================================
        // 3. BST vs AVL — Comparación experimental (1000 aleatorios)
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> BST vs AVL (1000 elementos aleatorios)");
        System.out.println("========================================");

        BST<Integer> bst = new BST<>(Integer::compareTo);
        AVL<Integer> avl = new AVL<>(Integer::compareTo);
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int val = rand.nextInt(10000);
            bst.insert(val);
            avl.insert(val);
        }

        System.out.println("Altura BST:          " + bst.height());
        System.out.println("Altura AVL:          " + avl.height());
        System.out.println("Balance AVL (raíz):  " + avl.getBalanceFactorRoot());
        System.out.println("Comparaciones BST:   " + bst.comparisons);
        System.out.println("Comparaciones AVL:   " + avl.comparisons);
        System.out.println("Rotaciones AVL:      " + avl.rotations);

        // ============================================================
        // 4. BST vs AVL — Búsqueda bajo 100,000 intersecciones
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> BST vs AVL — Búsqueda con 100,000 elementos");
        System.out.println("========================================");

        BST<Integer> bst100k = new BST<>(Integer::compareTo);
        AVL<Integer> avl100k = new AVL<>(Integer::compareTo);

        List<Integer> datos100k = new ArrayList<>();
        for (int i = 0; i < 100000; i++) datos100k.add(i);
        Collections.shuffle(datos100k);

        for (int val : datos100k) {
            bst100k.insert(val);
            avl100k.insert(val);
        }

        int target = datos100k.get(rand.nextInt(100000));

        bst100k.resetStats();
        long t1 = System.nanoTime();
        bst100k.search(target);
        long t2 = System.nanoTime();

        avl100k.comparisons = 0;
        long t3 = System.nanoTime();
        avl100k.search(target);
        long t4 = System.nanoTime();

        System.out.println("Altura BST (100k):         " + bst100k.height());
        System.out.println("Altura AVL (100k):         " + avl100k.height());
        System.out.println("Tiempo búsqueda BST (ns):  " + (t2 - t1));
        System.out.println("Tiempo búsqueda AVL (ns):  " + (t4 - t3));
        System.out.println("Comparaciones BST:         " + bst100k.comparisons);
        System.out.println("Comparaciones AVL:         " + avl100k.comparisons);

        // ============================================================
        // 5. HEAP — Cola de prioridad de eventos
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> HEAP DE EVENTOS");
        System.out.println("========================================");

        Heap<Evento> heap = new Heap<>(10, EventoComparators.porPrioridad.reversed());

        Evento e1 = new Evento(1, 5, 50, System.currentTimeMillis(), 7);
        Evento e2 = new Evento(2, 2, 30, System.currentTimeMillis(), 3);
        Evento e3 = new Evento(3, 9, 80, System.currentTimeMillis(), 10);

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);

        System.out.println("Extracción por prioridad (mayor primero):");
        while (!heap.isEmpty()) System.out.println("  " + heap.extract());

        // ============================================================
        // 6. CRITERIO DE TRÁFICO — Inyección de comportamiento
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> CRITERIO DE TRÁFICO (congestion > umbral = 40)");
        System.out.println("========================================");

        Heap<Evento> heap2 = new Heap<>(10, EventoComparators.porPrioridad);
        heap2.setCriterio((evento, umbral) -> evento.getCongestion() > umbral);
        heap2.insert(e1);
        heap2.insert(e2);
        heap2.insert(e3);
        heap2.procesarConCriterio(40);

        // ============================================================
        // 7. UPDATE PRIORITY
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> UPDATE PRIORITY");
        System.out.println("========================================");

        Heap<Evento> heap3 = new Heap<>(10, EventoComparators.porPrioridad.reversed());
        heap3.insert(e1);
        heap3.insert(e2);
        heap3.insert(e3);

        System.out.println("Antes de actualizar prioridad de e1 (prioridad=5):");
        e1.setPrioridad(100);
        heap3.updatePriority(e1, e1);
        System.out.println("Después de actualizar prioridad de e1 a 100:");
        while (!heap3.isEmpty()) System.out.println("  " + heap3.extract());

        // ============================================================
        // 8. CAMBIO DE CRITERIO DINÁMICO — Los 4 criterios
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> CAMBIO DE CRITERIO DINÁMICO");
        System.out.println("========================================");

        // Restablecer prioridad de e1 para las pruebas
        e1.setPrioridad(5);

        // Criterio 1: por prioridad
        System.out.println("-- Criterio: Por Prioridad --");
        Heap<Evento> heapCriterio = new Heap<>(10, EventoComparators.porPrioridad);
        heapCriterio.insert(e1);
        heapCriterio.insert(e2);
        heapCriterio.insert(e3);
        while (!heapCriterio.isEmpty()) System.out.println("  " + heapCriterio.extract());

        // Criterio 2: por congestión
        System.out.println("-- Criterio: Por Congestión --");
        heapCriterio.setComparator(EventoComparators.porCongestion);
        heapCriterio.insert(e1);
        heapCriterio.insert(e2);
        heapCriterio.insert(e3);
        while (!heapCriterio.isEmpty()) System.out.println("  " + heapCriterio.extract());

        // Criterio 3: por tiempo de reporte
        System.out.println("-- Criterio: Por Tiempo de Reporte --");
        heapCriterio.setComparator(EventoComparators.porTiempo);
        heapCriterio.insert(e1);
        heapCriterio.insert(e2);
        heapCriterio.insert(e3);
        while (!heapCriterio.isEmpty()) System.out.println("  " + heapCriterio.extract());

        // Criterio 4: por riesgo
        System.out.println("-- Criterio: Por Riesgo --");
        heapCriterio.setComparator(EventoComparators.porRiesgo);
        heapCriterio.insert(e1);
        heapCriterio.insert(e2);
        heapCriterio.insert(e3);
        while (!heapCriterio.isEmpty()) System.out.println("  " + heapCriterio.extract());

        // ============================================================
        // 9. BENCHMARK — Simulación y exportación a CSV
        // ============================================================
        System.out.println("\n========================================");
        System.out.println(">>> BENCHMARK (exporta resultados.csv)");
        System.out.println("========================================");
        Benchmark.ejecutar();

        System.out.println("\n=== FIN DEL SISTEMA ===");
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================

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

    private static List<Interseccion> cargarIntersecciones(String path) {
        List<Interseccion> lista = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // header
            int id = 1;

            while (scanner.hasNextLine()) {
                String[] partes = scanner.nextLine().split(",");
                if (partes.length < 5) continue;

                String distrito  = partes[1].trim();
                String zona      = partes[2].trim();
                String avenida   = partes[3].trim();
                String inter     = partes[4].trim();
                int congestion   = partes.length > 5 ? Integer.parseInt(partes[5].trim()) : 0;

                lista.add(new Interseccion(id++, inter, congestion, distrito, zona, avenida));
            }

        } catch (Exception e) {
            System.out.println("Error cargando intersecciones: " + e.getMessage());
        }

        return lista;
    }
}