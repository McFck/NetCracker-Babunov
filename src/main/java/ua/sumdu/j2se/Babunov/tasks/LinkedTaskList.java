package ua.sumdu.j2se.Babunov.tasks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList<LinkedTaskList> implements Cloneable {
    private Node head;

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new ArrayStoreException("No empty links allowed in LinkedTaskList!");
        }

        Node newNode = new Node(task);
        newNode.next = null;
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node last = this.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }

        super.updateSize(1);
    }

    public boolean remove(Task task) {
        if (this.head.data == task) {
            this.head = this.head.next;
            super.updateSize(-1);
            return true;
        }
        Node current = this.head;
        while (current.next != null) {
            if (current.next.data == task) {
                current.next = current.next.next;
                super.updateSize(-1);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= super.size()) {
            throw new IndexOutOfBoundsException(String.format("Index: %s, Size: %s", index, super.size()));
        }
        Node current = this.head;
        int count = 0;
        while (current != null) {
            if (count == index)
                return current.data;
            count++;
            current = current.next;
        }

        return null;
    }

    @Override
    public AbstractTaskList<LinkedTaskList> getSublist() {
        return new LinkedTaskList();
    }

    @Override
    public void removeAll() {
        this.head = null;
        super.updateSize((-1) * super.size());
    }

    @Override
    public Stream<Task> getStream() {
        Task[] data = new Task[this.size()];
        int i = 0;
        Node current = this.head;
        while (current != null) {
            data[i++] = current.data;
            current = current.next;
        }
        return Stream.of(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        LinkedTaskList otherList = (LinkedTaskList) o;
        if (this.size() != otherList.size()) {
            return false;
        }

        Node thisHead = this.head;
        Node otherHead = otherList.head;

        while (thisHead != null) {
            if (!thisHead.data.equals(otherHead.data)) {
                return false;
            }
            thisHead = thisHead.next;
            otherHead = otherHead.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    public String toString() {
        Node current = this.head;
        StringBuilder builder = new StringBuilder();
        builder.append("LinkedTaskList{\n");
        while (current != null) {
            builder.append("\t");
            builder.append(current.data);
            if (current.next != null) {
                builder.append(",");
                builder.append("\n");
            }
            current = current.next;
        }
        builder.append("\n}");
        return builder.toString();
    }

    @Override
    public Iterator<Task> iterator() {
        return new Itr(this.head);
    }

    @Override
    public LinkedTaskList clone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            if (this.size() == 0) return clone;
            clone.updateSize((-1) * this.size());
            clone.head = null;
            Node current = this.head;
            while (current != null) {
                clone.add(current.data);
                current = current.next;
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    static class Node {

        Task data;
        Node next;
        private Node(){

        }

        Node(Task data) {
            this.data = data;
            next = null;
        }
    }

    private class Itr implements Iterator<Task> {
        Node current;

        public Itr(Node firstInList) {
            this.current = firstInList;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Task next() {
            Node savedCurrent = current;
            current = current.next;
            return savedCurrent.data;
        }
    }
}
