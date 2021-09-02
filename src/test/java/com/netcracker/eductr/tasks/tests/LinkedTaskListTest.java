package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.LinkedTaskList;

public class LinkedTaskListTest {
    @Test
    public void runBasicTests() {
        BasicTaskListTests<LinkedTaskList> testClass = new BasicTaskListTests<LinkedTaskList>(TaskListFactory.createTaskList(ListTypes.types.LINKED));
        testClass.runAllTests();
    }
}
