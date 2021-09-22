package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateCommand implements Command {
    private MainService service;

    public CreateCommand(MainService service) {
        this.service = service;
    }

    @Override
    public void Excecute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        System.out.println("Enter task title:");
        Task task = null;
        boolean isActive = true;
        try {
            String title;
            title = reader.readLine();
            System.out.println("Repeating task? (Y/N):");
            boolean isRepeating = false;
            isRepeating = reader.readLine().equals("Y");
            if (isRepeating) {
                System.out.println("Enter repeat interval:");
                var interval = Integer.parseInt(reader.readLine());
                System.out.println("Enter start execution date parameters...");
                var startDateTime = UserInteraction.readDateTime(reader);
                System.out.println("Enter end execution date parameters...");
                var endDateTime = UserInteraction.readDateTime(reader);
                task = new Task(title, startDateTime, endDateTime, interval);

            } else {
                System.out.println("Enter task execution date parameters...");
                var startDateTime = UserInteraction.readDateTime(reader);
                task = new Task(title, startDateTime);
            }
            System.out.println("Activate task? (Default: No,(Y))");
            isActive = reader.readLine().equals("Y");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        if (isActive) {
            task.setActive(true);
        }
        this.service.addTask(task);
    }
}
