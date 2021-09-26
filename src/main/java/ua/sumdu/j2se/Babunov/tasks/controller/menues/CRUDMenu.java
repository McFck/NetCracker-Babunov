package ua.sumdu.j2se.Babunov.tasks.controller.menues;

import ua.sumdu.j2se.Babunov.tasks.controller.Controller;
import ua.sumdu.j2se.Babunov.tasks.controller.Option;
import ua.sumdu.j2se.Babunov.tasks.controller.commands.*;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

import java.util.ArrayList;

public class CRUDMenu extends Controller {
    private final MainService service;

    public CRUDMenu(MainService service) {
        this.service = service;
    }

    @Override
    protected ArrayList<Option> GetChoices() {
        var list = new ArrayList<Option>();
        list.add(new Option("1", "Create task", new CreateCommand(this.service)));
        list.add(new Option("2", "Delete task", new DeleteCommand(this.service)));
        list.add(new Option("3", "Update task", new UpdateCommand(this.service)));
        list.add(new Option("4", "View all", new ViewAllCommand(this.service)));
        list.add(new Option("5", "View schedule", new ViewScheduleCommand(this.service)));
        return list;
    }

    @Override
    protected void ExitRoutine() {
        this.service.saveSession();
    }
}
