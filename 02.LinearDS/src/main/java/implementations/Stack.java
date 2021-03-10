package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> head;
    private int size;

    public Stack() {
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
    public void push(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.head != null) {
            newNode.setNext(this.head);
        }

        this.head = newNode;

        ++this.size;

    }

    @Override
    public E pop() {
        if (this.size == 0) {
            throw new IllegalStateException("The Stack is empty!!!");
        }

        Node<E> oldHead = this.head;
        this.head.next = null;
        this.head = oldHead.getNext();

        --size;

        return oldHead.getValue();
    }

    @Override
    public E peek() {
        if (this.size == 0) {
            throw new IllegalStateException("The Stack is empty!!!");
        }

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
                return this.current.getNext() != null;
            }

            @Override
            public E next() {
                E value = this.current.getValue();
                this.current = this.current.next;
                return value;
            }
        };
    }
}
