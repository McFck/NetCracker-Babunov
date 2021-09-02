package ua.sumdu.j2se.Babunov.tasks;

public class LinkedTaskList extends AbstractTaskList<LinkedTaskList> {
    static class Node {

        Task data;
        Node next;

        Node(Task data) {
            this.data = data;
            next = null;
        }
    }

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
    public void removeAll(){
        this.head = null;
        super.updateSize((-1)*super.size());
    }
}
