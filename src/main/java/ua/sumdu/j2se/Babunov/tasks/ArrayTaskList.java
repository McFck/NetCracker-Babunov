package ua.sumdu.j2se.Babunov.tasks;

import java.util.Arrays;

public class ArrayTaskList {

    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Task[] data;

    public ArrayTaskList() {
        this.data = new Task[INITIAL_CAPACITY];
    }

    private void ensureCapacity() {
        int newIncreasedCapacity = this.data.length * 2;
        this.data = Arrays.copyOf(this.data, newIncreasedCapacity);
    }

    public void add(Task task) {
        if(task == null){
            throw new ArrayStoreException("No empty links allowed in TaskArrayList!");
        }
        if (this.size == this.data.length) {
            ensureCapacity();
        }
        this.data[this.size++] = task;
    }

    public boolean remove(Task task) {
        int index = -1;
        for (int i = 0; i < this.size; i++) {
            if (this.data[i] == task) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }

        System.arraycopy(this.data, index + 1, this.data, index, this.size - 1 - index);
        this.size--;

        return true;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index: %s, Size: %s", index, this.size));
        }
        return this.data[index];
    }

    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList subset = new ArrayTaskList();
        int closestActivationTime;
        for (int i = 0; i < size; i++) {
            closestActivationTime = this.data[i].nextTimeAfter(from);
            if (closestActivationTime >= from && closestActivationTime <= to) {
                subset.add(this.data[i]);
            }
        }
        return subset;
    }

}
