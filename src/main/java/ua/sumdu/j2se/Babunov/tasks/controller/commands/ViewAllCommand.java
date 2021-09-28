package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ViewAllCommand implements Command {
    private final MainService service;

    public ViewAllCommand(MainService service) {
        this.service = service;
    }

    @Override
    public void Execute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        System.out.println("All tasks:");
        var list = this.service.getTaskList();
        if (list.size() == 0) {
            System.out.println("No tasks available!");
            return;
        }
        this.service.getTable(list, false).printTable();
    }
}
