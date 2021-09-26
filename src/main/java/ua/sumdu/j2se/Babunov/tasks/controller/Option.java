package ua.sumdu.j2se.Babunov.tasks.controller;

import ua.sumdu.j2se.Babunov.tasks.controller.commands.Command;

public class Option {
    public String id;
    public String description;
    public Command command;

    public Option(String id, String description, Command command) {
        this.id = id;
        this.description = description;
        this.command = command;
    }

    public void ExecuteOption() {
        command.Excecute();
    }

    @Override
    public String toString() {
        return this.id + ". " + this.description;
    }
}
