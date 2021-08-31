package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.LinkedTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedTaskListTest {
    @Test
    public void simpleAddToList() {
        LinkedTaskList list = new LinkedTaskList();
        Task task = new Task("Test", 0);
        list.add(task);
        assertEquals(1, list.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, list.size()));
        assertEquals(task, list.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task.getTitle(), list.getTask(0).getTitle()));
    }

    @Test
    public void complexAddToList() {
        LinkedTaskList list = new LinkedTaskList();
        Task task1 = new Task("Test", 0);
        Task task2 = new Task("Test1", 1);
        Task task3 = new Task("Test2", 2);
        list.add(task1);
        list.add(task2);
        assertEquals(2, list.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, list.size()));
        list.remove(task1);
        assertEquals(1, list.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, list.size()));
        list.add(task3);
        assertEquals(task3, list.getTask(1), String.format("Wrong task at index! Expected: %s, Actual: %s", task3.getTitle(), list.getTask(1).getTitle()));
    }

    @Test
    public void incomingTest() {
        LinkedTaskList list = new LinkedTaskList();
        Task task1 = new Task("Test0", 0);
        Task task2 = new Task("Test1", 1);
        Task task3 = new Task("Test2", 7);
        Task task4 = new Task("Test3", 2);

        task1.setActive(true);
        task2.setActive(true);
        task3.setActive(true);
        task4.setActive(true);

        list.add(task1);
        list.add(task2);
        list.add(task3);
        list.add(task4);

        assertEquals(4, list.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, list.size()));

        LinkedTaskList subList = list.incoming(1,2);
        assertEquals(2, subList.size(), String.format("Wrong sublist size! Expected: %s, Actual: %s", 1, list.size()));

        assertEquals(task2, subList.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task2.getTitle(), list.getTask(0).getTitle()));
        assertEquals(task4, subList.getTask(1), String.format("Wrong task at index! Expected: %s, Actual: %s", task4.getTitle(), list.getTask(1).getTitle()));
    }
}
