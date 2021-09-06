package ua.sumdu.j2se.Babunov.tasks;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

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

    public abstract Stream<Task> getStream();

    //public native Object clone() throws CloneNotSupportedException;

    //public abstract void removeAt(int index);

    public abstract Task getTask(int index);

    public abstract AbstractTaskList<E> getSublist();

    public final E incoming(int from, int to) {
        AbstractTaskList<E> subset = getSublist();
        Stream<Task> stream = this.getStream();
        Task[] t = (Task[]) stream.filter(x -> x.nextTimeAfter(from) >= from && x.nextTimeAfter(from) <= to).toArray();
//        int closestActivationTime;
//
//        for (int i = 0; i < this.size(); i++) {
//            closestActivationTime = this.getTask(i).nextTimeAfter(from);
//            if (closestActivationTime >= from && closestActivationTime <= to) {
//                subset.add(this.getTask(i));
//            }
//        }
        return (E) subset;
    }
}
