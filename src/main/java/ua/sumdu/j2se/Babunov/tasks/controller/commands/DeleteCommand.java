package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.Task;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteCommand implements Command {
    private MainService service;

    public DeleteCommand(MainService service) {
        this.service = service;
    }

    @Override
    public void Excecute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        System.out.println("Enter task title you would like to delete:");
        Task task = null;
        try {
            String title;
            title = reader.readLine();
            var found = this.service.findTask(title);
            if (found.size() == 0) {
                System.out.println("No tasks found");
            } else {
                System.out.println("Choose tasks to delete (ex.:1,2,3):");
                for (int i = 0; i < found.size(); i++) {
                    System.out.println("=" + (i + 1) + "=");
                    System.out.println(found.getTask(i));
                }
                System.out.println();
                var choice = reader.readLine();
                var tokens = choice.split(",");
                for (var token : tokens) {
                    this.service.removeTask(found.getTask(Integer.parseInt(token) - 1));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
