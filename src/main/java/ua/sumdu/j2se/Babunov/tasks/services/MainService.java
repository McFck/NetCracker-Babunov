package ua.sumdu.j2se.Babunov.tasks.services;

import dnl.utils.text.table.TextTable;
import ua.sumdu.j2se.Babunov.tasks.AbstractTaskList;
import ua.sumdu.j2se.Babunov.tasks.ArrayTaskList;
import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.TaskIO;
import ua.sumdu.j2se.Babunov.tasks.controller.notifications.SuccessfulLoading;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Timer;

public class MainService {
    private final ArrayTaskList taskList;
    private final String fileName = "session.bin";
    private final Timer timer;
    private final NotificationManager notificationManager;

    public MainService() {
        this.taskList = new ArrayTaskList();
        this.timer = new Timer();
        this.notificationManager = new NotificationManager(this);
    }

    public void addTask(Task task) {
        this.taskList.add(task);
        this.notificationManager.refreshNotifications(this.getNotificationsData());
    }

    public AbstractTaskList getNotificationsData() {
        var today = LocalDateTime.now();
        return this.taskList.incoming(today, today.plus(1, ChronoUnit.DAYS));
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public boolean tryLoadSession() {
        var file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            TaskIO.readBinary(this.taskList, file);
            new SuccessfulLoading().notifyEvent();
            this.notificationManager.refreshNotifications(this.getNotificationsData());
            return true;
        }
        return false;
    }

    public void saveSession() {
        var file = new File(fileName);
        TaskIO.writeBinary(this.taskList, file);
        this.notificationManager.killScheduler();
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

    public TextTable getTable(ArrayTaskList list, boolean isNumbered) {
        int additional = isNumbered ? 1 : 0;
        Object[][] params = new Object[list.size()][6 + additional];
        Map<Integer, Object> map;
        for (int i = 0; i < list.size(); i++) {
            map = list.getTask(i).getParametersMap();
            params[i][0] = i;
            for (int j = additional; j < 6 + additional; j++) {
                params[i][j] = map.get(j);
            }
        }
        return new TextTable(Task.getFieldsMap(false).values().toArray(new String[0]), params);
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
        this.notificationManager.refreshNotifications(this.getNotificationsData());
    }
}
