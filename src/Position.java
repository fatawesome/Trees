/**
 * This interface is the base of making iterative collecrtions of the nodes of trees.
 * @param <T>
 */
public interface Position<T> {
    T getElement() throws IllegalStateException;
}
