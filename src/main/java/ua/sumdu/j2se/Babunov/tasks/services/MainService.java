package ua.sumdu.j2se.Babunov.tasks.services;

import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;

import java.time.LocalDateTime;

public class MainService {
    private ArrayTaskList taskList;

    public MainService() {
        this.taskList = new ArrayTaskList();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public ArrayTaskList findTask(String title) {
        var result = new ArrayTaskList();
        for (var t : this.taskList) {
            if (t.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayTaskList getTaskList() {
        return this.taskList;
    }

    public ArrayTaskList getIncomingList(LocalDateTime from, LocalDateTime to) {
        return this.taskList.incoming(from, to);
    }

    public boolean verifyDates(LocalDateTime from, LocalDateTime to) {
        return !from.isAfter(to);
    }

    public void removeTask(Task t) {
        this.taskList.remove(t);
    }
}
