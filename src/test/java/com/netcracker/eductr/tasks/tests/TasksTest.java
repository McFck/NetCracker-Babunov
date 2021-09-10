package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.Tasks;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TasksTest {
    @Test
    public void incomingTest() {
        var array = new Task[3];
        Task task;

        for (int i = 0; i < 3; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            array[i] = task;
            task.setActive(true);
        }

        var actual = Tasks.incoming(List.of(array), LocalDateTime.of(2021, 9, 2, 0, 0),
                LocalDateTime.of(2021, 9, 3, 0, 0));

        int counter = 0;
        for (var t : actual) {
            assertEquals(t, array[counter + 1], "Different tasks!");
            counter++;
        }
        assertEquals(2, counter, "Different amount of tasks!");

    }

    @Test
    public void calendarTest() {
        var array = new Task[8];
        Task task;
        int additionalCounter = 0;
        for (int i = 0; i < 5; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            array[i] = task;
            task.setActive(true);
            if (i % 2 == 0) {
                task = new Task("test +", LocalDateTime.of(2021, 9, i + 1, 1, 0));
                array[5 + additionalCounter++] = task;
                task.setActive(true);
            }

        }

        var firstDate = LocalDateTime.of(2021, 9, 2, 0, 0);
        var secondDate = LocalDateTime.of(2021, 9, 3, 0, 0);
        var actual = Tasks.calendar(List.of(array), firstDate, secondDate);

        assertEquals(1, actual.get(firstDate).size(), "Different amount of tasks in first date!");
        assertEquals(2, actual.get(secondDate).size(), "Different amount of tasks in second date!");

        var expectedTask1 = new Task("test", LocalDateTime.of(2021, 9, 2, 0, 0));
        expectedTask1.setActive(true);
        assertTrue(actual.get(firstDate).contains(expectedTask1), "Different tasks in first date!");

        var expectedTask2 = new Task("test", LocalDateTime.of(2021, 9, 3, 0, 0));
        expectedTask2.setActive(true);
        assertTrue(actual.get(secondDate).contains(expectedTask2), "Different tasks in second date!");

        var expectedTask3 = new Task("test +", LocalDateTime.of(2021, 9, 3, 1, 0));
        expectedTask3.setActive(true);
        assertTrue(actual.get(secondDate).contains(expectedTask3), "Different tasks in second date!");

    }
}
