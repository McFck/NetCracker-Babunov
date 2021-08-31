package ua.sumdu.j2se.Babunov.tasks;

public class LinkedTaskList {
    static class Node {

        Task data;
        Node next;

        Node(Task data) {
            this.data = data;
            next = null;
        }
    }

    private int size = 0;
    private Node head;

    public void add(Task task) {
        if (task == null) {
            throw new ArrayStoreException("No empty links allowed in LinkedTaskList!");
        }

        Node newNode = new Node(task);
        newNode.next = null;
        if (this.head == null) {
            this.head = newNode;
        }
        else {
            Node last = this.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }

        this.size++;
    }

    public boolean remove(Task task) {
        if (this.head.data == task) {
            this.head = this.head.next;
            this.size--;
            return true;
        }
        Node current = this.head;
        while (current.next != null) {
            if (current.next.data == task) {
                current.next = current.next.next;
                this.size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public Task getTask(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index: %s, Size: %s", index, this.size));
        }
        Node current = this.head;
        int count = 0;
        while (current != null)
        {
            if (count == index)
                return current.data;
            count++;
            current = current.next;
        }

        return null;
    }

    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList subset = new LinkedTaskList();
        int closestActivationTime;
        Node current = this.head;

        while (current != null)
        {
            closestActivationTime = current.data.nextTimeAfter(from);
            if (closestActivationTime >= from && closestActivationTime <= to) {
                subset.add(current.data);
            }
            current = current.next;
        }

        return subset;
    }
}
