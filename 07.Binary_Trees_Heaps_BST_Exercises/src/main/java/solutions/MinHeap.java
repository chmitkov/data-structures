package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private ArrayList<E> elements;

    public MinHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        if (size() > 1) {
            int index = this.elements.indexOf(element);
            heapifyUp(index);
        }
    }

    private void heapifyUp(int index) {
        int parentIdx = getParentIndex(index);

        if (index > 0 && getElement(index).compareTo(getElement(parentIdx)) < 0) {
            Collections.swap(this.elements, index, parentIdx);
            heapifyUp(parentIdx);
        }

    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private E getElement(int index) {
        return this.elements.get(index);
    }

    private void ensureNonEmpty() {
        if (size() == 0) {
            throw new IllegalStateException("The heap is empty");
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return getElement(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.elements.get(0);
        this.elements.remove(0);
        heapifyDown(0);
        return element;
    }

    private void heapifyDown(int index) {
        if (this.elements.size() < 1) {
            return;
        }

        int leftChildIdx = 2 * index + 1;

        if (leftChildIdx < this.elements.size()
                && getElement(index).compareTo(getElement(leftChildIdx)) > 0) {

            Collections.swap(this.elements, index, leftChildIdx);
            heapifyDown(leftChildIdx);
        }

    }

    @Override
    public void decrease(E element) {
        ensureNonEmpty();
        int index = this.elements.indexOf(element);
        getElement(index).decrease();
        heapifyUp(index);

    }

}
