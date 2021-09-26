package ua.sumdu.j2se.Babunov.tasks.controller.notifications;

import ua.sumdu.j2se.Babunov.tasks.Task;

import java.util.TimerTask;

public class TaskNotification extends TimerTask implements Notification {

    private final Task task;

    public TaskNotification(Task task) {
        this.task = task;
    }


    @Override
    public void notifyEvent() {
        System.out.println("[Notification] Task is starting -> " + this.task);
    }

    @Override
    public void run() {
        this.notifyEvent();
    }
}
