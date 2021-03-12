package implementations;

import interfaces.AbstractTree;

import java.util.*;

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
        Deque<Tree<E>> stack = new ArrayDeque<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            Tree<E> current = stack.pop();
            sb.append(current.key);
            sb.append(System.lineSeparator());
            for (Tree<E> child : current.children) {
                stack.push(child);
            }
        }

        return sb.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            if (current.children.isEmpty()) {
                result.add(current.key);
            }
            for (Tree<E> child : current.children) {
                queue.offer(child);
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
        Tree<E> bestLeaf = null;
        for (Tree<E> leaf : leaves) {
            int currentScore = 0;
            Tree<E> currentLeaf = leaf;
            while (currentLeaf.parent != null) {
                ++currentScore;
                currentLeaf = currentLeaf.parent;
            }
            if (currentScore > best) {
                best = currentScore;
                bestLeaf = currentLeaf;
            }
        }

        return bestLeaf;
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
        List<E> longestPath = new ArrayList<>();
        int best = 0;

        for (Tree<E> child : this.children) {
            int currentBest = 0;
            List<E> currentRouth = new ArrayList<>();
            while (child.parent != null) {
                ++best;
                currentRouth.add(child.key);
                child = child.parent;
                if (currentBest > best) {
                    best = currentBest;
                    longestPath.addAll(currentRouth);
                }
            }
        }
        longestPath.add(0, this.key);
        return longestPath;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



