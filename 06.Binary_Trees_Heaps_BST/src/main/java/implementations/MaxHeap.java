package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private ArrayList<E> elements;

    public MaxHeap() {
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

    private int getParentIdx(int newElementIdx) {
        return (newElementIdx - 1) / 2;
    }

    @Override
    public E peek() {
        if (this.elements.size() == 0) {
            throw new IllegalStateException("Heep is empty!");
        }
        return this.elements.get(0);
    }
}
