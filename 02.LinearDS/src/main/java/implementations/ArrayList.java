package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private final int INITIAL_CAPACITY = 7;
    private static final String LEFT = "left";
    private static final String RIGHT = "right";

    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean add(E element) {
        if (size >= elements.length) {
            grow();
        }
        try {
            elements[size++] = element;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (!isValidIndex(index)) {
            return false;
        }
        shift(RIGHT, index);
        this.elements[index] = element;
        ++this.size;
        return true;
    }

    @Override
    public E get(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException();
        }

        return getCastedElementAtIndex(index);
    }

    @Override
    public E set(int index, E element) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        E oldElement = getCastedElementAtIndex(index);
        this.elements[index] = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        E oldElement = getCastedElementAtIndex(index);
        this.elements[index] = null;
        shift(LEFT, index);
        --this.size;
        ensureShrink();
        return oldElement;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        if(element == null){
            return -1;
        }

        for (int i = 0; i < elements.length; i++) {
            if (element.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) {
            return false;
        }

        for (Object o : elements) {
            if (element.equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return getCastedElementAtIndex(index++);
            }
        };
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }

    @SuppressWarnings("unchecked")
    private E getCastedElementAtIndex(int index) {
        return (E) elements[index];
    }

    private void shift(String side, int index) {
        if (size >= elements.length) {
            grow();
        }
        if (LEFT.equalsIgnoreCase(side)) {
            for (int i = index; i < size; i++) {
                this.elements[i] = this.elements[i + 1];
            }
        } else if (RIGHT.equalsIgnoreCase(side)) {
            for (int i = size; i > index; i--) {
                this.elements[i] = this.elements[i - 1];
            }
        } else {
            System.out.println("Enter valid command for side(left or right)!");
        }
    }

    private void grow() {
        this.elements = Arrays.copyOf(elements, elements.length * 2);
    }

    private void ensureShrink() {
        if (elements.length / 3 > size) {
            elements = Arrays.copyOf(elements, size * 2);
        }
    }

}
