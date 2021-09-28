package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import ua.sumdu.j2se.Babunov.tasks.controller.notifications.InputError;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;

public class ViewScheduleCommand implements Command {
    private final MainService service;

    public ViewScheduleCommand(MainService service) {
        this.service = service;
    }

    @Override
    public void Execute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        try {
            System.out.println("Enter schedule start:");
            var start = UserInteraction.readDateTime(reader);

            System.out.println("Enter schedule end:");
            var end = UserInteraction.readDateTime(reader);

            if (!this.service.verifyDates(start, end)) {
                System.out.println("Wrong dates order!!!");
                return;
            }
            var list = this.service.getIncomingList(start, end);
            if (list.size() == 0) {
                System.out.println("No tasks scheduled for this period of time");
                return;
            }

            this.service.getTable(list, false).printTable();
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            new InputError().notifyEvent();
        }
    }
}
