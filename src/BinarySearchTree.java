import java.io.PrintWriter;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Class implements the BST.
 * My implementation has impressive inheritance,
 * because it is a logical way to construct the collection of trees.
 * @param <K>
 * @param <V>
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> extends LinkedBinaryTree<Pair<K, V>> {

    BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    private Comparator<K> comp = Comparator.naturalOrder();

    /**
     * Method for comparing two entries according to key.
     */
    protected int compare(Pair<K, V> a, Pair<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    /**
     * Method for comparing a key and an entry's key.
     */
    private int compare(K a, Pair<K, V> b) {
        return comp.compare(a, b.getKey());
    }

    /**
     * Method for comparing a key and an entry's key.
     */
    protected int compare(Pair<K, V> a, K b) {
        return comp.compare(a.getKey(), b);
    }

    /**
     * Method for comparing two keys.
     */
    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }


    /**
     * Constructor.
     */
    BinarySearchTree() {
        super();
        tree.addRoot(null);
    }

    public int size() {
        return (tree.size() - 1) / 2;
    }

    /**
     * Insert a new entry at a leaf of the tree.
     *
     * @param p    position.
     * @param pair entry.
     */
    private void expandExternal(Position<Pair<K, V>> p, Pair<K, V> pair) {
        tree.set(p, pair);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    public Position<Pair<K, V>> root() {
        return tree.root();
    }

    /**
     * Search for the given Key in the tree.
     *
     * @param p   position.
     * @param key given key.
     * @return position with the given key.
     */
    Position<Pair<K, V>> search(Position<Pair<K, V>> p, K key) {
        if (isExternal(p))
            return p;
        int comp = compare(key, p.getElement());
        if (comp == 0)
            return p;
        else if (comp < 0)
            return search(left(p), key);
        else
            return search(right(p), key);
    }

    /**
     * Get the value if the given key.
     *
     * @param key of the node.
     * @return value of the node.
     * @throws IllegalArgumentException if key is illegal.
     */
    public V get(K key) throws IllegalArgumentException {
        Position<Pair<K, V>> p = search(root(), key);
        if (isExternal(p))
            return null;
        else
            return p.getElement().getValue();
    }

    /**
     * Finds the maximum of the subtree of given position.
     *
     * @param p position.
     * @return right most node.
     */
    private Position<Pair<K, V>> treeMax(Position<Pair<K, V>> p) {
        Position<Pair<K, V>> traverse = p;
        while (isInternal(traverse))
            traverse = right(traverse);
        return parent(traverse);
    }

    /**
     * Insert an element to the tree.
     *
     * @param key   of the element.
     * @param value value of the element.
     * @return old value of that node, if exists.
     * @throws IllegalArgumentException if given arguments are wrong.
     */
    V put(K key, V value) throws IllegalArgumentException {
        Pair<K, V> pair = new Pair<>(key, value);
        Position<Pair<K, V>> p = search(root(), key);
        if (isExternal(p)) {
            expandExternal(p, pair);
            rebalanceInsert(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            set(p, pair);
            return old;
        }
    }

    /**
     * Delete an element from the tree.
     *
     * @param key of the element.
     * @return value of removed element.
     * @throws IllegalArgumentException
     */
    V remove(K key) throws IllegalArgumentException {
        Position<Pair<K, V>> p = search(root(), key);
        if (isExternal(p)) {
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) {
                Position<Pair<K, V>> replace = treeMax(left(p));
                set(p, replace.getElement());
            }
            Position<Pair<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Pair<K, V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }


    /**
     * Prints the tree in the asked order using BFS.
     *
     * @param p      position.
     * @param writer PrintWriter object.
     */
    void print(Position<Pair<K, V>> p, PrintWriter writer) {
        LinkedList<Position<Pair<K, V>>> q = new LinkedList<>();
        q.add(p);
        boolean line = false;
        while (!q.isEmpty()) {
            Position<Pair<K, V>> node = q.remove();
            if (isInternal(node)) {
                if (left(node) != null) q.add(left(node));
                if (right(node) != null) q.add(right(node));
                printPos(node, writer, line);
                line = true;
            }
        }
    }

    /**
     * Special method for printing Position with its children in one line.
     */
    private void printPos(Position<Pair<K, V>> p, PrintWriter writer, boolean line) {
        if (isExternal(p)) return;
        if (isExternal(left(p)) && isExternal(right(p))) return;
        if (line) writer.println();
        writer.print(p.getElement().getKey());
        if (isInternal(left(p)))
            writer.print(" " + left(p).getElement().getKey().toString());
        if (isInternal(right(p)))
            writer.print(" " + right(p).getElement().getKey().toString());
    }

    /**
     * Method prints the tree using BFS algorithm,
     * but in mirror order.
     * So left child of every node becomes right and vice versa.
     *
     * @param p      posiion.
     * @param writer PrintWriter instance.
     */
    public void mirror(Position<Pair<K, V>> p, PrintWriter writer) {
        LinkedList<Position<Pair<K, V>>> q = new LinkedList<>();
        q.add(p);
        boolean line = false;
        while (!q.isEmpty()) {
            Position<Pair<K, V>> node = q.remove();
            if (isInternal(node)) {
                if (right(node) != null) q.add(right(node));
                if (left(node) != null) q.add(left(node));
                printPosMirror(node, writer, line);
                line = true;
            }
        }
    }

    /**
     * Supports the mirror() method.
     */
    private void printPosMirror(Position<Pair<K, V>> p, PrintWriter writer, boolean line) {
        if (isExternal(p)) return;
        if (isExternal(left(p)) && isExternal(right(p))) return;
        if (line) writer.println();
        writer.print(p.getElement().getKey());
        if (isInternal(right(p)))
            writer.print(" " + right(p).getElement().getKey().toString());
        if (isInternal(left(p)))
            writer.print(" " + left(p).getElement().getKey().toString());
    }


    /**
     * Hooks for the methods of balanced tree.
     * They must be implemented in the extending balanced tree class,
     * like AVL or RB.
     *
     * @param p root of the subtree.
     */
    protected void rebalanceInsert(Position<Pair<K, V>> p) {
    }

    protected void rebalanceDelete(Position<Pair<K, V>> p) {
    }
}
