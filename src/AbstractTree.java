import java.util.Iterator;

/**
 * AbstractTree implements some of the methods to work with the tree,
 * but still has a lot of abstract things, so we can easily extend
 * other different trees from here and be able to use these methods.
 * @param <T>
 */
public abstract class AbstractTree<T> implements Tree<T> {
    public boolean isInternal(Position<T> p) {
        return numChildren(p) > 0;
    }
    public boolean isExternal(Position<T> p) {
        return numChildren(p) == 0;
    }
    public boolean isRoot(Position<T> p) {
        return p == root();
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    public int depth(Position<T> p) {
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }

    public int height(Position<T> p) {
        int h = 0;
        for (Position<T> c : children(p))
            h = Math.max(h, 1 + height(c));
        return h;
    }

    private class ElementIterator implements Iterator<T> {
        Iterator<Position<T>> posIterator = positions().iterator();
        public boolean hasNext() {
            return posIterator.hasNext();
        }
        public T next() {
            return posIterator.next().getElement();
        }
        public void remove() {
            posIterator.remove();
        }
    }

    public Iterator<T> iterator() {
        return new ElementIterator();
    }
}
