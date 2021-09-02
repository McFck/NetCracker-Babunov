package com.netcracker.eductr.tasks.tests;

import ua.sumdu.j2se.Babunov.tasks.AbstractTaskList;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.LinkedTaskList;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        return switch (type) {
            case ARRAY -> new ArrayTaskList();
            case LINKED -> new LinkedTaskList();
        };
    }
}
