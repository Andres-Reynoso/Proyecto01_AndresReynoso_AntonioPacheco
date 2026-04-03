    package org.example;

    import java.util.Comparator;

    public class AVL<T> {

        private AVLNode<T> root;
        private Comparator<T> comparator;


        public int comparisons = 0;
        public int rotations = 0;

        public AVL(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        // 1.Insert con Balance Automático
        public void insert(T data) {
            root = insertRec(root, data);
        }

        private AVLNode<T> insertRec(AVLNode<T> node, T data) {
            if (node == null) return new AVLNode<>(data);

            comparisons++;
            int cmp = comparator.compare(data, node.data);

            if (cmp < 0) {
                node.left = insertRec(node.left, data);
            } else if (cmp > 0) {
                node.right = insertRec(node.right, data);
            } else {
                return node; // Duplicados no permitidos
            }

            // Actualizar altura del nodo actual
            updateHeight(node);

            // 2. Registro del Factor de Balance y Rotaciones
            return balance(node);
        }

        private void updateHeight(AVLNode<T> node) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        private int getHeight(AVLNode<T> node) {
            return (node == null) ? 0 : node.height;
        }

        private int getBalanceFactor(AVLNode<T> node) {
            return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
        }

        private AVLNode<T> balance(AVLNode<T> node) {
            int balance = getBalanceFactor(node);

            // Caso Izquierda-Izquierda
            if (balance > 1 && getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }

            // Caso Izquierda-Derecha
            if (balance > 1 && getBalanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Caso Derecha-Derecha
            if (balance < -1 && getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }

            // Caso Derecha-Izquierda
            if (balance < -1 && getBalanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        // 3. Rotaciones (Simples y Dobles)
        private AVLNode<T> rightRotate(AVLNode<T> y) {
            AVLNode<T> x = y.left;
            AVLNode<T> T2 = x.right;

            x.right = y;
            y.left = T2;

            updateHeight(y);
            updateHeight(x);

            rotations++; // Conteo de rotaciones
            return x;
        }

        private AVLNode<T> leftRotate(AVLNode<T> x) {
            AVLNode<T> y = x.right;
            AVLNode<T> T2 = y.left;

            y.left = x;
            x.right = T2;

            updateHeight(x);
            updateHeight(y);

            rotations++; // Conteo de rotaciones
            return y;
        }

        // 4.Search
        public boolean search(T data) {
            return searchRec(root, data);
        }

        private boolean searchRec(AVLNode<T> node, T data) {
            if (node == null) return false;
            comparisons++;
            int cmp = comparator.compare(data, node.data);
            if (cmp == 0) return true;
            return (cmp < 0) ? searchRec(node.left, data) : searchRec(node.right, data);
        }

        public int height() {
            return getHeight(root);
        }
    }