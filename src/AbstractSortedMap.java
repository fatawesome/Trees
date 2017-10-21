import java.util.Comparator;

public abstract class AbstractSortedMap<K extends Comparable<? super K>,V> extends LinkedBinaryTree<Pair<K,V>> {

    // instance variable for an AbstractSortedMap
    /** The comparator defining the ordering of keys in the map. */
    private Comparator<K> comp = Comparator.<K>naturalOrder();

    /**
     * Initializes the comparator for the map.
     * @param c comparator defining the order of keys in the map
     */
    protected AbstractSortedMap(Comparator<K> c) {
        comp = c;
    }

    public AbstractSortedMap() {}

    /** Method for comparing two entries according to key */
    protected int compare(Pair<K,V> a, Pair<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    /** Method for comparing a key and an entry's key */
    protected int compare(K a, Pair<K,V> b) {
//        System.out.println(a + " " + b.getKey());
        return comp.compare(a, b.getKey());
    }

    /** Method for comparing a key and an entry's key */
    protected int compare(Pair<K,V> a, K b) {
        return comp.compare(a.getKey(), b);
    }

    /** Method for comparing two keys */
    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }

    /** Determines whether a key is valid. */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key,key)==0);   // see if key can be compared to itself
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }
}