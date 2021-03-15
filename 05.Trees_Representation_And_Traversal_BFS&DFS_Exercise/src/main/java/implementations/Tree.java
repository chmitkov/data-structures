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
        this.children.addAll(Arrays.asList(children));
        this.children.forEach(eTree -> eTree.setParent(this));
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

        traverseTreeWithRecurrence(sb, 0, this);

        return sb.toString().trim();
    }

    private String getPadding(int size) {
        //Doesn't work in Judge :)
        //return " ".repeat(Math.max(0, size));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    private void traverseTreeWithRecurrence(StringBuilder sb, int indent, Tree<E> tree) {

        sb.append(getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(sb, indent + 2, child);
        }

    }

    @Override
    public List<E> getLeafKeys() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            current
                    .children
                    .forEach(queue::offer);

            if (current.children.isEmpty()) {
                result.add(current.getKey());
            }
        }

        return result;
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            if (!current.children.isEmpty() && current.parent != null) {
                result.add(current.key);
            }
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> leaves = getLeaves();
        int best = 0;
        Tree<E> deepestLeaf = this;

        for (Tree<E> leaf : leaves) {
            if (leaf.parent != null && leaf.children.isEmpty()) {
                int path = getMaxPathCount(leaf);

                if (path > best) {
                    best = path;
                    deepestLeaf = leaf;
                }
            }
        }

        return deepestLeaf;
    }

    private int getMaxPathCount(Tree<E> tree) {
        int result = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            ++result;
            current = current.parent;
        }
        return result;
    }

    private List<Tree<E>> getLeaves() {
        List<Tree<E>> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            if (current.children.isEmpty()) {
                result.add(current);
            }
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> leaves = getLeaves();
        int best = 0;
        List<E> pathIndexes = new ArrayList<>();

        for (Tree<E> leaf : leaves) {
            if (leaf.parent != null && leaf.children.isEmpty()) {
                List<E> maxPathCountAndIndexes = getMaxPathCountAndIndexes(leaf);

                if (maxPathCountAndIndexes.size() > best) {
                    best = maxPathCountAndIndexes.size();
                    pathIndexes = maxPathCountAndIndexes;
                }
            }
        }

        return pathIndexes;
    }

    private List<E> getMaxPathCountAndIndexes(Tree<E> leaf) {
        List<E> result = new ArrayList<>();
        int longestPath = 0;
        int currentPath = 0;
        List<E> pathHistory = new ArrayList<>();
        Tree<E> current = leaf;
        while (current.parent != null) {
            pathHistory.add(current.key);
            ++currentPath;
            current = current.parent;
            if (currentPath > longestPath) {
                longestPath = currentPath;
                result = pathHistory;
            }
        }
        result.add(this.key);
        Collections.reverse(result);
        return result;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<Tree<E>> leaves = getLeaves();
        List<List<E>> resultPaths = new ArrayList<>();

        for (Tree<E> leaf : leaves) {
            Tree<E> currentLeaf = leaf;
            int totalSum = (int) this.key;
            List<E> path = new ArrayList<>();

            while (currentLeaf.parent != null && totalSum <= sum) {
                totalSum += (int) currentLeaf.key;
                path.add(currentLeaf.key);

                currentLeaf = currentLeaf.parent;
            }

            if (totalSum == sum) {
                path.add(this.key);
                Collections.reverse(path);
                resultPaths.add(path);
            }

        }
        return resultPaths;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {

        return getMiddleTreesWithBFS()
                .stream().filter(x -> getSumOfTree(x) == sum)
                .collect(Collectors.toList());
    }

    private int getSumOfTree(Tree<E> tree) {
        int result = 0;
        Deque<Tree<E>> queue = new ArrayDeque<>() {{
            offer(tree);
        }};

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            result += (int) current.getKey();
            current.children.forEach(queue :: offer);
        }
        return result;
    }

    private List<Tree<E>> getMiddleTreesWithBFS() {
        List<Tree<E>> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            if (current.parent != null && !current.children.isEmpty()) {
                result.add(current);
            }
            current.children.forEach(queue::offer);
        }

        return result;
    }
}



