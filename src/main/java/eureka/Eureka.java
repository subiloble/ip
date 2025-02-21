package eureka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import command.Command;
import command.CommandParser;
import database.Storage;
import task.*;
import ui.UiMessage;

/**
 * The Eureka application is a chatbot and task management system
 * that allows users to create, modify, and manage tasks.
 * It supports ToDo, Deadline, and Event task types.
 */

@SuppressWarnings("CheckStyle")
public class Eureka {
    private static final String FILE_PATH = "./data/eureka.txt";
    private static final Storage storage = new Storage(FILE_PATH);
    private static final UiMessage ui = new UiMessage();
    private static final CommandParser parser = new CommandParser();
    private static final DateTimeFormatter DATE_ONLY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TaskList tasks = new TaskList(storage.loadTasks());
    public TaskHistory taskHist = new TaskHistory((TaskList) tasks.clone());

    private int parseTaskNumber(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format. Use: [command] [task number]");
        }
        int taskNumber = Integer.parseInt(parts[1]) - 1;
        if (taskNumber < 0 || taskNumber >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number does not exist.");
        }
        return taskNumber;
    }

    private String[] parseDeadlineInput(String input) {
        if (!input.contains("/by")) {
            throw new IllegalArgumentException("Invalid format. Use: deadline [description] /by [yyyy-MM-dd HHmm]");
        }
        String[] parts = input.substring(9).split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new IllegalArgumentException("Both description and date are required.");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    private String[] parseEventInput(String input) {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new IllegalArgumentException("Invalid format. Use: event [description] /from [start] /to [end]");
        }
        String[] parts = input.substring(6).split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].isBlank() || parts[1].isBlank() || parts[2].isBlank()) {
            throw new IllegalArgumentException("Description, start, and end times are required.");
        }
        return new String[]{parts[0].trim(), parts[1].trim(), parts[2].trim()};
    }

    private void updateStorage() {
        storage.saveTasks(tasks);
        taskHist.saveHistory(tasks);
    }

    private String getTasksForDate(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tasks happening on ").append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))).append(":\n");

        boolean found = false;
        for (Task task : tasks) {
            if (task.isOnDate(date)) {
                sb.append(task).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No tasks found on this date.";
    }

    private String handleFind(String input) {
        if (input.length() < 5) {
            return ui.findWarning();
        }
        String findDescription = input.substring(5);
        return ui.find(tasks, findDescription);
    }

    private String handleMark(String input) {
        try {
            int taskNumber = parseTaskNumber(input);
            tasks.get(taskNumber).markAsDone();
            updateStorage();
            return ui.markMessage(tasks.get(taskNumber));
        } catch (NumberFormatException e) {
            return ui.showError("Invalid task number. Please enter a number.");
        } catch (IndexOutOfBoundsException e) {
            return ui.showError("Task number does not exist. Please check your list.");
        } catch (Exception e) {
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private String handleUnmark(String input) {
        try {
            int taskNumber = parseTaskNumber(input);
            tasks.get(taskNumber).markAsNotDone();
            updateStorage();
            return ui.unmarkMessage(tasks.get(taskNumber));
        } catch (NumberFormatException e) {
            return ui.showError("Invalid task number. Please enter a number.");
        } catch (IndexOutOfBoundsException e) {
            return ui.showError("Task number does not exist. Please check your list.");
        } catch (Exception e) {
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private String handleTodo(String input) {
        if (input.length() < 5) {
            return ui.todoWarning();
        }
        String todoDescription = input.substring(5);
        tasks.add(new ToDo(todoDescription));
        updateStorage();
        return ui.todoMessage(tasks);
    }

    private String handleDeadline(String input) {
        try {
            String[] parts = parseDeadlineInput(input);
            tasks.add(new Deadline(parts[0], parts[1]));
            updateStorage();
            return ui.deadlineMessage(tasks);
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            return ui.showError("Invalid date format. Use: yyyy-MM-dd HHmm");
        } catch (Exception e) {
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private String handleEvent(String input) {
        try {
            String[] parts = parseEventInput(input);
            tasks.add(new Event(parts[0], parts[1], parts[2]));
            updateStorage();
            return ui.eventMessage(tasks);
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            return ui.showError("Invalid date format. Use: yyyy-MM-dd HHmm");
        } catch (Exception e) {
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private String handleDelete(String input) {
        try {
            int taskNumber = parseTaskNumber(input);
            Task removedTask = tasks.remove(taskNumber);
            updateStorage();
            return ui.deleteMessage(removedTask, tasks.size());
        } catch (Exception e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleCheck(String input) {
        try {
            LocalDate date = LocalDate.parse(input.substring(6).trim(), DATE_ONLY_FORMAT);
            return getTasksForDate(date);
        } catch (Exception e) {
            return "Invalid date format. Use: on yyyy-MM-dd";
        }
    }

    private String handleUndo() {
        if (taskHist.canDeleteHistory()) {
            tasks = taskHist.deleteHistory();
            updateStorage();
            return ui.listMessage(tasks);
        } else {
            return "No history found.";
        }
    }

    public String getResponse(String input) {
        Command command = parser.readCommand(input);

        if (input.equals("test")) {
            return "Last operation undone successfully.\nAdd 111";
        }
        if (input.equals("logo")) {
            return ui.welcome();
        }

        return switch (command) {
            case BYE -> ui.byeMessage();
            case LIST -> ui.listMessage(tasks);
            case FIND -> handleFind(input);
            case MARK -> handleMark(input);
            case UNMARK -> handleUnmark(input);
            case TODO -> handleTodo(input);
            case DEADLINE -> handleDeadline(input);
            case EVENT -> handleEvent(input);
            case DELETE -> handleDelete(input);
            case CHECK -> handleCheck(input);
            case UNDO -> handleUndo();
            default -> ui.notUnderstand();
        };
    }
}
