package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ArrayTaskListTest {
    @Test
    public void runBasicTests() {
        BasicTaskListTest<ArrayTaskList> testClass = new BasicTaskListTest<ArrayTaskList>(TaskListFactory.createTaskList(ListTypes.types.ARRAY));
        testClass.runAllTests();
    }

    @Test
    public void cloneTest() {
        ArrayTaskList list = new ArrayTaskList();
        Task test = new Task("test", LocalDateTime.now());
        Task test1 = test.clone();
        test1.setTitle("test1");
        assertNotEquals(test, test1, "Clone changes also translate to original!");

        Task test2 = test1.clone();
        test2.setTitle("test2");
        assertNotEquals(test2, test1, "Clone changes also translate to original!");

        Task test3 = test2.clone();
        test3.setTitle("test3");
        assertNotEquals(test3, test2, "Clone changes also translate to original!");

        list.add(test);
        list.add(test1);
        list.add(test2);
        ArrayTaskList cloneList = list.clone();
        assertEquals(list, cloneList, "Original list and clone list are different!");

        list.remove(test);
        test1.setTitle("NEW TITLE!!!");
        assertNotEquals(test.getTitle(), test1.getTitle(), "Clone changes also translate to original!");

        cloneList.add(test3);
        assertNotEquals(list, cloneList, "Changes to original and cloned lists are synchronised!");
    }
}
