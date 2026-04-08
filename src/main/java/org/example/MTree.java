package org.example;

/**
 * Implementa un árbol multi-camino (no binario).
 *
 * Cada nodo puede tener varios hijos hasta un máximo definido.
 * Se usa para representar estructuras jerárquicas como:
 * Ciudad → Distrito → Zona → Avenida → Intersección.
 */
public class MTree<T> {

    private MNode<T> root;
    private int maxChildren;

    /**
     * Crea el árbol con un nodo raíz y límite de hijos por nodo.
     */
    public MTree(T rootData, int maxChildren) {
        this.maxChildren = maxChildren;
        this.root = new MNode<>(rootData, maxChildren);
    }

    /**
     * Devuelve la raíz del árbol.
     */
    public MNode<T> getRoot() {
        return root;
    }

    /**
     * Recorrido por niveles (BFS).
     *
     * Visita los nodos nivel por nivel usando una cola.
     */
    public void levelOrder() {
        if (root == null) return;

        SimpleQueue<MNode<T>> queue = new SimpleQueue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            MNode<T> current = queue.dequeue();
            System.out.print(current.getData() + " ");

            for (MNode<T> child : current.getChildren()) {
                queue.enqueue(child);
            }
        }
        System.out.println();
    }

    /**
     * Calcula la profundidad máxima del árbol.
     */
    public int maxDepth() {
        return maxDepthRec(root);
    }

    private int maxDepthRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;

        int max = 0;
        for (MNode<T> child : node.getChildren()) {
            max = Math.max(max, maxDepthRec(child));
        }
        return max + 1;
    }

    /**
     * Cuenta cuántos nodos hoja tiene el árbol.
     */
    public int countLeaves() {
        return countLeavesRec(root);
    }

    private int countLeavesRec(MNode<T> node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;

        int count = 0;
        for (MNode<T> child : node.getChildren()) {
            count += countLeavesRec(child);
        }
        return count;
    }

    /**
     * Cuenta los nodos internos (los que tienen al menos un hijo).
     */
    public int countInternalNodes() {
        return countInternalRec(root);
    }

    private int countInternalRec(MNode<T> node) {
        if (node == null || node.getChildren().isEmpty()) return 0;

        int count = 1;
        for (MNode<T> child : node.getChildren()) {
            count += countInternalRec(child);
        }
        return count;
    }

    /**
     * Calcula el factor de ramificación promedio del árbol.
     *
     * Es la relación entre la cantidad total de hijos y la cantidad de nodos.
     */
    public double branchingFactor() {
        int totalChildren = countAllChildren(root);
        int totalNodes = countNodes(root);
        return (double) totalChildren / totalNodes;
    }

    private int countAllChildren(MNode<T> node) {
        if (node == null) return 0;

        int count = node.getChildren().size();
        for (MNode<T> child : node.getChildren()) {
            count += countAllChildren(child);
        }
        return count;
    }

    private int countNodes(MNode<T> node) {
        if (node == null) return 0;

        int count = 1;
        for (MNode<T> child : node.getChildren()) {
            count += countNodes(child);
        }
        return count;
    }

    /**
     * Cuenta cuántas intersecciones (hojas) hay dentro de un distrito.
     *
     * Busca el nodo del distrito y cuenta todas sus hojas.
     */
    public int countInterseccionesPorDistrito(String distrito) {
        MNode<T> nodoDistrito = findNode(root, distrito);
        if (nodoDistrito == null) {
            System.out.println("Distrito no encontrado: " + distrito);
            return 0;
        }
        return countLeavesRec(nodoDistrito);
    }

    /**
     * Busca un nodo por su valor (recorrido en profundidad).
     */
    private MNode<T> findNode(MNode<T> node, String target) {
        if (node == null) return null;
        if (node.getData().toString().equals(target)) return node;

        for (MNode<T> child : node.getChildren()) {
            MNode<T> result = findNode(child, target);
            if (result != null) return result;
        }
        return null;
    }
}