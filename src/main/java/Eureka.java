import java.util.Scanner;
import java.util.ArrayList;

enum TaskType {
    todo, deadline, event
}

enum Command {
    bye, list, mark, unmark, todo, deadline, event, delete, unknown
}

abstract class Task {
    String description;
    boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    @Override
    public String toString() {
        return getStatus() + " " + description;
    }
}

class ToDo extends  Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    String from;
    String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

public class Eureka {
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
            return Command.bye;
        } else if (userInput.equals("list")) {
            return Command.list;
        } else if (userInput.startsWith("mark ")) {
            return Command.mark;
        } else if (userInput.startsWith("unmark ")) {
            return Command.unmark;
        } else if (userInput.startsWith("todo")) {
            return Command.todo;
        } else if (userInput.startsWith("deadline ")) {
            return Command.deadline;
        } else if (userInput.startsWith("event ")) {
            return Command.event;
        } else if (userInput.startsWith("delete ")) {
            return Command.delete;
        } else {
            return Command.unknown;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        int taskCount = 0;

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
                case bye:
                    printLine();
                    System.out.println("  Bye. Hope to see you again soon!");
                    printLine();
                    scanner.close();
                    return;

                case list:
                    printLine();
                    System.out.println("  Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("  " + (i + 1) + ". " + tasks.get(i));
                    }
                    printLine();
                    break;

                case mark:
                    int markTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (markTaskNumber >= 0 && markTaskNumber < taskCount) {
                        tasks.get(markTaskNumber).markAsDone();
                        printLine();
                        System.out.println("  Nice! I've marked this task as done:");
                        System.out.println("    " + tasks.get(markTaskNumber));
                        printLine();
                    }
                    break;

                case unmark:
                    int unmarkTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (unmarkTaskNumber >= 0 && unmarkTaskNumber < taskCount) {
                        tasks.get(unmarkTaskNumber).markAsNotDone();
                        printLine();
                        System.out.println("  OK, I've marked this task as not done yet:");
                        System.out.println("    " + tasks.get(unmarkTaskNumber));
                        printLine();
                    }
                    break;

                case todo:
                    if (input.length()<5) {
                        printLine();
                        System.out.println("  A todo without a description doesn’t work. Try again with more details!");
                        printLine();
                        break;
                    }
                    String todoDescription = input.substring(5);
                    tasks.add(new ToDo(todoDescription));
                    taskCount++;
                    printLine();
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("    " + tasks.get(taskCount - 1));
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    printLine();
                    break;

                case deadline:
                    String[] deadlineParts = input.substring(9).split(" /by ");
                    String deadlineDescription = deadlineParts[0];
                    String deadlineBy = deadlineParts[1];
                    tasks.add(new Deadline(deadlineDescription, deadlineBy));
                    taskCount++;
                    printLine();
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("    " + tasks.get(taskCount - 1));
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    printLine();
                    break;

                case event:
                    String[] eventParts = input.substring(6).split(" /from | /to ");
                    String eventDescription = eventParts[0];
                    String eventFrom = eventParts[1];
                    String eventTo = eventParts[2];
                    tasks.add(new Event(eventDescription, eventFrom, eventTo));
                    taskCount++;
                    printLine();
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("    " + tasks.get(taskCount - 1));
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    printLine();
                    break;

                case delete:
                    int deleteTaskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (deleteTaskNumber < 0 || deleteTaskNumber >= tasks.size()) {
                        printLine();
                        System.out.println("Oops! That task doesn’t exist. Try a valid task number.");
                        printLine();
                    } else {
                        Task removedTask = tasks.remove(deleteTaskNumber);
                        printLine();
                        System.out.println("  Noted. I've removed this task:");
                        System.out.println("    " + removedTask);
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        printLine();
                        taskCount--;
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
