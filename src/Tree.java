import java.util.Iterator;

/**
 * Interface abstracts general methods of working with the trees of any kind.
 * @param <T>
 */
public interface Tree<T> extends Iterable {
    Position<T> root();
    Position<T> parent(Position<T> p) throws IllegalArgumentException;
    Iterable<Position<T>> children(Position<T> p) throws IllegalArgumentException;
    int numChildren(Position<T> p);
    boolean isInternal(Position<T> p) throws IllegalArgumentException;
    boolean isExternal(Position<T> p) throws IllegalArgumentException;
    boolean isRoot(Position<T> p) throws IllegalArgumentException;
    int size();
    boolean isEmpty();
    Iterator<T> iterator();
    Iterable<Position<T>> positions();
}
