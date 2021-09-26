package ua.sumdu.j2se.Babunov.tasks.controller.notifications;

public class InputError implements Notification {
    @Override
    public void notifyEvent() {
        System.out.println("Input error!");
    }
}
