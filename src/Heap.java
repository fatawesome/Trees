import java.util.ArrayList;
import java.util.Comparator;

public class Heap<K extends Comparable<? super K>,V> {

    private Comparator<K> comp = Comparator.<K>naturalOrder();

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

    /**
     * Array to store the data.
     */
    private ArrayList<Pair<K,V>> data = new ArrayList<>();

    /**
     * Default constructor.
     */
    Heap() {
        super();
    }

    /**
     * Constructs a new heap from the given array.
     * @param array input.
     */
    Heap(ArrayList<Pair<K,V>> array) {
        data = array;
        for (int i = data.size() / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * @return data array.
     */
    public ArrayList<Pair<K, V>> getData() {
        return data;
    }

    /**
     * Adds a given pair to the heap.
     * @param key of the element.
     * @param value of the element.
     */
    void add(K key, V value) {
        Pair<K,V> pair = new Pair<>(key, value);
        data.add(pair);
        siftUp(data.size() - 1);
    }

    private void siftDown(int i, int size) {
        while (2 * i + 1 < size) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int j = left;

            if ((right < size) && (compare(data.get(right), data.get(left)) > 0))
                j = right;
            if (compare(data.get(i), data.get(j)) >= 0)
                break;

            swapPair(i,j);
            i = j;
        }
    }

    /**
     * Restores
     * @param i
     */
    private void siftUp(int i) {
        while (compare(data.get(i), data.get((i - 1) /2)) > 0) {
            swapPair(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * HeapSort
     */
    public void sort() {
        int heapSize = data.size();
        for (int i = 0; i < data.size() - 1; i++) {
            swapPair(0, data.size() - i - 1);
            heapSize--;
            siftDown(0, heapSize);
        }
    }

    /**
     * @return string representation of the data array.
     */
    public String toString() {
        return data.toString();
    }

    /**
     * Swaps two elements of the data array.
     * @param p1 index of pair1.
     * @param p2 index of pair2.
     */
    private void swapPair(int p1, int p2) {
        Pair<K,V> temp = data.get(p1);
        data.set(p1, data.get(p2));
        data.set(p2, temp);

    }

    /**
     * Restores the MaxHeap.
     * @param i index of the root.
     */
    private void heapify(int i) {
        int left, right, largest;
        for ( ; ; ) {
            left = 2 * i + 1;
            right = 2 * i + 2;
            largest = i;

            if ((left < data.size()) && (compare(data.get(left), data.get(largest)) > 0))
                largest = left;
            if ((right < data.size()) && (compare(data.get(right), data.get(largest)) > 0))
                largest = right;
            if (largest == i)
                break;

            swapPair(i, largest);
            i = largest;
        }
    }

    /**
     * @return root (maximum) element of MaxHeap
     */
    private Pair<K, V> getMax() {
        Pair<K,V> result = data.get(0);
        data.set(0, data.get(data.size() - 1));
        data.remove(data.size() - 1);
        heapify(0);
        return result;
    }

    /**
     * Return reverse-sorted array and clears the heap.
     * @return array.
     */
    ArrayList<Pair<K,V>> sortRevers() {
        ArrayList<Pair<K,V>> result = new ArrayList<>();

        for (int i = data.size() - 1; i >= 0; i--)
            result.add(getMax());

        return result;
    }
}
