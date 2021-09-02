package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.ListTypes;
import ua.sumdu.j2se.Babunov.tasks.TaskListFactory;

public class ArrayTaskListTest {
    @Test
    public void runBasicTests() {
        BasicTaskListTests<ArrayTaskList> testClass = new BasicTaskListTests<ArrayTaskList>(TaskListFactory.createTaskList(ListTypes.types.ARRAY));
        testClass.simpleAddToList();
        testClass.complexAddToList();
        testClass.incomingTest();
    }
}
