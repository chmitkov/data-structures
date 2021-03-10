package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {

    private Node<E> head;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        public Node() {
        }

        public Node(E element) {
            this.value = element;
        }

        public E getValue() {
            return value;
        }

        public Node<E> setValue(E value) {
            this.value = value;
            return this;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
        } else {
            newNode.setNext(this.head);
            this.head = newNode;
        }
        ++this.size;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
        }else {
            Node<E> tail = findTail();
            tail.setNext(newNode);
        }

        ++this.size;
    }

    private Node<E> findTail() {
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    @Override
    public E removeFirst() {
        ensureIsNotEmpty();
        E currentFirstValue = this.head.getValue();
        this.head = this.head.getNext();
        --this.size;

        return currentFirstValue;
    }

    @Override
    public E removeLast() {
        ensureIsNotEmpty();
        Node<E> current = this.head;
        Node<E> prev = null;
        E value;
        while (current.getNext() != null) {
            prev = current;
            current = current.getNext();
        }
        if (prev == null) {
            value = removeFirst();
        } else {
            value = prev.next.getValue();
            prev.next = null;
        }

        return value;
    }

    @Override
    public E getFirst() {
        ensureIsNotEmpty();
        return this.head.getValue();
    }

    @Override
    public E getLast() {
        return findTail().getValue();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                E value = current.getValue();
                current = current.getNext();
                return value;
            }
        };
    }

    private void ensureIsNotEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("The LinkedList is empty!");
        }
    }
}
