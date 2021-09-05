package ua.sumdu.j2se.Babunov.tasks;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends AbstractTaskList<ArrayTaskList> implements Cloneable {

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
    public void removeAll() {
        Arrays.fill(data, null);
        super.updateSize((-1) * super.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ArrayTaskList tasks = (ArrayTaskList) o;
        for (int i = 0; i < this.size(); i++) {
            if (!this.data[i].equals(tasks.data[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ArrayTaskList{\n");
        for (int i = 0; i < super.size(); i++) {
            builder.append("\t");
            builder.append(this.data[i]);
            if (i + 1 < super.size()) {
                builder.append(",");
                builder.append("\n");
            }
        }
        builder.append("\n}");
        return builder.toString();
    }

    @Override
    public Iterator<Task> iterator() {
        return new Itr();
    }

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            if (this.size() == 0) return clone;
            clone.updateSize((-1) * this.size());
            clone.data = new Task[this.size()];
            for (int i = 0; i < this.size(); i++) {
                clone.add(this.data[i]);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private class Itr implements Iterator<Task> {
        int cursor;
        int lastRet = -1;

        Itr() {
        }

        public boolean hasNext() {
            return cursor != ArrayTaskList.this.size();
        }

        @SuppressWarnings("unchecked")
        public Task next() {
            int i = cursor;
            if (i >= ArrayTaskList.this.size())
                throw new NoSuchElementException();
            Object[] elementData = ArrayTaskList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (Task) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ArrayTaskList.this.remove(ArrayTaskList.this.getTask(lastRet));
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
