package ua.sumdu.j2se.Babunov.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        var result = new ArrayList<Task>();

        LocalDateTime nextStart;
        LocalDateTime nextEnd;

        for (Task t : tasks) {
            nextStart = t.nextTimeAfter(start);

            if (nextStart == null) {
                continue;
            }

            if (!nextStart.isBefore(start) && !nextStart.isAfter(end)) {
                result.add(t);
            }
        }
        return result;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        var result = new TreeMap<LocalDateTime, Set<Task>>();
        HashSet<Task> currentSet;

        for (LocalDateTime date = start; !date.isAfter(end); date = date.plusDays(1)) {
            currentSet = new HashSet<Task>();

            var endT = date.toLocalDate().atTime(LocalTime.MAX);
            ;
            var incoming = Tasks.incoming(tasks, date, endT);
            for (var t : incoming) {
                currentSet.add(t);
            }
            if (result.containsKey(date)) {
                result.put(date, mergeSets(currentSet, result.get(date)));
            } else {
                result.put(date, currentSet);
            }
        }
        return result;
    }

    private static Set<Task> mergeSets(Set<Task> a, Set<Task> b) {
        return new HashSet<Task>() {
            {
                addAll(a);
                addAll(b);
            }
        };
    }
}
