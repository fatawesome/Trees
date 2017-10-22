import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Integer> inputSeq(Scanner in) {
        ArrayList<Integer> arr = new ArrayList<>();
        String[] strArray = in.nextLine().split(" ");
        for (String s : strArray)
            arr.add(Integer.parseInt(s));
        return arr;
    }

    private static int calculateSum(MyAVL<Integer, Integer> tree) {
        Iterable<Position<Pair<Integer, Integer>>> collection = tree.inorder();

        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter out = new PrintWriter(new File("output.txt"));

        ArrayList<Integer> input = inputSeq(in);
//        int toFind = in.nextInt();
//        int toRemove = in.nextInt();
//        int toInsert = in.nextInt();
//
//        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
//        for (int i : input)
//            tree.put(i, i);
//
//        out.println(tree.search(tree.root(), toFind).getElement().getKey());
//        tree.remove(toRemove);
//        tree.put(toInsert, toInsert);
//
//        Iterable<Position<Pair<Integer, Integer>>> array1 = tree.inorder();
//        for (Position<Pair<Integer, Integer>> p : array1)
//            out.print(p.getElement().getKey() + " ");
//        out.println();
//
//        out.println("BST:");
//        tree.breadthFirst(tree.root(), out);
//        out.println();
//        out.println("BSMT:");
//        tree.mirror(tree.root(), out);

        MyAVL<Integer, Integer> tree = new MyAVL<>();
        for (int i : input)
            tree.put(i, i);

//        Iterable<Position<Pair<Integer, Integer>>> array1 = tree.inorder();
//        for (Position<Pair<Integer, Integer>> p : array1)
//            out.print(p.getElement().getKey() + " ");
//        out.println();

        out.print(tree.getThisSum());

        in.close();
        out.close();

    }

}
