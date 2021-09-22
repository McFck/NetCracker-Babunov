package ua.sumdu.j2se.Babunov.tasks.controller.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserInteraction {
    public static LocalDateTime readDateTime(BufferedReader reader) throws IOException {
        System.out.println("Enter date(day.month.year):");
        var date = reader.readLine();
        System.out.println("Enter time (Hour:Minutes):");
        var time = reader.readLine();
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(date + " " + time, formatter);
    }
}
