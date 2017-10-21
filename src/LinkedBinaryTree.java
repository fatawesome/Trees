import java.util.ArrayList;

public class LinkedBinaryTree<T> extends AbstractBinaryTree<T> {

    protected static class Node<T> implements Position<T> {
        private T value;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        public Node(T value, Node<T> parent, Node<T> left, Node<T> right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getElement() {
            return value;
        }
        public Node<T> getParent() {
            return parent;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }
    }

    protected Node<T> createNode(T value, Node<T> parent, Node<T> left, Node<T> right) {
        return new Node<T>(value, parent, left, right);
    }

    protected Node<T> root = null;
    private int size = 0;

    public LinkedBinaryTree() {}

    protected Node<T> validate(Position<T> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type");
        }
        Node<T> n = (Node<T>) p;
        if (n.getParent() == n) {
            throw new IllegalArgumentException("P is no longer in a tree");
        }
        return n;
    }

    public int size() {
        return size;
    }

    private void preorderSubtree(Position<T> p, ArrayList<Position<T>> snapshot) {
        if (isInternal(p))
            snapshot.add(p);
        for (Position<T> c : children(p))
            preorderSubtree(c, snapshot);
    }

    public Iterable<Position<T>> preorder() {
        ArrayList<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }

    private void inorderSubtree(Position<T> p, ArrayList<Position<T>> snapshot) {
        if (p.getElement() != null) {
            inorderSubtree(left(p), snapshot);
            snapshot.add(p);
            inorderSubtree(right(p), snapshot);
        }
    }

    public Iterable<Position<T>> inorder() {
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

    public Position<T> left(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        return n.getLeft();
    }

    public Position<T> right(Position<T> p) throws IllegalArgumentException {
        Node<T> n = validate(p);
        return n.getRight();
    }

    public Position<T> addRoot(T value) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("Tree is not empty");
        root = createNode(value, null, null, null);
        size = 1;
        return root;
    }

    public Position<T> addLeft(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("P already has left child");
        Node<T> child = createNode(value, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<T> addRight(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("P already has right child");
        Node<T> child = createNode(value, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public T set(Position<T> p, T value) throws IllegalArgumentException {
        Node<T> n = validate(p);
        T temp = n.getElement();
        n.setValue(value);
        return temp;
    }

    /**
     * Attach tree1 and tree2 as left and right subtrees respectively to node P
     * @param p node
     * @param tree1 left subtree
     * @param tree2 right subtree
     */
    public void attach(Position<T> p, LinkedBinaryTree<T> tree1, LinkedBinaryTree<T> tree2) {
        Node<T> n = validate(p);
        if (isInternal(p))
            throw new IllegalArgumentException("P must be a leaf");
        size += tree1.size() + tree2.size();
        if (!tree1.isEmpty()) {
            tree1.root.setParent(n);
            n.setLeft(tree1.root);
            tree1.root = null;
            tree1.size = 0;
        }
        if (!tree2.isEmpty()) {
            tree2.root.setParent(n);
            n.setRight(tree2.root);
            tree2.root = null;
            tree2.size = 0;
        }
    }


}
