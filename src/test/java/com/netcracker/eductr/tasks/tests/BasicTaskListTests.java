package com.netcracker.eductr.tasks.tests;

import ua.sumdu.j2se.Babunov.tasks.AbstractTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BasicTaskListTests<E> {
    private AbstractTaskList<E> workList;

    public BasicTaskListTests(AbstractTaskList<E> workList) {
        this.workList = workList;
    }

    public void runAllTests() {
        this.simpleRemoveFromList();
        this.simpleAddToList();
        this.complexAddToList();
        this.incomingTest();
        this.iteratorTest();
        this.multipleIteratorTest();
        this.equalsTest();
        this.toStringTest();
    }

    public void simpleRemoveFromList() {
        this.workList.removeAll();
        Task task = new Task("Test", 0);
        Task task1 = new Task("Test1", 0);
        this.workList.add(task);
        this.workList.add(task1);
        this.workList.add(task);
        assertEquals(3, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        this.workList.remove(task);
        assertEquals(2, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        assertEquals(task1, this.workList.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task1.getTitle(), this.workList.getTask(0).getTitle()));
    }

    public void simpleAddToList() {
        this.workList.removeAll();
        Task task = new Task("Test", 0);
        this.workList.add(task);
        assertEquals(1, this.workList.size(), String.format("Wrong size! Expected: %s, Actual: %s", 1, this.workList.size()));
        assertEquals(task, this.workList.getTask(0), String.format("Wrong task at index! Expected: %s, Actual: %s", task.getTitle(), this.workList.getTask(0).getTitle()));
    }


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

    public void iteratorTest() {
        this.workList.removeAll();
        Task test = new Task("test", 0);
        Task test1 = new Task("test1", 0);
        Task test2 = new Task("test2", 0);
        this.workList.add(test);
        this.workList.add(test1);
        this.workList.add(test2);
        int counter = 0;
        AbstractTaskList<E> subList = this.workList.getSublist();
        for (Task t : this.workList) {
            counter++;
            subList.add(t);
        }
        assertEquals(3, counter, "Wrong iteration counter on 1 iterator construction!");
        assertEquals(test, subList.getTask(0), "Wrong iteration counter on 1 iterator construction!");
        assertEquals(test1, subList.getTask(1), "Wrong iteration counter on 1 iterator construction!");
        assertEquals(test2, subList.getTask(2), "Wrong iteration counter on 1 iterator construction!");
    }

    public void multipleIteratorTest() {
        this.workList.removeAll();
        Task test = new Task("test", 0);
        Task test1 = new Task("test1", 0);
        Task test2 = new Task("test2", 0);
        this.workList.add(test);
        this.workList.add(test1);
        this.workList.add(test2);
        int counter = 0;
        int secondCounter = 0;
        AbstractTaskList<E> subList = this.workList.getSublist();
        for (Task t : this.workList) {
            counter++;
            for (Task f : this.workList) {
                secondCounter++;
            }
            subList.add(t);
        }
        assertEquals(3, counter, "Wrong iteration counter on 1 iterator construction!");
        assertEquals(9, secondCounter, "Wrong iteration counter on 2 iterator construction!");
        assertEquals(test, subList.getTask(0), "Wrong iteration counter on 1 iterator construction!");
        assertEquals(test1, subList.getTask(1), "Wrong iteration counter on 1 iterator construction!");
        assertEquals(test2, subList.getTask(2), "Wrong iteration counter on 1 iterator construction!");
    }

    public void equalsTest() {
        this.workList.removeAll();
        Task test = new Task("test", 0);
        Task test1 = new Task("test1", 0);
        Task test2 = new Task("test2", 0);
        this.workList.add(test);
        this.workList.add(test1);
        this.workList.add(test2);

        AbstractTaskList<E> newList = this.workList.getSublist();
        newList.add(test);
        newList.add(test1);
        assertNotEquals(this.workList, newList, "Lists were equals when they weren't supposed to!");
        newList.add(test2);
        assertEquals(this.workList, newList, "Lists weren't equals when they were supposed to!");

        Task uniqueTest = new Task("test", 0);
        uniqueTest.setActive(true);
        this.workList.add(test);
        assertNotEquals(this.workList, newList, "Lists were equals when they weren't supposed to!");
    }

    public void toStringTest() {
        this.workList.removeAll();
        Task test = new Task("test", 0);
        Task test1 = new Task("test1", 0);
        this.workList.add(test);
        this.workList.add(test1);
        String[] parts = this.workList.toString().split("\n");

        assertEquals(parts[0], this.workList.getClass().getSimpleName() + "{", "Not equals class titles(ToString)!");
        for (int i = 0; i < this.workList.size(); i++) {
            if (i + 1 < this.workList.size()) {
                assertEquals(parts[i + 1], "\t" + this.workList.getTask(i) + ",", "Not elements with comma(ToString)!");
            } else {
                assertEquals(parts[i + 1], "\t" + this.workList.getTask(i) + "", "Not equals last elements(ToString)!");
            }
        }
        assertEquals(parts[parts.length - 1], "}", "Not equals closing brackets(ToString)!");
    }

}
