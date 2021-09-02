package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.AbstractTaskList;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTaskListTests<E> {
    private AbstractTaskList<E> workList;

    public BasicTaskListTests(AbstractTaskList<E> workList) {
        this.workList = workList;
    }

    @Test
    public void simpleAddToList() {
        this.workList.removeAll();
        Task task = new Task("Test", 0);
        this.workList.add(task);
        assertEquals(1, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        assertEquals(task, this.workList.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task.getTitle(), this.workList.getTask(0).getTitle()));
    }

    @Test
    public void complexAddToList() {
        this.workList.removeAll();
        Task task1 = new Task("Test", 0);
        Task task2 = new Task("Test1", 1);
        Task task3 = new Task("Test2", 2);
        this.workList.add(task1);
        this.workList.add(task2);
        assertEquals(2, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        this.workList.remove(task1);
        assertEquals(1, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        this.workList.add(task3);
        assertEquals(task3, this.workList.getTask(1), String.format("Wrong task at index! Expected: %s, Actual: %s", task3.getTitle(), this.workList.getTask(1).getTitle()));
    }

    @Test
    public void incomingTest() {
        this.workList.removeAll();
        Task task1 = new Task("Test0", 0);
        Task task2 = new Task("Test1", 1);
        Task task3 = new Task("Test2", 7);
        Task task4 = new Task("Test3", 2);

        task1.setActive(true);
        task2.setActive(true);
        task3.setActive(true);
        task4.setActive(true);

        this.workList.add(task1);
        this.workList.add(task2);
        this.workList.add(task3);
        this.workList.add(task4);

        assertEquals(4, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));

        AbstractTaskList<E> subList = (AbstractTaskList<E>) this.workList.incoming(1, 2);
        assertEquals(2, subList.size(), String.format("Wrong sublist size! Expected: %s, Actual: %s", 1, this.workList.size()));

        assertEquals(task2, subList.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task2.getTitle(), this.workList.getTask(0).getTitle()));
        assertEquals(task4, subList.getTask(1), String.format("Wrong task at index! Expected: %s, Actual: %s", task4.getTitle(), this.workList.getTask(1).getTitle()));
    }
}
