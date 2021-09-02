package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.LinkedTaskList;
import ua.sumdu.j2se.Babunov.tasks.ListTypes;
import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.TaskListFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedTaskListTest {
    @Test
    public void runBasicTests() {
        BasicTaskListTests<LinkedTaskList> testClass = new BasicTaskListTests<LinkedTaskList>(TaskListFactory.createTaskList(ListTypes.types.LINKED));
        testClass.simpleAddToList();
        testClass.complexAddToList();
        testClass.incomingTest();
    }
}
