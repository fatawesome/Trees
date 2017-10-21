import java.util.ArrayList;
import java.util.Comparator;

public class TreeMap<K extends Comparable<? super K>,V> extends AbstractSortedMap<K,V> {

    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    public TreeMap() {
        super();
        tree.addRoot(null);
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null);
    }

    public int size() {
        return (tree.size() - 1) / 2;
    }

    /**
     * Insert a new entry at a leaf of the tree.
     * @param p position.
     * @param pair entry.
     */
    private void expandExternal(Position<Pair<K,V>> p, Pair<K,V> pair) {
        tree.set(p, pair);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    public Position<Pair<K,V>> root() {
        return tree.root();
    }

    /**
     * Search for the given Key in the tree.
     * @param p position.
     * @param key given key.
     * @return position with the given key.
     */
    private Position<Pair<K,V>> search(Position<Pair<K,V>> p, K key) {
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

    public V get(K key) throws IllegalArgumentException {
        Position<Pair<K,V>> p = search(root(), key);
        if (isExternal(p))
            return null;
        else
            return p.getElement().getValue();
    }

    /**
     * FInds the maximum of the subtree of given position.
     * @param p position.
     * @return right most node.
     */
    protected Position<Pair<K,V>> treeMax(Position<Pair<K,V>> p) {
        Position<Pair<K,V>> traverse = p;
        while (isInternal(traverse))
            traverse = right(traverse);
        return parent(traverse);
    }

    public V put(K key, V value) throws IllegalArgumentException {
        Pair<K,V> pair = new Pair<>(key, value);
        Position<Pair<K,V>> p = search(root(), key);
        if (isExternal(p)) {
            expandExternal(p, pair);
            rebalanceInsert(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            set(p, pair);
            rebalanceAccess(p);
            return old;
        }
    }

    public V remove(K key) throws IllegalArgumentException {
        Position<Pair<K,V>> p = search(root(), key);
        if (isExternal(p)) {
            rebalanceAccess(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) {
                Position<Pair<K,V>> replace = treeMax(left(p));
                set(p, replace.getElement());
            }
            Position<Pair<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Pair<K,V>> sib = sibling(leaf);
            remove(leaf.getElement().getKey());
            remove(p.getElement().getKey());
            rebalanceDelete(sib);
            return old;
        }
    }

    public ArrayList<Pair<K,V>> entrySet() {
        ArrayList<Pair<K,V>> buff = new ArrayList<>(size());
        for (Position<Pair<K,V>> p : tree.preorder())
            if (isInternal(p))
                buff.add(p.getElement());
        return buff;
    }

    protected void rebalanceInsert(Position<Pair<K,V>> p) {}
    protected void rebalanceAccess(Position<Pair<K,V>> p) {}
    protected void rebalanceDelete(Position<Pair<K,V>> p) {}
}
