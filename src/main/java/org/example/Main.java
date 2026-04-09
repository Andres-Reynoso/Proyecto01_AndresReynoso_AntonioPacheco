package org.example;

import java.io.File;
import java.util.*;

/**
 * Clase principal del sistema.
 *
 * Aquí se prueban todas las estructuras del proyecto:
 * árbol multi-camino, BST, AVL, heap y benchmark.
 * También se cargan los datos desde archivo.
 */
public class Main {

    /**
     * Punto de inicio del programa.
     *
     * Ejecuta todas las pruebas del sistema:
     * - Construcción del árbol de la ciudad
     * - Indexación con AVL o BST
     * - Comparaciones de rendimiento
     * - Uso de heap con eventos
     * - Benchmark final
     */
    public static void main(String[] args) {

        System.out.println("=== MOTOR INTELIGENTE DE GESTIÓN DE TRÁFICO ===\n");

        // 1. ÁRBOL MULTI-CAMINO
        System.out.println("========================================");
        System.out.println("ARBOL MULTI-CAMINO");
        System.out.println("========================================");

        MTree<String> ciudad = cargarCiudadDesdeArchivo("datos_ciudad.txt");

        System.out.println(">> Recorrido por niveles:");
        ciudad.levelOrder();

        System.out.println("Profundidad maxima: " + ciudad.maxDepth());
        System.out.println("Hojas: " + ciudad.countLeaves());
        System.out.println("Nodos internos: " + ciudad.countInternalNodes());
        System.out.println("Factor de ramificacion: " + ciudad.branchingFactor());

        System.out.println("Intersecciones en Distrito1: " +
                ciudad.countInterseccionesPorDistrito("Distrito1"));
        System.out.println("Intersecciones en Sur: " +
                ciudad.countInterseccionesPorDistrito("Sur"));
        System.out.println("Intersecciones en Norte: " +
                ciudad.countInterseccionesPorDistrito("Norte"));

        // 2. BST Y AVL
        System.out.println("\n========================================");
        System.out.println("INDEXACION AVL O BST");
        System.out.println("========================================");

        Scanner sc = new Scanner(System.in);
        String eleccion = sc.nextLine().trim().toLowerCase();

        List<Interseccion> intersecciones = cargarIntersecciones("datos_ciudad.txt");

        if (eleccion.equals("avl")) {

            AVL<Interseccion> avl = new AVL<>(Comparator.comparingInt(Interseccion::getId));

            for (Interseccion i : intersecciones) {
                avl.insert(i);
            }

            System.out.println("\nRecorridos AVL:");
            System.out.print("InOrder: "); avl.inOrder();
            System.out.print("PreOrder: "); avl.preOrder();
            System.out.print("PostOrder: "); avl.postOrder();

            System.out.println("Altura AVL: " + avl.height());
            System.out.println("Rotaciones AVL: " + avl.rotations);
            System.out.println("Comparaciones AVL: " + avl.comparisons);
            System.out.println("Factor de balance raiz: " + avl.getBalanceFactorRoot());

        } else {

            BST<Interseccion> bst = new BST<>(Comparator.comparingInt(Interseccion::getId));

            for (Interseccion i : intersecciones) {
                bst.insert(i);
            }

            System.out.println("\nRecorridos BST:");
            System.out.print("InOrder: "); bst.inOrder();
            System.out.print("PreOrder: "); bst.preOrder();
            System.out.print("PostOrder: "); bst.postOrder();

            System.out.println("Altura BST: " + bst.height());
            System.out.println("Comparaciones BST: " + bst.comparisons);
        }

        // 3. COMPARACIÓN BST VS AVL
        System.out.println("\n========================================");
        System.out.println(" BST VS AVL (1000)");
        System.out.println("========================================");

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
        System.out.println("Comparaciones BST: " + bst.comparisons);
        System.out.println("Comparaciones AVL: " + avl.comparisons);
        System.out.println("Rotaciones AVL: " + avl.rotations);

        // 4. BÚSQUEDA 100K
        System.out.println("\n========================================");
        System.out.println("BUSQUEDA 100K");
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

        System.out.println("Altura BST: " + bst100k.height());
        System.out.println("Altura AVL: " + avl100k.height());
        System.out.println("Tiempo BST: " + (t2 - t1));
        System.out.println("Tiempo AVL: " + (t4 - t3));
        System.out.println("Comparaciones BST: " + bst100k.comparisons);
        System.out.println("Comparaciones AVL: " + avl100k.comparisons);

        // 5. HEAP
        System.out.println("\n========================================");
        System.out.println(" HEAP");
        System.out.println("========================================");

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

        // 6. CRITERIO DINÁMICO
        System.out.println("\n========================================");
        System.out.println("CRITERIO");
        System.out.println("========================================");

        Heap<Evento> heap2 = new Heap<>(10, EventoComparators.porPrioridad);
        heap2.setCriterio((evento, umbral) -> evento.getCongestion() > umbral);

        heap2.insert(e1);
        heap2.insert(e2);
        heap2.insert(e3);

        heap2.procesarConCriterio(40);

        // 7. UPDATE PRIORITY
        System.out.println("\n========================================");
        System.out.println(" UPDATE PRIORITY");
        System.out.println("========================================");

        Heap<Evento> heap3 = new Heap<>(10, EventoComparators.porPrioridad.reversed());

        heap3.insert(e1);
        heap3.insert(e2);
        heap3.insert(e3);

        e1.setPrioridad(100);
        heap3.updatePriority(e1, e1);

        while (!heap3.isEmpty()) {
            System.out.println(heap3.extract());
        }

        // 8. BENCHMARK
        System.out.println("\n========================================");
        System.out.println("BENCHMARK");
        System.out.println("========================================");

        Benchmark.ejecutar();

        System.out.println("\n=== FIN ===");
    }

    /**
     * Carga la ciudad desde un archivo y arma la jerarquía:
     * Ciudad → Distrito → Zona → Avenida → Intersección.
     *
     * Usa claves internas para no repetir nodos.
     */
    private static MTree<String> cargarCiudadDesdeArchivo(String path) {

        int maxChildren = 5;
        MTree<String> tree = new MTree<>("Ciudad", maxChildren);
        MNode<String> root = tree.getRoot();

        Map<String, MNode<String>> mapa = new HashMap<>();
        mapa.put("Ciudad", root);

        try (Scanner scanner = new Scanner(new File(path))) {

            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {

                String[] partes = scanner.nextLine().split(",");

                String distrito = partes[1].trim();
                String zona = partes[2].trim();
                String avenida = partes[3].trim();
                String inter = partes[4].trim();

                String keyDistrito = "D-" + distrito;
                String keyZona = keyDistrito + "-Z-" + zona;
                String keyAvenida = keyZona + "-A-" + avenida;
                String keyInter = keyAvenida + "-I-" + inter;

                mapa.putIfAbsent(keyDistrito, new MNode<>(distrito, maxChildren));
                if (!root.getChildren().contains(mapa.get(keyDistrito)))
                    root.addChild(mapa.get(keyDistrito));

                mapa.putIfAbsent(keyZona, new MNode<>(zona, maxChildren));
                if (!mapa.get(keyDistrito).getChildren().contains(mapa.get(keyZona)))
                    mapa.get(keyDistrito).addChild(mapa.get(keyZona));

                mapa.putIfAbsent(keyAvenida, new MNode<>(avenida, maxChildren));
                if (!mapa.get(keyZona).getChildren().contains(mapa.get(keyAvenida)))
                    mapa.get(keyZona).addChild(mapa.get(keyAvenida));

                mapa.putIfAbsent(keyInter, new MNode<>(inter, maxChildren));
                if (!mapa.get(keyAvenida).getChildren().contains(mapa.get(keyInter)))
                    mapa.get(keyAvenida).addChild(mapa.get(keyInter));
            }

        } catch (Exception e) {
            System.out.println("Error cargando ciudad");
        }

        return tree;
    }

    /**
     * Lee el archivo y convierte cada línea en una intersección.
     * Los IDs se generan automáticamente.
     */
    private static List<Interseccion> cargarIntersecciones(String path) {

        List<Interseccion> lista = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {

            if (scanner.hasNextLine()) scanner.nextLine();
            int id = 1;

            while (scanner.hasNextLine()) {

                String[] partes = scanner.nextLine().split(",");

                lista.add(new Interseccion(
                        id++,
                        partes[4].trim(),
                        0,
                        partes[1].trim(),
                        partes[2].trim(),
                        partes[3].trim()
                ));
            }

        } catch (Exception e) {
            System.out.println("Error cargando intersecciones");
        }

        return lista;
    }
}