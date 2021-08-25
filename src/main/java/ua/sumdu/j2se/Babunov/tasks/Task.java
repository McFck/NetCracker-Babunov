package ua.sumdu.j2se.Babunov.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean isActive;

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    String getTitle() {
        return this.title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    boolean isActive() {
        return this.isActive;
    }

    void setActive(boolean active) {
        this.isActive = active;
    }

    int getTime() {
        return this.isRepeated() ? this.start : this.time;
    }

    void setTime(int time) {
        if (this.isRepeated()) {
            this.interval = 0;
        }
        this.time = time;
    }

    int getStartTime() {
        return this.start;
    }

    int getEndTime() {
        return this.end;
    }

    int getRepeatInterval() {
        return this.interval;
    }

    void setTime(int start, int end, int interval) {
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    private boolean isRepeated() {
        return this.interval != 0;
    }

    int nextTimeAfter(int current) {
        if (!this.isActive) {
            return -1;
        }

        if (this.isRepeated()) {
            if (current <= this.start) {
                return this.start;
            }

            if (current > this.end) {
                return -1;
            }

            int next;

            do {
                next = this.start + this.interval;
            } while (current > next);

            return next;

        }
        return current > this.time ? -1 : this.time;
    }
}
