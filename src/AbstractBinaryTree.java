import java.util.ArrayList;

/**
 * Same thing as about AbstractTree, but now we are abstracting
 * @param <T>
 */
public abstract class AbstractBinaryTree<T> extends AbstractTree<T> implements BinaryTree<T> {

    public Position<T> sibling(Position<T> p) {
        Position<T> parent = parent(p);

        if (parent == null)
            return null;
        if (p == left(parent))
            return right(parent);
        else
            return left(parent);
    }

    public int numChildren(Position<T> p) {
        int count = 0;

        if (left(p) != null)
            count++;
        if (right(p) != null)
            count++;

        return count;
    }

    public Iterable<Position<T>> children(Position<T> p) {
        ArrayList<Position<T>> snapshot = new ArrayList<>(2);

        if (left(p) != null)
            snapshot.add(left(p));
        if (right(p) != null)
            snapshot.add(right(p));

        return snapshot;
    }
}

