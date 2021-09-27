package ua.sumdu.j2se.Babunov.tasks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean isActive;

    private Task() {

    }

    public Task(String title, LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time can't be null");
        }
        this.title = title;
        this.time = time;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        if (end == null || start == null || interval <= 0) {
            throw new IllegalArgumentException("Time stamp can't be null and interval must be more than 0!");
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

    public LocalDateTime getTime() {
        return this.isRepeated() ? this.start : this.time;
    }

    public void setTime(LocalDateTime time) {
        if (this.isRepeated()) {
            this.interval = 0;
        }
        if (time == null) {
            throw new IllegalArgumentException("Time can't be below null!");
        }
        this.time = time;
    }

    public LocalDateTime getStartTime() {
        return this.start;
    }

    public LocalDateTime getEndTime() {
        return this.end;
    }

    @JsonIgnore
    public static Map<Integer, String> getFieldsMap() {
        var result = new HashMap<Integer, String>();

        result.put(0, "Title");
        result.put(1, "isActive");
        result.put(2, "Time");
        result.put(3, "Start time");
        result.put(4, "End time");
        result.put(5, "Interval");

        return result;
    }

    @JsonIgnore
    public Map<Integer, Object> getParametersMap() {
        var result = new HashMap<Integer, Object>();
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        result.put(0, this.title);
        result.put(1, this.isActive ? "Yes" : "No");
        result.put(2, this.time == null ? "Undefined" : this.time.format(formatter));
        result.put(3, this.start == null ? "Undefined" : this.getStartTime().format(formatter));
        result.put(4, this.end == null ? "Undefined" : this.getEndTime().format(formatter));
        result.put(5, this.interval == 0 ? "Not repeating" : this.interval + " hours");

        return result;
    }

    @JsonIgnore
    public int getRepeatInterval() {
        return this.interval;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (end == null || start == null || interval <= 0) {
            throw new IllegalArgumentException("Time stamp can't be null and interval must be more than 0!");
        }
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    private boolean isRepeated() {
        return this.interval != 0;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null) {
            throw new IllegalArgumentException("Time stamp can't be null!");
        }

        if (this.isRepeated()) {
            if (this.end != null && current.isAfter(this.end)) {
                return null;
            }

            if (this.start == null) {
                return null;
            }

            if (current.compareTo(this.start) <= 0) {
                return this.start;
            }

            LocalDateTime next = this.start;

            do {
                next = next.plusHours(this.interval);
            } while (current.isAfter(next));

            return next;

        }
        return current.isAfter(this.time) ? null : this.time; //!this.time.isAfter(current)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Task task = (Task) o;

        return Objects.equals(this.time, task.time) && Objects.equals(this.start, task.start) &&
                Objects.equals(this.end, task.end) && Objects.equals(this.interval, task.interval) &&
                this.isActive == task.isActive && this.title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.time, this.start, this.end, this.interval, this.isActive);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        builder.append(this.title);
        builder.append(" : ");
        builder.append("(");
        if (this.isRepeated()) {

            builder.append(this.start.format(formatter))
                    .append(" | ")
                    .append(this.end.format(formatter))
                    .append(" | Every ")
                    .append(this.interval)
                    .append(" hours");

        } else {
            builder.append(this.time.format(formatter));
        }
        builder.append(")");
        if (this.isActive) {
            builder.append(" (+)");
        } else {
            builder.append(" (-)");
        }
        return builder.toString();

//        return "Task{" +
//                "title='" + title + '\'' +
//                ", time=" + time +
//                ", start=" + start +
//                ", end=" + end +
//                ", interval=" + interval +
//                ", isActive=" + isActive +
//                '}';
        // Title : (Start | End | interval) / (Activation)
    }

    @Override
    public Task clone() {
        try {
            Task clone = (Task) super.clone();
            clone.title = this.title;
            clone.time = this.time;
            clone.start = this.start;
            clone.end = this.end;
            clone.interval = this.interval;
            clone.isActive = this.isActive;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
