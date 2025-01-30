import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum Command {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, CHECK, UNKNOWN
}

public class Eureka {
    private static final String FILE_PATH = "./data/eureka.txt";
    private static final Storage storage = new Storage(FILE_PATH);

    static void printLine() {
        System.out.print("  ");
        for(int i = 0; i < 50; i++)
        {
            System.out.print("_");
        }
        System.out.println();
        System.out.println();
    }

    static Command parseCommand(String userInput) {
        if (userInput.equals("bye")) {
            return Command.BYE;
        } else if (userInput.equals("list")) {
            return Command.LIST;
        } else if (userInput.startsWith("mark ")) {
            return Command.MARK;
        } else if (userInput.startsWith("unmark ")) {
            return Command.UNMARK;
        } else if (userInput.startsWith("todo")) {
            return Command.TODO;
        } else if (userInput.startsWith("deadline ")) {
            return Command.DEADLINE;
        } else if (userInput.startsWith("event ")) {
            return Command.EVENT;
        } else if (userInput.startsWith("delete ")) {
            return Command.DELETE;
        } else if (userInput.startsWith("check ")) {
            return Command.CHECK;
        } else {
            return Command.UNKNOWN;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = storage.loadTasks();
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter DATE_ONLY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String logo = "  ______ _    _ _____  ______ _   __      __       \n"
                    + " |  ____| |  | |  __ \\|  ____| | / /    / /\\ \\     \n"
                    + " | |__  | |  | | |__) | |__  |  / /    / /__\\ \\     \n"
                    + " |  __| | |  | |  _  /|  __| |   <    / /____\\ \\       \n"
                    + " | |____| |__| | | \\ \\| |____|  \\ \\  / /      \\ \\ \n"
                    + " |______|\\____/|_|  \\_\\______|_| \\_\\/_/        \\_\\\n";
        System.out.println(logo);
        System.out.println("  Hello! I'm Eureka.");
        System.out.println("  What can I do for you?");
        printLine();

        while (true) {
            String input = scanner.nextLine();
            Command command = parseCommand(input);

            switch (command) {
            case BYE:
                printLine();
                System.out.println("  Bye. Hope to see you again soon!");
                printLine();
                scanner.close();
                return;

            case LIST:
                printLine();
                System.out.println("  Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + tasks.get(i));
                }
                printLine();
                break;

            case MARK:
                int markTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (markTaskNumber >= 0 && markTaskNumber < tasks.size()) {
                    tasks.get(markTaskNumber).markAsDone();
                    storage.saveTasks(tasks);
                    printLine();
                    System.out.println("  Nice! I've marked this task as done:");
                    System.out.println("    " + tasks.get(markTaskNumber));
                    printLine();
                }
                break;

            case UNMARK:
                int unmarkTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (unmarkTaskNumber >= 0 && unmarkTaskNumber < tasks.size()) {
                    tasks.get(unmarkTaskNumber).markAsNotDone();
                    storage.saveTasks(tasks);
                    printLine();
                    System.out.println("  OK, I've marked this task as not done yet:");
                    System.out.println("    " + tasks.get(unmarkTaskNumber));
                    printLine();
                }
                break;

            case TODO:
                if (input.length()<5) {
                    printLine();
                    System.out.println("  A todo without a description doesn’t work. Try again with more details!");
                    printLine();
                    break;
                }
                String todoDescription = input.substring(5);
                tasks.add(new ToDo(todoDescription));
                storage.saveTasks(tasks);
                printLine();
                System.out.println("  Got it. I've added this task:");
                System.out.println("    " + tasks.get(tasks.size() - 1));
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                printLine();
                break;

            case DEADLINE:
                String[] deadlineParts = input.substring(9).split(" /by ");
                String deadlineDescription = deadlineParts[0];
                String deadlineBy = deadlineParts[1];
                tasks.add(new Deadline(deadlineDescription, deadlineBy));
                storage.saveTasks(tasks);
                printLine();
                System.out.println("  Got it. I've added this task:");
                System.out.println("    " + tasks.get(tasks.size() - 1));
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                printLine();
                break;

            case EVENT:
                String[] eventParts = input.substring(6).split(" /from | /to ");
                String eventDescription = eventParts[0];
                String eventFrom = eventParts[1];
                String eventTo = eventParts[2];
                tasks.add(new Event(eventDescription, eventFrom, eventTo));
                storage.saveTasks(tasks);
                printLine();
                System.out.println("  Got it. I've added this task:");
                System.out.println("    " + tasks.get(tasks.size() - 1));
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                printLine();
                break;

            case DELETE:
                int deleteTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (deleteTaskNumber < 0 || deleteTaskNumber >= tasks.size()) {
                    printLine();
                    System.out.println("Oops! That task doesn’t exist. Try a valid task number.");
                    printLine();
                } else {
                    Task removedTask = tasks.remove(deleteTaskNumber);
                    storage.saveTasks(tasks);
                    printLine();
                    System.out.println("  Noted. I've removed this task:");
                    System.out.println("    " + removedTask);
                    System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                    printLine();
                }
                break;

            case CHECK:
                try {
                    LocalDate date = LocalDate.parse(input.substring(6).trim(), DATE_ONLY_FORMAT);
                    printLine();
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
                    printLine();
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use: on yyyy-MM-dd");
                }
                break;

            default:
                printLine();
                System.out.println("  Sorry, I didn’t get that. Could you try something else?");
                printLine();
                break;
            }
        }
    }
}
