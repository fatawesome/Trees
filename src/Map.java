import java.util.ArrayList;

/**
 * Map ADT
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */
public interface Map<K, V> {

    /**
     * Operation to get the value of given key.
     * @param key .
     * @return Value dedicated to the given key.
     */
    V get(K key);

    /**
     * Method puts an entry with given key and value into the table.
     * @param key Given key.
     * @param value Given value.
     */
    V put(K key, V value);

    /**
     * Removes an entry with the given key from the hash table.
     * @param key Key to delete.
     * @return Value of the deleted entry.
     */
    V remove(K key);

    /**
     * @return Current size of the table.
     */
    int size();

    /**
     * @return True if table is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * @return set of keys from this map
     */
    ArrayList<K> keySet();

    /**
     * @return set of values from this map
     */
    ArrayList<V> values();
    /**
     * @return set of entries from this map
     */
    ArrayList<Pair<K,V>> entrySet();
}