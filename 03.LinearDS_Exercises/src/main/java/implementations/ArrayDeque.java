package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {

    private static final String RIGHT = "RIGHT";
    private static final String LEFT = "LEFT";

    private final int DEFAULT_CAPACITY = 7;
    private int headIdx;
    private int tailIdx;
    private int size;
    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.headIdx = this.elements.length / 2;
        this.tailIdx = this.headIdx;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    private Object[] grow() {
        Object[] grown = new Object[this.elements.length * 2 + 1];
        int index = this.headIdx;
        int middle = grown.length / 2;
        int begin = middle - this.size / 2;


        for (int i = begin; index <= this.tailIdx; i++) {
            grown[i] = this.elements[index++];
        }
        this.headIdx = begin;
        this.tailIdx = this.headIdx + this.size - 1;
        return grown;

    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    private void shiftElements(String... varArgs) {
        String selectedSide = varArgs.length == 0 ? LEFT : RIGHT;
        if (this.tailIdx + this.headIdx >= this.elements.length - 1) {
            grow();
        }

        Object[] arr = new Object[this.elements.length];
        // The default side for shifting is left
        if (RIGHT.equalsIgnoreCase(selectedSide)) {
            for (int i = this.size - 1; i > 0; i--) {
                arr[i + this.tailIdx] = this.elements[i];
            }
            ++this.tailIdx;
        } else {
            for (int i = 0; i < this.size; i++) {
                arr[this.headIdx + i - 1] = this.elements[i];
            }
            --this.headIdx;
        }
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.elements[this.headIdx] = element;
        } else {
            if (this.headIdx == 0) {
                this.elements = grow();
            }
            this.elements[--this.headIdx] = element;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[headIdx] = element;
        } else {
            if (this.tailIdx == this.elements.length - 1) {
                elements = grow();
            }
            ++this.tailIdx;
            this.elements[this.tailIdx] = element;
        }
        ++this.size;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.headIdx + index;
        ensureIndex(realIndex);
        if (realIndex - this.headIdx < this.tailIdx - realIndex) {
            insertAndShiftLeft(realIndex - 1, element);
        } else {
            insertAndShiftRight(realIndex, element);
        }
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.headIdx + index;
        ensureIndex(realIndex);
        this.elements[realIndex] = element;
    }


    @Override
    public E peek() {
        return isEmpty() ? null : this.getAt(this.headIdx);
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeLast();
    }



    @Override
    public E get(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.headIdx; i <= this.tailIdx; i++) {
            if (this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.headIdx + index;
        ensureIndex(realIndex);
        return getAt(realIndex);

    }

    @Override
    public E remove(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.headIdx; i <= this.tailIdx ; i++) {
            if (this.elements[i].equals(object)) {
                E element = this.getAt(i);
                this.elements[i] = null;
                for (int j = i; j < this.tailIdx; j++) {
                    this.elements[j] = this.elements[j+1];
                }
                this.removeLast();
                return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()) {
            E element = this.getAt(this.headIdx);
            this.elements[this.headIdx] = null;
            this.headIdx++;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()) {
            E element = this.getAt(this.tailIdx);
            this.elements[this.tailIdx] = null;
            this.tailIdx--;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {

        Object[] trimmed = new Object[this.size];
        int index = 0;
        for (int i = this.headIdx; i <= this.tailIdx; i++) {
            trimmed[index++] = this.elements[i];
        }
        this.elements = trimmed;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = headIdx;

            @Override
            public boolean hasNext() {
                return index <= tailIdx;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.elements.length; i++) {
            sb.append(String.format("Index:%s - Value: %s", i, this.elements[i]))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public E get(int index) {
        int realIndex = this.headIdx + index;
        ensureIndex(realIndex);
        return getAt(realIndex);
    }

    private void ensureIndex(int realIndex) {
        if (realIndex < this.headIdx || realIndex > this.tailIdx) {
            throw new IndexOutOfBoundsException();
        }
    }

    private E getAt(int index) {
        return (E) this.elements[index];
    }

    private void insertAndShiftRight(int index, E element) {
        E last = getAt(this.tailIdx);
        if (this.tailIdx - index >= 0)
            System.arraycopy(this.elements, index, this.elements,
                    index + 1, this.tailIdx - index);
        this.elements[index] = element;
        this.addLast(last);
    }

    private void insertAndShiftLeft(int index, E element) {
        E first = getAt(this.headIdx);
        for (int i = this.headIdx; i < index; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = element;
        this.addFirst(first);
    }
}
