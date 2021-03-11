package implementations;


import java.util.Iterator;

public class ReversedList<E> implements Iterable {
    private final int INITIAL_CAPACITY = 2;
    private Object[] elements;
    private int lastIdx;
    private int size;
    private int capacity;
    private int firstIdx;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.lastIdx = -1;
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
        this.firstIdx = 0;
    }

    public void add(E element) {

        if (this.size++ == elements.length) {
            this.elements = grow(this.elements);
        }
        this.elements[firstIdx++] = element;
        this.lastIdx++;

    }

    private Object[] grow(Object[] elements) {
        this.capacity *= 2;
        Object[] grown = new Object[this.capacity];
        for (int i = 0; i < elements.length; i++) {
            grown[i] = elements[i];
        }
        return grown;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public E get(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (E) this.elements[this.size - 1 - index];
    }

    public E removeAt(int index) {
        int realIndex = this.size - 1 - index;
        if (realIndex >= this.elements.length || realIndex < 0) {
            return null;
        }
        this.lastIdx--;
        this.size--;
        this.firstIdx--;
        E old = (E) this.elements[realIndex];
        this.elements[realIndex] = null;
        this.elements = trimm(this.elements);
        return old;
    }

    private Object[] trimm(Object[] elements) {
        Object[] trimmed = new Object[elements.length];
        int index = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                trimmed[index++] = elements[i];
            }
        }
        return trimmed;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int idx = lastIdx;

            @Override
            public boolean hasNext() {
                return lastIdx >= 1;
            }

            @Override
            public E next() {
                return (E)elements[--idx];
            }
        };
    }
}
