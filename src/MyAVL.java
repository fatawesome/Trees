import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.util.Comparator;

public class MyAVL<K extends Comparable<? super K>,V> extends TreeMap<K,V> {
    public MyAVL() {
        super();
    }

    public MyAVL(Comparator<K> comp) {
        super(comp);
    }

    public int height(Position<Pair<K, V>> p) {
        return tree.getAux(p);
    }

    protected void recomputeHeight(Position<Pair<K,V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    protected boolean isBalanced(Position<Pair<K,V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    protected Position<Pair<K,V>> tallerChild(Position<Pair<K,V>> p) {
        if (height(left(p)) > height(right(p)))
            return left(p);
        if (height(left(p)) < height(right(p)))
            return right(p);
        if (isRoot(p))
            return left(p);
        if (p == left(parent(p)))
            return left(p);
        else
            return right(p);
    }

    protected void rebalance(Position<Pair<K,V>> p) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p);
            if (!isBalanced(p)) {
                p = tree.restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while (oldHeight != newHeight && p != null);
    }

    protected void rebalanceInsert(Position<Pair<K,V>> p) {
        rebalance(p);
    }

    protected void rebalanceDelete(Position<Pair<K,V>> p) {
        if (!isRoot(p))
            rebalance(parent(p));
    }
}
