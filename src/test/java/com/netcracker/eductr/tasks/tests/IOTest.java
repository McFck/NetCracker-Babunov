package com.netcracker.eductr.tasks.tests;

import org.junit.jupiter.api.Test;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.LinkedTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.TaskIO;

import java.io.File;
import java.io.Writer;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOTest {
    @Test
    public void binaryReadAndWriteArray() {
        var list = new ArrayTaskList();
        Task task;

        for (int i = 0; i < 3; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            list.add(task);
            task.setActive(true);
        }
        var file = new File("binaryReadAndWrite_TestFile.bin");
        TaskIO.writeBinary(list, file);
        var result = new ArrayTaskList();
        TaskIO.readBinary(result, file);
        file.delete();
        assertEquals(list, result, "Different arrays from writing and reading the same content!");
    }

    @Test
    public void binaryReadAndWriteList() {
        var list = new LinkedTaskList();
        Task task;

        for (int i = 0; i < 3; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            list.add(task);
            task.setActive(true);
        }
        var file = new File("binaryReadAndWrite_TestFile.bin");
        TaskIO.writeBinary(list, file);
        var result = new LinkedTaskList();
        TaskIO.readBinary(result, file);
        file.delete();
        assertEquals(list, result, "Different lists from writing and reading the same content!");
    }

    @Test
    public void jsonReadAndWriteArray() {
        var list = new ArrayTaskList();
        Task task;

        for (int i = 0; i < 3; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            list.add(task);
            task.setActive(true);
        }
        var file = new File("test.json");
        TaskIO.writeText(list, file);
        var result = new ArrayTaskList();
        TaskIO.readText(result, file);
        assertEquals(list, result, "Array different after write and read!");
        file.delete();
    }

    @Test
    public void jsonReadAndWriteList() {
        var list = new LinkedTaskList();
        Task task;

        for (int i = 0; i < 3; i++) {
            task = new Task("test", LocalDateTime.of(2021, 9, i + 1, 0, 0));
            list.add(task);
            task.setActive(true);
        }
        var file = new File("test.json");
        TaskIO.writeText(list, file);
        var result = new LinkedTaskList();
        TaskIO.readText(result, file);
        assertEquals(list, result, "Linked list different after write and read!");
        file.delete();
    }
}
