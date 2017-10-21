import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Integer> inputSeq(Scanner in) {
        ArrayList<Integer> arr = new ArrayList<>();
        String[] strArray = in.nextLine().split(" ");
        for (String s : strArray)
            arr.add(Integer.parseInt(s));
        return arr;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter out = new PrintWriter(new File("output.txt"));

        ArrayList<Integer> input = inputSeq(in);
        int toFind = in.nextInt();
        int toRemove = in.nextInt();
        int toInsert = in.nextInt();

        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        for (int i : input)
            tree.put(i, ".");

        Iterable<Position<Pair<Integer, String>>> array1 = tree.inorder();
        for (Position<Pair<Integer, String>> p : array1)
            out.print(p.getElement().getKey() + " ");

        in.close();
        out.close();
    }

}
