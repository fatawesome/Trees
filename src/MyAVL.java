/**
 * Impleementation of the AVL tree.
 * @param <K>
 * @param <V>
 */
public class MyAVL<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    /**
     * Constructor
     */
    MyAVL() {
        super();
    }

    /**
     * Method supports the following trinode restructure.
     *
     * @param p position.
     * @return aux of the given position.
     */
    public int height(Position<Pair<K, V>> p) {
        return tree.getAux(p);
    }

    /**
     * Recalculates height of the given
     *
     * @param p
     */
    private void recomputeHeight(Position<Pair<K, V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    private boolean isBalanced(Position<Pair<K, V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /**
     * Method returns the taller child of the given position.
     * It supports the rebalance() mrthod.
     */
    private Position<Pair<K, V>> tallerChild(Position<Pair<K, V>> p) {
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

    /**
     * Method rebalances the subtree with the given root position
     * using Treenode restructure from the BalanceableBinaryTree class.
     */
    private void rebalance(Position<Pair<K, V>> p) {
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

    /**
     * Supports rebalancing after insertion.
     */
    protected void rebalanceInsert(Position<Pair<K, V>> p) {
        rebalance(p);
    }

    /**
     * Supports rebalancing after deletion.
     */
    protected void rebalanceDelete(Position<Pair<K, V>> p) {
        if (!isRoot(p))
            rebalance(parent(p));
    }

    /**
     * Method implements the feature, which is asked in the task.
     * I don't know, why is it so strange (easy).
     */
    int getThisSum() {
        return (size() * (size() - 1)) / 2;
    }

}
