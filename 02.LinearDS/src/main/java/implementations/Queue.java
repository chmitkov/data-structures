package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public Queue() {
    }

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

        public Node<E> setNext(Node<E> next) {
            this.next = next;
            return this;
        }
    }


    @Override
    public void offer(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.size == 0) {
            this.head = this.tail = newNode;
        }
        this.tail.setNext(newNode);
        this.tail = newNode;
        size++;
    }

    @Override
    public E poll() {
        ensureCapacity();
        E element = this.head.getValue();
        if (this.size == 1) {
            this.head = null;
        } else {
            Node<E> next = this.head.getNext();
            this.head.setNext(null);
            this.head = next;
        }
        this.size--;
        return element;
    }

    private void ensureCapacity() {
        if (this.size == 0) {
            throw new IllegalStateException("The Queue is empty!");
        }

    }

    @Override
    public E peek() {
        ensureCapacity();
        return this.head.getValue();
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
                return this.current != null;
            }

            @Override
            public E next() {
                E element = this.current.value;
                this.current = this.current.next;
                return element;
            }
        };
    }
}
