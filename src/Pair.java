/**
 * Represents an entry in the hash table.
 * @param <K> Type of Key
 * @param <V> Type of Value
 */
public class Pair<K,V> {
    private K key;
    private V value;

    K getKey() { return key; }

    V getValue() { return value; }

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Converts a pair to string format, so it can be easily printed.
     * @return String representation.
     */
    public String toString() {
        return "<\"" + getKey() + "\", " + getValue() + ">";
    }
}