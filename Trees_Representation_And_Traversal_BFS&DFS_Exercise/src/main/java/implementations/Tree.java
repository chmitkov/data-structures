package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();
        Arrays.stream(children)
                .forEach(eTree -> {
                    eTree.setParent(this);
                    this.children.add(eTree);
                });
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();

        getAsString(sb, this, 0);

        return sb.toString().trim();
    }

    private void getAsString(StringBuilder result, Tree<E> tree, int indentation) {
        result.append(getPadding(indentation))
                .append(tree.getKey())
                .append(System.lineSeparator());

        tree
                .children
                .forEach(child -> getAsString(result, child, indentation + 2));
    }

    private String getPadding(int indentation) {
        //Doesn't work in Judge :)
        //return " ".repeat(Math.max(0, size));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> result = new ArrayList<>();

        children
                .forEach(child -> getLeafKeys(result, child));

        return result
                .stream()
                .sorted()
                .collect(Collectors.toList());

    }

    private void getLeafKeys(List<E> result, Tree<E> child) {

        if (child.children.isEmpty()) {
            result.add(child.key);
            return;
        }

        for (Tree<E> ch : child.children) {
            if (ch.children.isEmpty()) {
                result.add(ch.key);
            }
        }
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> result = new ArrayList<>();
        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            if (current.parent != null && !current.children.isEmpty()) {
                result.add(current.key);
            }

            current
                    .children
                    .forEach(queue::offer);
        }

        return result;
    }


    @Override
    public Tree<E> getDeepestLeftmostNode() {
        Queue<Tree<E>> queue = new ArrayDeque<>();
        Tree<E> best = null;
        int counter = 0;

        queue.add(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            int maxPath = getMaxPath(current);

            if (maxPath > counter) {
                best = current;
                counter = maxPath;
            }

            current
                    .children
                    .forEach(queue::offer);
        }

        return best;
    }


    private int getMaxPath(Tree<E> tree) {

        int counter = 0;

        if (tree.parent == null) {
            return counter;
        }

        while (tree.parent != null) {
            counter++;

            tree = tree.parent;
        }

        return counter;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> leaves = getAllLeavesAsCollectionOfTrees(this);
        int bestPath = 0;
        Tree<E> leafWithLongestPath = null;

        for (Tree<E> leaf : leaves) {
            int currentPath = getMaxPath(leaf);
            if (currentPath > bestPath) {
                leafWithLongestPath = leaf;
                bestPath = currentPath;
            }
        }
        ;

        List<E> result = getPathFromRootToLeaf(leafWithLongestPath);

        return result;
    }

    private List<E> getPathFromRootToLeaf(Tree<E> leafWithLongestPath) {
        List<E> result = new ArrayList<>();
        result.add(leafWithLongestPath.key);

        while (leafWithLongestPath.parent != null) {
            result
                    .add(leafWithLongestPath.parent.key);

            leafWithLongestPath = leafWithLongestPath.parent;
        }

        Collections.reverse(result);
        return result;
    }

    private List<Tree<E>> getAllLeavesAsCollectionOfTrees(Tree<E> tree) {
        List<Tree<E>> result = new ArrayList<>();

        if (tree == null) {
            return result;
        }

        if (tree.children.isEmpty()) {
            result.add(tree);
            return result;
        }

        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(tree);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            if (current.children.isEmpty()) {
                result.add(current);
            } else {
                current
                        .children
                        .forEach(queue::offer);
            }

        }


        return result;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<Tree<E>> leaves = getAllLeavesAsCollectionOfTrees(this);
        List<List<E>> result = new ArrayList<>();

        for (Tree<E> leaf : leaves) {
            List<E> getPatAsList = getPathFromRootToLeaf(leaf);

            int pathCount = getPatAsList
                    .stream()
                    .mapToInt(e -> Integer.parseInt(String.valueOf(e)))
                    .sum();
            if (pathCount == sum) {
                result.add(getPatAsList);
            }
        }


        return result;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {

        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.add(this);
        List<Tree<E>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            int treeSum = getSumOfTreeValues(current);

            if (treeSum == sum) {
                result.addAll(getPathFromRootToLeafAsListOfNodes(current));
            }

            current
                    .children
                    .forEach(queue::offer);
        }


        return result;
    }

    private List<Tree<E>> getPathFromRootToLeafAsListOfNodes(Tree<E> tree) {
        List<Tree<E>> result = new ArrayList<>();
        result.add(tree);

        while (tree.parent != null) {
            result
                    .add(tree.parent);

            tree = tree.parent;
        }

        Collections.reverse(result);
        return result;
    }


    private int getSumOfTreeValues(Tree<E> tree) {
        int result = 0;

        result += Integer.parseInt(String.valueOf(tree.key));

        result += tree
                .children.stream()
                .mapToInt(tree1 -> Integer.parseInt(String.valueOf(tree1.key)))
                .sum();

        return result;
    }
}



