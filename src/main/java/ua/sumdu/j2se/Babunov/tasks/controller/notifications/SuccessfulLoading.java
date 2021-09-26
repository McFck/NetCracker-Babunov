package ua.sumdu.j2se.Babunov.tasks.controller.notifications;

public class SuccessfulLoading implements Notification {
    @Override
    public void notifyEvent() {
        System.out.println("Session successfully loaded!");
    }
}
