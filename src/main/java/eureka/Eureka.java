package eureka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import command.Command;
import command.CommandParser;
import database.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import task.ToDo;
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

    public String getResponse(String input) {
        UiMessage ui = new UiMessage();
        CommandParser parser = new CommandParser();
        TaskList tasks = new TaskList(storage.loadTasks());
        DateTimeFormatter DATE_ONLY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Command command = parser.readCommand(input);

        if (input.equals("test")) {
            return "Nice! GUI is Working.";
        }
        if (input.equals("logo")) {
            return ui.welcome();
        }

        switch (command) {
            case BYE:
                return ui.byeMessage();

            case LIST:
                return ui.listMessage(tasks);

            case FIND:
                if (input.length() < 5) {
                    return ui.findWarning();
                }
                String findDescription = input.substring(5);
                return ui.find(tasks, findDescription);

            case MARK:
                try {
                    int markTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    assert markTaskNumber >= 0 && markTaskNumber <= tasks.size(): "Task number out of bounds";
                    tasks.get(markTaskNumber).markAsDone();
                    storage.saveTasks(tasks);
                    return ui.markMessage(tasks.get(markTaskNumber));
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    return ui.showError(e.getMessage());
                }

            case UNMARK:
                try {
                    int unmarkTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    assert unmarkTaskNumber >= 0 && unmarkTaskNumber <= tasks.size(): "Task number out of bounds";
                    tasks.get(unmarkTaskNumber).markAsNotDone();
                    storage.saveTasks(tasks);
                    return ui.unmarkMessage(tasks.get(unmarkTaskNumber));
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    return ui.showError(e.getMessage());
                }

            case TODO:
                if (input.length() < 5) {
                    return ui.todoWarning();
                }
                String todoDescription = input.substring(5);
                tasks.add(new ToDo(todoDescription));
                storage.saveTasks(tasks);
                return ui.todoMessage(tasks);

            case DEADLINE:
                try {
                    if (!input.contains("/by")) {
                        throw new IllegalArgumentException("Invalid format. Use: "
                                + "deadline [description] /by [yyyy-MM-dd HHmm]");
                    }
                    String[] deadlineParts = input.substring(9).split(" /by ", 2);
                    if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                        throw new IllegalArgumentException("Both description and "
                                + "date are required. Example: deadline Submit report /by 2024-02-01 1800");
                    }
                    String deadlineDescription = deadlineParts[0].trim();
                    String deadlineBy = deadlineParts[1].trim();
                    tasks.add(new Deadline(deadlineDescription, deadlineBy));
                    storage.saveTasks(tasks);
                    return ui.deadlineMessage(tasks);
                } catch (IllegalArgumentException e) {
                    return ui.showError(e.getMessage());
                }

            case EVENT:
                try {
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new IllegalArgumentException("Invalid format. Use: "
                                + "event [description] /from [start] /to [end]");
                    }
                    String[] eventParts = input.substring(6).split(" /from | /to ", 3);
                    if (eventParts.length < 3 || eventParts[0].isBlank()
                            || eventParts[1].isBlank() || eventParts[2].isBlank()) {
                        throw new IllegalArgumentException("Description, start, and end times are required. "
                                + "Example: event Workshop /from 2024-02-01 0900 /to 2024-02-01 1200");
                    }
                    String eventDescription = eventParts[0].trim();
                    String eventFrom = eventParts[1].trim();
                    String eventTo = eventParts[2].trim();
                    tasks.add(new Event(eventDescription, eventFrom, eventTo));
                    storage.saveTasks(tasks);
                    return ui.eventMessage(tasks);
                } catch (IllegalArgumentException e) {
                    return ui.showError(e.getMessage());
                }

            case DELETE:
                try {
                    String[] deleteParts = input.split(" ");
                    if (deleteParts.length != 2) {
                        throw new IllegalArgumentException("Invalid format. Use: delete [task number]");
                    }
                    int deleteTaskNumber = Integer.parseInt(deleteParts[1]) - 1;
                    if (deleteTaskNumber < 0 || deleteTaskNumber >= tasks.size()) {
                        throw new IndexOutOfBoundsException("Task number does not exist.");
                    }
                    Task removedTask = tasks.remove(deleteTaskNumber);
                    storage.saveTasks(tasks);
                    return ui.deleteMessage(removedTask, tasks.size());
                } catch (NumberFormatException e) {
                    return ui.showError("Invalid task number. Please enter a valid number.");
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    return ui.showError(e.getMessage());
                }

            case CHECK:
                try {
                    LocalDate date = LocalDate.parse(input.substring(6).trim(), DATE_ONLY_FORMAT);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Tasks happening on ").append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))).
                            append(":").append("\n");

                    boolean found = false;
                    for (Task task : tasks) {
                        if (task.isOnDate(date)) {
                            sb.append(task).append("\n");
                            found = true;
                        }
                    }

                    if (!found) {
                        return "No tasks found on this date.";
                    } else {
                        return sb.toString();
                    }
                } catch (Exception e) {
                    return "Invalid date format. Use: on yyyy-MM-dd";
                }

            default:
                return ui.notUnderstand();
        }
    }
}
