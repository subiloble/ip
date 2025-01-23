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
            } else {
                printLine();
                System.out.print("  ");
                System.out.println(input);
                printLine();
            }
        }
        scanner.close();
    }
}
