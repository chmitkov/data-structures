import implementations.TheMatrix;
import implementations.Tree;
import implementations.TreeFactory;

public class Main {
    public static void main(String[] args) {
        String[] input = {
                "7 19",
                "7 21",
                "7 14",
                "19 1",
                "19 12",
                "19 31",
                "14 23",
                "14 6"
        };
        TreeFactory treeFactory = new TreeFactory();
        Tree<Integer> tree = treeFactory.createTreeFromStrings(input);
    }
}
