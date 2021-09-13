package ua.sumdu.j2se.Babunov.tasks;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            writeRoutine(tasks, oos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            readRoutine(tasks, ois);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            writeRoutine(tasks, oos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static int convertTimeToInt(LocalDateTime dateTime) {
        return (int) (dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
    }

    private static void writeRoutine(AbstractTaskList tasks, ObjectOutputStream oos) throws IOException {
        Task task;
        oos.writeInt(tasks.size());
        for (var t : tasks) {
            task = (Task) t;
            oos.writeInt(task.getTitle().length());
            oos.writeBytes(task.getTitle());
            oos.writeInt(task.isActive() ? 1 : 0);
            oos.writeInt(task.getRepeatInterval());
            if (task.getRepeatInterval() == 0) {
                oos.writeInt(convertTimeToInt(task.getTime()));
            } else {
                oos.writeInt(convertTimeToInt(task.getStartTime()));
                oos.writeInt(convertTimeToInt(task.getEndTime()));
            }
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            readRoutine(tasks, ois);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void readRoutine(AbstractTaskList tasks, ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Task task;
        var counter = ois.readInt();
        int repetition;
        int nameLength;
        String name;
        byte[] arr;
        LocalDateTime timeF;
        LocalDateTime timeS;
        for (int i = 0; i < counter; i++) {
            nameLength = ois.readInt();
            arr = new byte[nameLength];
            for (int j = 0; j < nameLength; j++) {
                arr[j] = ois.readByte();
            }
            name = new String(arr);
            task = new Task(name, LocalDateTime.MIN);
            task.setActive(ois.readInt() == 1);
            repetition = ois.readInt();

            timeF = Instant.ofEpochMilli(((long) ois.readInt()) * 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (repetition == 0) {
                task.setTime(timeF);
            } else {
                timeS = Instant.ofEpochMilli(((long) ois.readInt()) * 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime();
                task.setTime(timeF, timeS, repetition);
            }
            tasks.add(task);
        }
    }

    private static void jsonDumpRoutine(AbstractTaskList tasks, Object out) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            var writer = mapper.writerWithDefaultPrettyPrinter();
            if (out instanceof Writer) {
                writer.writeValue((Writer) out, tasks);
            } else if (out instanceof File) {
                writer.writeValue((File) out, tasks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        jsonDumpRoutine(tasks, out);
    }

    private static void jsonReadRoutine(AbstractTaskList tasks, Object in) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            if (in instanceof Reader) {
                mapper.readerForUpdating(tasks).readValue((Reader) in);
            } else if (in instanceof File) {
                mapper.readerForUpdating(tasks).readValue((File) in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        jsonReadRoutine(tasks, in);
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        jsonDumpRoutine(tasks, file);
    }

    public static void readText(AbstractTaskList tasks, File file) {
        jsonReadRoutine(tasks, file);
    }
}

