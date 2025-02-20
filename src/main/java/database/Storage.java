package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import task.Task;
import task.Event;
import task.Deadline;
import task.ToDo;

/** Database storing all tasks in the Eureka task book */

public class Storage {
    private final String filePath;

    /**
     * Initialises the task book.
     *
     * @param filePath Path of the task book.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /** Saves the task book as a new task book.
     *
     * @param tasks Task list containing all tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     *  Creates a task given its information.
     *
     * @param type Type of the task.
     * @param description Description of the task.
     * @param parts Array of other potential parameters of the task.
     * @return Task created using the given parameters.
     */

    private Task createTask(String type, String description, String[] parts) {
        return switch (type) {
            case "T" -> new ToDo(description);
            case "D" -> parts.length > 3 ? new Deadline(description, parts[3]) : null;
            case "E" -> parts.length > 4 ? new Event(description, parts[3], parts[4]) : null;
            default -> {
                System.err.println("Unidentified task type: " + type);
                yield null;
            }
        };
    }

    /** Loads the task book.
     *
     * @return Task book.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" \\| ");
                String type = parts[0];
                boolean isDone = "1".equals(parts[1]);
                String description = parts[2];

                Task task = createTask(type, description, parts);

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Corrupt data file detected. Starting fresh.");
        } catch (DateTimeParseException e) {
            System.out.println("Corrupt date/time format. Starting fresh.");
        }

        return tasks;
    }
}