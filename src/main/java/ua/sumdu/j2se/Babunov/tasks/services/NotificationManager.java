package ua.sumdu.j2se.Babunov.tasks.services;

import ua.sumdu.j2se.Babunov.tasks.AbstractTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.controller.notifications.TaskNotification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationManager {

    private final MainService service;
    private Timer timer;
    TimerTask hourlyTask = new TimerTask() {

        @Override
        public void run() {
            refreshNotifications(service.getNotificationsData());
        }
    };

    public NotificationManager(MainService service) {
        this.service = service;
        this.timer = new Timer();
        this.timer.schedule(hourlyTask, 0L, 1000 * 60 * 60);
    }

    public void refreshNotifications(AbstractTaskList incomingTasks) {
        var today = LocalDateTime.now();
        Date out;
        this.timer.cancel();
        this.timer.purge();
        this.timer = new Timer();
        Task task;
        for (Object o : incomingTasks) {
            task = (Task) o;
            var nextTime = task.nextTimeAfter(today);
            if (nextTime != null) {
                out = Date.from(nextTime.atZone(ZoneId.systemDefault()).toInstant());
                this.timer.schedule(new TaskNotification(task), out);
            }
        }
    }

    public void killScheduler() {
        this.timer.cancel();
    }
}
