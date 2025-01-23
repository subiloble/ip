import java.util.Scanner;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
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
            if (input.equals("bye")) {
                printLine();
                System.out.println("  Bye. Hope to see you again soon!");
                printLine();
                break;
            } else if (input.equals("list"))  {
                printLine();
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println("  " + (i + 1) + ". " + status + " " + tasks[i]);
                }
                printLine();
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (taskNumber >= 0 && taskNumber < taskCount) {
                    isDone[taskNumber] = true;
                    printLine();
                    System.out.println("  Nice! I've marked this task as done:");
                    System.out.println("    [X] " + tasks[taskNumber]);
                    printLine();
                }
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (taskNumber >= 0 && taskNumber < taskCount) {
                    isDone[taskNumber] = false;
                    printLine();
                    System.out.println("  OK, I've marked this task as not done yet:");
                    System.out.println("    [ ] " + tasks[taskNumber]);
                    printLine();
                }
            } else {
                printLine();
                tasks[taskCount] = input;
                isDone[taskCount] = false;
                taskCount++;
                System.out.println("  added: " + input);
                printLine();
            }
        }
        scanner.close();
    }
}
