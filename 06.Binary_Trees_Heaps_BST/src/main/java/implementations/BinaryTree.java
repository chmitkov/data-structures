package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {

    private E key;
    private BinaryTree<E> left;
    private BinaryTree<E> right;


    public BinaryTree(E key, BinaryTree<E> left, BinaryTree<E> right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder result = new StringBuilder();

        result.append(getPadding(indent))
                .append(this.key);

        if (getLeft() != null) {
            result.append(System.lineSeparator())
                    .append(getLeft().asIndentedPreOrder(indent + 2));
        }

        if (getRight() != null) {
            result.append(System.lineSeparator())
                    .append(getRight().asIndentedPreOrder(indent + 2));

        }

        return result.toString();
    }

    private String getPadding(int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        result.add(this);

        if (this.getLeft() != null) {
            result.addAll(this.getLeft().preOrder());
        }

        if (this.getRight() != null) {
            result.addAll(this.getRight().preOrder());
        }

        return result;
    }


    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            result.addAll(this.getLeft().inOrder());
        }

        result.add(this);

        if (this.getRight() != null) {
            result.addAll(this.getRight().inOrder());
        }

        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            result.addAll(this.getLeft().postOrder());
        }

        if (this.getRight() != null) {
            result.addAll(this.getRight().postOrder());
        }

        result.add(this);

        return result;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {

        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }
        consumer.accept(this.key);

        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }

    }
}
