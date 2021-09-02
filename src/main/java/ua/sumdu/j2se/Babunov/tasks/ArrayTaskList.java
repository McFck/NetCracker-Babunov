package ua.sumdu.j2se.Babunov.tasks;

import java.util.Arrays;

public class ArrayTaskList extends AbstractTaskList<ArrayTaskList> {

    private static final int INITIAL_CAPACITY = 10;
    private Task[] data;

    public ArrayTaskList() {
        this.data = new Task[INITIAL_CAPACITY];
    }

    private void ensureCapacity() {
        int newIncreasedCapacity = this.data.length * 2;
        this.data = Arrays.copyOf(this.data, newIncreasedCapacity);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new ArrayStoreException("No empty links allowed in TaskArrayList!");
        }
        if (super.size() == this.data.length) {
            ensureCapacity();
        }

        this.data[super.size()] = task;
        super.updateSize(1);
    }

    public boolean remove(Task task) {
        int index = -1;
        for (int i = 0; i < super.size(); i++) {
            if (this.data[i] == task) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }

        System.arraycopy(this.data, index + 1, this.data, index, super.size() - 1 - index);
        super.updateSize(-1);

        return true;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= super.size()) {
            throw new IndexOutOfBoundsException(String.format("Index: %s, Size: %s", index, super.size()));
        }
        return this.data[index];
    }

    @Override
    public AbstractTaskList<ArrayTaskList> getSublist() {
        return new ArrayTaskList();
    }

    @Override
    public void removeAll(){
        Arrays.fill(data, null);
        super.updateSize((-1)*super.size());
    }

}
