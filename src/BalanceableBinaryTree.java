public class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Pair<K,V>> {
    protected static class BSTNode<E> extends Node<E> {
        int aux = 0;

        BSTNode(E value, Node<E> parent, Node<E> left, Node<E> right) {
            super(value, parent, left, right);
        }

        int getAux() {
            return aux;
        }

        void setAux(int aux) {
            this.aux = aux;
        }
    }

    int getAux(Position<Pair<K,V>> p) {
        return ((BSTNode<Pair<K,V>>) p).getAux();
    }

    void setAux(Position<Pair<K, V>> p, int value) {
        ((BSTNode<Pair<K,V>>) p).setAux(value);
    }

    protected Node<Pair<K,V>> createNode(Pair<K,V> pair, Node<Pair<K,V>> parent, Node<Pair<K,V>> left, Node<Pair<K,V>> right) {
        return new BSTNode<>(pair, parent, left, right);
    }

    /**
     * Relinks parent with its child
     * @param parent node
     * @param child node
     * @param makeLeftChild is it going to be left or right child
     */
    private void relink(Node<Pair<K,V>> parent, Node<Pair<K,V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    /**
     * Rotates position p around its parent.
     * @param p position
     */
    private void rotate(Position<Pair<K,V>> p) {
        Node<Pair<K,V>> x = validate(p);
        Node<Pair<K,V>> y = x.getParent();
        Node<Pair<K,V>> z = y.getParent();

        if (z == null) {
            root = x;
            x.setParent(null);
        } else
            relink(z, x, y == z.getLeft());

        if (x == y.getLeft()) {
            relink(y, x.getRight(), true);
            relink(x ,y , false);
        } else {
            relink(y, x.getLeft(), false);
            relink(x, y, true);
        }
    }

    /**
     * Trinode restructure of position X.
     * @param x position.
     * @return new subtree root.
     */
    Position<Pair<K,V>> restructure(Position<Pair<K, V>> x) {
        Position<Pair<K,V>> y = parent(x);
        System.out.println(y);
        Position<Pair<K,V>> z = parent(y);
        if ((x == right(y)) == (y == right(z))) {
            rotate(y);
            return y;
        } else {
            rotate(x);
            rotate(x);
            return x;
        }
    }
}
