package ua.sumdu.j2se.Babunov.tasks;

import ua.sumdu.j2se.Babunov.tasks.controller.menues.CRUDMenu;
import ua.sumdu.j2se.Babunov.tasks.services.MainService;

public class Main {
    public static void main(String[] args) {
        new CRUDMenu(new MainService()).Excecute();
    }
}
