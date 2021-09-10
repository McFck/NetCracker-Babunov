package ua.sumdu.j2se.Babunov.tasks;

import java.time.LocalDateTime;
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

    public abstract Task getTask(int index);

    public abstract AbstractTaskList<E> getSublist();

    public final E incoming(LocalDateTime from, LocalDateTime to) {
        AbstractTaskList<E> subset = getSublist();
        Stream<Task> stream = this.getStream();
        stream.filter(Objects::nonNull)
                .filter(x -> !x.nextTimeAfter(from).isBefore(from) && !x.nextTimeAfter(from).isAfter(to))
                .forEach(subset::add);
        return (E) subset;
    }
}
