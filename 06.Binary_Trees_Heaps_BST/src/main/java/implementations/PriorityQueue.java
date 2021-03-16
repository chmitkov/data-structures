package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";

    private ArrayList<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        if (this.elements.size() != 1) {
            this.heapyfyUp();
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E resultElement = this.elements.get(0);
        Collections.swap(this.elements, 0, size() - 1);
        this.elements.remove(resultElement);
        heapifyDown(0);

        return resultElement;
    }

    private void heapyfyUp() {
        int newElementIdx = this.elements.size() - 1;
        int parentIndex = getParentIdx(newElementIdx);

        while (getParentIdx(newElementIdx) > -1
                && this.elements.get(newElementIdx)
                .compareTo(this.elements.get(parentIndex)) > 0) {

            Collections.swap(this.elements, newElementIdx, parentIndex);
            newElementIdx = parentIndex;
            parentIndex = getParentIdx(newElementIdx);
        }
    }

    private void heapifyDown(int index) {
        while (index < this.elements.size() / 2) {
            int child = getChildIdx(index, LEFT);

            if (child + 1 < this.elements.size()
                    && isLess(getElement(child), getElement(child + 1))) {
                child = child + 1;
            }

            if (isLess(getElement(child), getElement(index))) {
                break;
            }
            Collections.swap(this.elements, child, index);
            index = child;
        }
    }

    private int getParentIdx(int newElementIdx) {
        return (newElementIdx - 1) / 2;
    }


    private boolean isLess(E firstElement, E secondElement) {
        return firstElement.compareTo(secondElement) < 0;
    }


    private E getElement(int idx) {
        return this.elements.get(idx);
    }

    private int getChildIdx(int parentIdx, String side) {
        return 2 * parentIdx + (LEFT.equals(side) ? 1 : 2);
    }

    private void ensureNonEmpty() {
        if (this.elements.size() == 0) {
            throw new IllegalStateException("The heap is empty!");
        }
    }
}
