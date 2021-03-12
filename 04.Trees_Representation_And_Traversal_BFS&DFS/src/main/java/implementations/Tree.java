package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }


    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            result.add(tree.key);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();
        dfs(this, result);
        return result;
    }

    private void dfs(Tree<E> eTree, List<E> result) {
        for (Tree<E> child : eTree.children) {
            dfs(child, result);
        }
        result.add(eTree.key);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parent = findByKey(parentKey);
        parent.children.add(child);
        child.parent = parent;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> node = findByKey(nodeKey);
        node.parent.children.remove(node);
        node.parent = null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstTree = findByKey(firstKey);
        Tree<E> secondTree = findByKey(secondKey);

        if (firstTree == null || secondTree == null) {
            throw new IllegalArgumentException();
        }

        if (firstTree.parent == null) {
            firstTree = secondTree;
        } else if (secondTree.parent == null) {
            secondTree = firstTree;
        }

        Tree<E> firstParent = firstTree.parent;
        Tree<E> secondParent = secondTree.parent;

        Tree<E> temp = firstTree;

        int firstIdx = firstTree.parent.children.indexOf(firstTree);
        int secondIdx = secondParent.children.indexOf(secondTree);

        firstTree.parent = secondParent;
        firstParent.children.set(firstIdx, secondTree);

        secondTree.parent = firstParent;
        secondParent.children.set(secondIdx, temp);



    }

    private Tree<E> findByKey(E key) {
        Tree<E> searched;
        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            for (Tree<E> child : current.children) {
                if (child.key.equals(key)) {
                    return child;
                }
                queue.offer(child);
            }
        }
        return null;
    }
}



