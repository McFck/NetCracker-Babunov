package ua.sumdu.j2se.Babunov.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean isActive;

    Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

    Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    boolean isActive() {
        return isActive;
    }

    void setActive(boolean active) {
        isActive = active;
    }

    int getTime() {
        if (isRepeated()) {
            return start;
        }
        return time;
    }

    void setTime(int time) {
        if (isRepeated()) {
            interval = 0;
        }
        this.time = time;
    }

    int getStartTime() {
        return start;
    }

    int getEndTime() {
        return end;
    }

    int getRepeatInterval() {
        return interval;
    }

    void setTime(int start, int end, int interval) {
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    boolean isRepeated() {
        return interval != 0;
    }

    int nextTimeAfter(int current) {
        if (!isActive) {
            return -1;
        }

        if (isRepeated()) {
            if (current <= start) {
                return start;
            }

            if (current > end) {
                return -1;
            }

            int next;

            do {
                next = start + interval;
            } while (current > next);

            return next;

        }
        return current > time ? -1 : time;
    }
}
