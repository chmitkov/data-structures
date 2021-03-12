package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, Tree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        for (String line : input) {
            String[] nodeValues = line.split("\\s+");

            addEdge(Integer.parseInt(nodeValues[0]), Integer.parseInt(nodeValues[1]));
        }

        return getRoot();
    }

    private Tree<Integer> getRoot() {
        for (Tree<Integer> tree : nodesByKeys.values()) {
            if (tree.getParent() == null) {
                return tree;
            }
        }
        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key, new Tree<>(key));
        return nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> tree = createNodeByKey(parent);
        Tree<Integer> childTree = createNodeByKey(child);

        tree.addChild(childTree);
        childTree.setParent(tree);
    }
}



