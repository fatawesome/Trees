import java.util.ArrayList;

/**
 * Class represents the LinkedBinaryTree structure.
 * It is an intermediate step to more complicated trees.
 *
 * @param <T>
 */
public class LinkedBinaryTree<T> extends AbstractBinaryTree<T> {

    /**
     * Simple node of the tree.
     * It implements the Position interface, thereby supports iterative collections.
     */
    protected static class Node<T> implements Position<T> {
        private T value;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        Node(T value, Node<T> parent, Node<T> left, Node<T> right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getElement() {
            return value;
        }

        Node<T> getParent() {
            return parent;
        }

        Node<T> getLeft() {
            return left;
        }

        Node<T> getRight() {
            return right;
        }

        void setValue(T value) {
            this.value = value;
        }

        void setLeft(Node<T> left) {
            this.left = left;
        }

        void setRight(Node<T> right) {
            this.right = right;
        }

        void setParent(Node<T> parent) {
            this.parent = parent;
        }
    }

    /**
     * Returns new Node with the given parameters.
     *
     * @param value  of the node.
     * @param parent of the node.
     * @param left   child of the node.
     * @param right  child of the node.
     * @return new Node.
     */
    protected Node<T> createNode(T value, Node<T> parent, Node<T> left, Node<T> right) {
        return new Node<>(value, parent, left, right);
    }

    Node<T> root = null;
    private int size = 0;

    /**
     * Simple constructor.
     */
    LinkedBinaryTree() {
    }

    /**
     * Method validates given Position, so we can be sure, that it is not null and
     * doesn't have any illegal parameters.
     *
     * @param p Position.
     * @return Node of the given Position.
     * @throws IllegalArgumentException if something is wrong with the position.
     */
    Node<T> validate(Position<T> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type");
        }
        Node<T> n = (Node<T>) p;
        if (n.getParent() == n) {
            throw new IllegalArgumentException("P is no longer in a tree");
        }
        return n;
    }

    /**
     * @return size of the tree.
     */
    public int size() {
        return size;
    }

    /**
     * Method supports preorder().
     */
    private void preorderSubtree(Position<T> p, ArrayList<Position<T>> snapshot) {
        if (isInternal(p))
            snapshot.add(p);
        for (Position<T> c : children(p))
            preorderSubtree(c, snapshot);
    }

    /**
     * @return Iterable collection of elements of the tree
     * using preorder rule.
     */
    Iterable<Position<T>> preorder() {
        ArrayList<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }

    /**
     * Method supports inorder();
     */
    private void inorderSubtree(Position<T> p, ArrayList<Position<T>> snapshot) {
        if (p.getElement() != null) {
            inorderSubtree(left(p), snapshot);
            snapshot.add(p);
            inorderSubtree(right(p), snapshot);
        }
    }

    /**
     * @return I
     * Iterable collection of elements of the tree
     * using inorder rule.
     */
    Iterable<Position<T>> inorder() {
        ArrayList<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);
        return snapshot;
    }

    @Override
    public Iterable<Position<T>> positions() {
        return preorder();
    }

    public Position<T> root() {
        return root;
    }

    public Position<T> parent(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        return n.getParent();
    }

    /**
     * Method returns the left child of the given position.
     */
    public Position<T> left(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        return n.getLeft();
    }

    /**
     * Method returns the right child of the given position.
     */
    public Position<T> right(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        return n.getRight();
    }

    /**
     * Method adds the new root to the tree.
     *
     * @param value of the new root.
     * @return new root.
     * @throws IllegalStateException if tree is already not empty.
     */
    Position<T> addRoot(T value) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("Tree is not empty");
        root = createNode(value, null, null, null);
        size = 1;
        return root;
    }

    /**
     * Method adds new left child to the given position.
     */
    Position<T> addLeft(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("P already has left child");
        Node<T> child = createNode(value, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Method adds new right child to the given position.
     */
    Position<T> addRight(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("P already has right child");
        Node<T> child = createNode(value, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Method sets new value to the given position.
     */
    T set(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> n = validate(p);
        T temp = n.getElement();
        n.setValue(value);
        return temp;
    }

    /**
     * Method removes the given position,
     * but only, if it has not more than one child.
     */
    T remove(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        if (numChildren(p) == 2)
            throw new IllegalArgumentException("p has two children");
        Node<T> child = (n.getLeft() != null ? n.getLeft() : n.getRight());

        if (child != null)
            child.setParent(n.getParent());
        if (n == root)
            root = child;
        else {
            Node<T> parent = n.getParent();
            if (n == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }

        size--;
        T temp = n.getElement();
        n.setValue(null);
        n.setLeft(null);
        n.setRight(null);
        n.setParent(n);

        return temp;
    }

}
