package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.controller.notifications.InputError;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;

public class UpdateCommand implements Command {
    private final MainService service;

    public UpdateCommand(MainService service) {
        this.service = service;
    }

    private void changeActivity(Task task, int input, boolean newState) {
        if (input == 1) {
            task.setActive(newState);
            System.out.println("Status changed!");
        } else {
            System.out.println("Denied");
        }
    }

    private void setRepeatable(BufferedReader reader, Task task) throws IOException {
        System.out.println("Enter start parameters:");
        var start = UserInteraction.readDateTime(reader);
        System.out.println("Enter end parameters:");
        var end = UserInteraction.readDateTime(reader);
        if (!this.service.verifyDates(start, end)) {
            System.out.println("Wrong dates order!!!");
            return;
        }
        System.out.println("Enter repetition interval:");
        var interval = Integer.parseInt(reader.readLine());
        task.setTime(start, end, interval);
        System.out.println("Done!");
    }


    private void editChosenParameter(Task task, int choice) throws IOException {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        int input;
        switch (choice) {
            case 0 -> {
                System.out.println("Enter new title:");
                task.setTitle(reader.readLine());
            }
            case 1 -> {
                if (task.isActive()) {
                    System.out.println("Enter 1 to deactivate task:");
                    this.changeActivity(task, Integer.parseInt(reader.readLine()), false);
                } else {
                    System.out.println("Enter 1 to activate task:");
                    this.changeActivity(task, Integer.parseInt(reader.readLine()), true);
                }
                this.service.getNotificationManager().refreshNotifications(this.service.getNotificationsData());
                //this.service.refreshNotifications();
            }
            case 2 -> {
                System.out.println("Enter new activation time:");
                task.setTime(UserInteraction.readDateTime(reader));
                this.service.getNotificationManager().refreshNotifications(this.service.getNotificationsData());
                //this.service.refreshNotifications();
            }
            case 3 -> {
                if (task.getRepeatInterval() == 0) {
                    setRepeatable(reader, task);
                } else {
                    System.out.println("Enter new time:");
                    var time = UserInteraction.readDateTime(reader);
                    if (!this.service.verifyDates(time, task.getEndTime())) {
                        System.out.println("Wrong dates order!!!");
                        return;
                    }
                    task.setTime(time, task.getEndTime(), task.getRepeatInterval());
                }
                this.service.getNotificationManager().refreshNotifications(this.service.getNotificationsData());
                //this.service.refreshNotifications();
            }
            case 4 -> {
                if (task.getRepeatInterval() == 0) {
                    setRepeatable(reader, task);
                } else {
                    System.out.println("Enter new time:");
                    var time = UserInteraction.readDateTime(reader);
                    if (!this.service.verifyDates(task.getStartTime(), time)) {
                        System.out.println("Wrong dates order!!!");
                        return;
                    }
                    task.setTime(task.getStartTime(), time, task.getRepeatInterval());

                }
                this.service.getNotificationManager().refreshNotifications(this.service.getNotificationsData());
                //this.service.refreshNotifications();
            }
            case 5 -> {
                if (task.getRepeatInterval() == 0) {
                    setRepeatable(reader, task);
                } else {
                    System.out.println("Enter new interval:");
                    task.setTime(task.getStartTime(), task.getEndTime(), Integer.parseInt(reader.readLine()));
                }
                this.service.getNotificationManager().refreshNotifications(this.service.getNotificationsData());
                //this.service.refreshNotifications();
            }
        }
    }

    private int chooseParameter(Task task) {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        var taskParameters = Task.getFieldsMap();
        for (var parameter : taskParameters.entrySet()) {
            System.out.println(parameter.getKey() + " | " + parameter.getValue());
        }
        System.out.println("E | Exit to main menu");
        int choice = 0;
        try {
            String input = reader.readLine();
            if (input.equals("E")) {
                return -1;
            }
            choice = Integer.parseInt(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (choice < 0 || choice > taskParameters.size()) {
            new InputError().notifyEvent();
            return 0;
        }
        try {
            editChosenParameter(task, choice);
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            new InputError().notifyEvent();
        }
        return 0;
    }

    @Override
    public void Excecute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        System.out.println("Enter task id:");
        var list = this.service.getTaskList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "| " + list.getTask(i));
        }
        System.out.println("Choose task:");
        try {
            int id = Integer.parseInt(reader.readLine());
            if (id >= list.size() || id < 0) {
                System.out.println("Wrong task!");
                return;
            }
            var task = list.getTask(id);
            var editingMode = true;
            while (editingMode) {
                if (this.chooseParameter(task) == -1) {
                    editingMode = false;
                }
            }

        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            new InputError().notifyEvent();
        }
    }
}
