package ua.sumdu.j2se.Babunov.tasks;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        return switch (type) {
            case ARRAY -> new ArrayTaskList();
            case LINKED -> new LinkedTaskList();
        };
    }
}
