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
        for (Tree<E> child : children) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    @Override
    public List<E> orderBfs() {
        Queue<Tree<E>> queue = new ArrayDeque<>();
        List<E> result = new ArrayList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            result.add(current.key);

            if (!current.children.isEmpty()) {
                current
                        .children
                        .forEach(queue::offer);
            }
        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        recursiveApproach(result, this);

        return result;
    }

    private void recursiveApproach(List<E> result, Tree<E> eTree) {
        if (!eTree.children.isEmpty()) {
            eTree
                    .children
                    .forEach(child -> recursiveApproach(result, child));
        }
        result.add(eTree.key);
    }


    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parent = findTreeByKey(parentKey);
        parent.children.add(child);
    }

    private Tree<E> findTreeByKey(E searchedKey) {
        if (searchedKey == this.key) {
            return this;
        }

        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            if (searchedKey == current.key) {
                return current;
            }

            if (!current.children.isEmpty()) {
                current
                        .children
                        .forEach(queue::offer);
            }
        }

        return null;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> toRemove = findTreeByKey(nodeKey);
        if (toRemove.parent == null) {
            this.key = null;
            this.children = null;
        }
        toRemove.getParent().children.remove(toRemove);
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = this.findTreeByKey(firstKey);
        Tree<E> secondNode = this.findTreeByKey(secondKey);

        if ( firstNode == null || secondNode == null) {
            throw new IllegalArgumentException();
        }

        if (firstNode.findTreeByKey(secondKey) != null) {
            firstNode.replaceNode(secondNode);
            return;
        } else if (secondNode.findTreeByKey(firstKey) != null) {
            secondNode.replaceNode(firstNode);
            return;
        }

        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;

        firstNode.parent = secondParent;
        secondNode.parent = firstParent;

        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex, secondNode);
        secondParent.children.set(secondIndex, firstNode);
    }

    private void replaceNode (Tree<E>  newNode) {
        this.key = newNode.key;
        this.children = newNode.children;

        newNode.key = null;
        newNode.parent.children.remove(newNode);
        for (Tree<E> child : newNode.children) {
            child.parent = this;
        }
    }

    public Tree<E> getParent() {
        return parent;
    }

    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }
}



