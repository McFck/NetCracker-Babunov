package ua.sumdu.j2se.Babunov.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean isActive;

    public Task(String title, int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time can't be below 0!");
        }
        this.title = title;
        this.time = time;
    }

    public Task(String title, int start, int end, int interval) {
        if (end < 0 || start < 0 || interval <= 0) {
            throw new IllegalArgumentException("Time stamp can't be below 0 and interval must be more than 0!");
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public int getTime() {
        return this.isRepeated() ? this.start : this.time;
    }

    public void setTime(int time) {
        if (this.isRepeated()) {
            this.interval = 0;
        }
        if (time < 0) {
            throw new IllegalArgumentException("Time can't be below 0!");
        }
        this.time = time;
    }

    public int getStartTime() {
        return this.start;
    }

    public int getEndTime() {
        return this.end;
    }

    public int getRepeatInterval() {
        return this.interval;
    }

    public void setTime(int start, int end, int interval) {
        if (end < 0 || start < 0 || interval <= 0) {
            throw new IllegalArgumentException("Time stamp can't be below 0 and interval must be more than 0!");
        }
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    private boolean isRepeated() {
        return this.interval != 0;
    }

    public int nextTimeAfter(int current) {
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
