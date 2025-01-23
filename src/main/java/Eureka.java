public class Eureka {
    static void printLine(int n) {
        for(int i = 0; i < n; i++)
        {
            System.out.print("_");
        }
        System.out.println();
        System.out.println();
    }
    public static void main(String[] args) {
        String logo = "  ______ _    _ _____  ______ _   __      __       \n"
                    + " |  ____| |  | |  __ \\|  ____| | / /    / /\\ \\     \n"
                    + " | |__  | |  | | |__) | |__  |  / /    / /__\\ \\     \n"
                    + " |  __| | |  | |  _  /|  __| |   <    / /____\\ \\       \n"
                    + " | |____| |__| | | \\ \\| |____|  \\ \\  / /      \\ \\ \n"
                    + " |______|\\____/|_|  \\_\\______|_| \\_\\/_/        \\_\\\n";
        System.out.println(logo);
        System.out.println("  Hello! I'm Eureka.");
        System.out.println("  What can I do for you?");
        printLine(50);
        System.out.println("  Bye. Hope to see you again soon!");

    }
}
