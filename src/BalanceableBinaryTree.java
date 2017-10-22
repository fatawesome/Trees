/**
 * This class is a connection between BST and Balansed BST.
 * Methods of Trinode restructure are implemented here.
 *
 * @param <K> key type.
 * @param <V> value type.
 */
public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Pair<K, V>> {

    /**
     * Node of the Binary Search Tree.
     * It supports the "aux" concept.
     *
     * @param <E> type of the node.
     */
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

    /**
     * @param p postition.
     * @return aux of the given position.
     */
    int getAux(Position<Pair<K, V>> p) {
        return ((BSTNode<Pair<K, V>>) p).getAux();
    }

    /**
     * Sets new aux for the given position.
     *
     * @param p     position.
     * @param value new aux.
     */
    void setAux(Position<Pair<K, V>> p, int value) {
        ((BSTNode<Pair<K, V>>) p).setAux(value);
    }

    /**
     * Method creates a new node.
     *
     * @param pair   value of the node.
     * @param parent of the node.
     * @param left   child of the node.
     * @param right  child of the node.
     * @return new Node.
     */
    protected Node<Pair<K, V>> createNode(Pair<K, V> pair, Node<Pair<K, V>> parent,
                                          Node<Pair<K, V>> left, Node<Pair<K, V>> right) {
        return new BSTNode<>(pair, parent, left, right);
    }

    /**
     * Relinks parent with its child
     *
     * @param parent        node
     * @param child         node
     * @param makeLeftChild is it going to be left or right child
     */
    private void relink(Node<Pair<K, V>> parent, Node<Pair<K, V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    /**
     * Rotates position p around its parent.
     *
     * @param p position
     */
    private void rotate(Position<Pair<K, V>> p) {
        Node<Pair<K, V>> x = validate(p);
        Node<Pair<K, V>> y = x.getParent();
        Node<Pair<K, V>> z = y.getParent();

        if (z == null) {
            root = x;
            x.setParent(null);
        } else
            relink(z, x, y == z.getLeft());

        if (x == y.getLeft()) {
            relink(y, x.getRight(), true);
            relink(x, y, false);
        } else {
            relink(y, x.getLeft(), false);
            relink(x, y, true);
        }
    }

    /**
     * Trinode restructure of position X.
     *
     * @param x position.
     * @return new subtree root.
     */
    Position<Pair<K, V>> restructure(Position<Pair<K, V>> x) {
        Position<Pair<K, V>> y = parent(x);
        Position<Pair<K, V>> z = parent(y);
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
