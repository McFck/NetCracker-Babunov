package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ViewScheduleCommand implements Command {
    private MainService service;

    public ViewScheduleCommand(MainService service) {
        this.service = service;
    }

    @Override
    public void Excecute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        try {
            System.out.println("Enter schedule start:");
            var start = UserInteraction.readDateTime(reader);

            System.out.println("Enter schedule end:");
            var end = UserInteraction.readDateTime(reader);

            var list = this.service.getIncomingList(start, end);
            if (list.size() == 0) {
                System.out.println("No tasks scheduled for this period of time");
                return;
            }
            for (var t : list) {
                System.out.println(t);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
