package ua.sumdu.j2se.Babunov.tasks;

public abstract class AbstractTaskList<E> implements Iterable<Task> {
    private int size = 0;

    protected void updateSize(int value) {
        this.size += value;
    }

    public int size() {
        return size;
    }

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract void removeAll();

    //public native Object clone() throws CloneNotSupportedException;

    //public abstract void removeAt(int index);

    public abstract Task getTask(int index);

    public abstract AbstractTaskList<E> getSublist();

    public E incoming(int from, int to) {
        AbstractTaskList<E> subset = getSublist();
        int closestActivationTime;

        for (int i = 0; i < this.size(); i++) {
            closestActivationTime = this.getTask(i).nextTimeAfter(from);
            if (closestActivationTime >= from && closestActivationTime <= to) {
                subset.add(this.getTask(i));
            }
        }
        return (E) subset;
    }
}
