package eureka;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import command.Command;
import command.CommandParser;
import ui.Ui;
import task.Task;
import task.TaskList;
import task.ToDo;
import task.Deadline;
import task.Event;
import database.Storage;

/**
 * The Eureka application is a chatbot and task management system
 * that allows users to create, modify, and manage tasks.
 * It supports ToDo, Deadline, and Event task types.
 */

public class Eureka {
    private static final String FILE_PATH = "./data/eureka.txt";
    private static final Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ui ui = new Ui();
        CommandParser parser = new CommandParser();
        TaskList tasks = new TaskList(storage.loadTasks());
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter DATE_ONLY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ui.printLogo();
        ui.welcome();

        while (true) {
            // Capture user input commands
            String input = scanner.nextLine();
            Command command = parser.readCommand(input);

            switch (command) {
            case BYE:
                ui.byeMessage();
                scanner.close();
                return;

            case LIST:
                ui.listMessage(tasks);
                break;

            case MARK:
                int markTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (markTaskNumber >= 0 && markTaskNumber < tasks.size()) {
                    tasks.get(markTaskNumber).markAsDone();
                    storage.saveTasks(tasks);
                    ui.markMessage(tasks.get(markTaskNumber));
                }
                break;

            case UNMARK:
                int unmarkTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (unmarkTaskNumber >= 0 && unmarkTaskNumber < tasks.size()) {
                    tasks.get(unmarkTaskNumber).markAsNotDone();
                    storage.saveTasks(tasks);
                    ui.unmarkMessage(tasks.get(unmarkTaskNumber));
                }
                break;

            case TODO:
                if (input.length()<5) {
                    ui.todoWarning();
                    break;
                }
                String todoDescription = input.substring(5);
                tasks.add(new ToDo(todoDescription));
                storage.saveTasks(tasks);
                ui.todoMessage(tasks);
                break;

            case DEADLINE:
                try {
                    if (!input.contains("/by")) {
                        throw new IllegalArgumentException("Invalid format. Use: deadline [description] /by [yyyy-MM-dd HHmm]");
                    }
                    String[] deadlineParts = input.substring(9).split(" /by ", 2);
                    if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                        throw new IllegalArgumentException("Both description and date are required. Example: deadline Submit report /by 2024-02-01 1800");
                    }
                    String deadlineDescription = deadlineParts[0].trim();
                    String deadlineBy = deadlineParts[1].trim();
                    tasks.add(new Deadline(deadlineDescription, deadlineBy));
                    storage.saveTasks(tasks);
                    ui.deadlineMessage(tasks);
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case EVENT:
                try {
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new IllegalArgumentException("Invalid format. Use: event [description] /from [start] /to [end]");
                    }
                    String[] eventParts = input.substring(6).split(" /from | /to ", 3);
                    if (eventParts.length < 3 || eventParts[0].isBlank() || eventParts[1].isBlank() || eventParts[2].isBlank()) {
                        throw new IllegalArgumentException("Description, start, and end times are required. Example: event Workshop /from 2024-02-01 0900 /to 2024-02-01 1200");
                    }
                    String eventDescription = eventParts[0].trim();
                    String eventFrom = eventParts[1].trim();
                    String eventTo = eventParts[2].trim();
                    tasks.add(new Event(eventDescription, eventFrom, eventTo));
                    storage.saveTasks(tasks);
                    ui.eventMessage(tasks);
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
                break;

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
                    ui.deleteMessage(removedTask, tasks.size());
                } catch (NumberFormatException e) {
                    ui.showError("Invalid task number. Please enter a valid number.");
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case CHECK:
                try {
                    LocalDate date = LocalDate.parse(input.substring(6).trim(), DATE_ONLY_FORMAT);
                    ui.printLine();
                    System.out.println("Tasks happening on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");

                    boolean found = false;
                    for (Task task : tasks) {
                        if (task.isOnDate(date)) {
                            System.out.println("  " + task);
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("  No tasks found on this date.");
                    }
                    ui.printLine();
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use: on yyyy-MM-dd");
                }
                break;

            default:
                ui.notUnderstand();
                break;
            }
        }
    }
}
