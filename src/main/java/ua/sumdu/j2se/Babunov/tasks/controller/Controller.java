package ua.sumdu.j2se.Babunov.tasks.controller;

import ua.sumdu.j2se.Babunov.tasks.controller.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public abstract class Controller implements Command {

    public void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void ShowMenu() {
        for (var choice : GetChoices()) {
            System.out.println(choice);
        }
    }

    protected abstract List<Option> GetChoices();

    protected abstract void ExitRoutine();

    public void Excecute() {
        var reader = new BufferedReader(
                new InputStreamReader(System.in));
        String input = null;
        boolean isFound = false;
        do {
            ClearScreen();
            ShowMenu();
            System.out.println("E | Exit");
            try {
                input = reader.readLine();
            } catch (IOException e) {
                input = "";
            }
            if (input.equals("E")) {
                this.ExitRoutine();
                break;
            }
            isFound = false;
            for (var choice : GetChoices()) {
                if (input.equals(choice.id)) {
                    choice.ExecuteOption();
                    isFound = true;
                }
            }
            if (!isFound) {
                System.out.println("Wrong input. Try again...");
            }

        } while (true);

    }
}
