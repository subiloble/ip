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
                String[] deadlineParts = input.substring(9).split(" /by ");
                String deadlineDescription = deadlineParts[0];
                String deadlineBy = deadlineParts[1];
                tasks.add(new Deadline(deadlineDescription, deadlineBy));
                storage.saveTasks(tasks);
                ui.deadMessage(tasks);
                break;

            case EVENT:
                String[] eventParts = input.substring(6).split(" /from | /to ");
                String eventDescription = eventParts[0];
                String eventFrom = eventParts[1];
                String eventTo = eventParts[2];
                tasks.add(new Event(eventDescription, eventFrom, eventTo));
                storage.saveTasks(tasks);
                ui.eventMessage(tasks);
                break;

            case DELETE:
                int deleteTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (deleteTaskNumber < 0 || deleteTaskNumber >= tasks.size()) {
                    ui.deleteNotFound();
                } else {
                    Task removedTask = tasks.remove(deleteTaskNumber);
                    storage.saveTasks(tasks);
                    ui.deleteMessage(removedTask, tasks.size());
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
