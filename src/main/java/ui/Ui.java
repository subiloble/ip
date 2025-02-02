package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import task.Task;
import task.TaskList;

/**
 * Chatbot responses and user interface.
 */

public class Ui {
    /** Logo */
    private final String logo;

    /** Initialise Ui wth Eureka logo. */
    public Ui() {
        this.logo = "  ______ _    _ _____  ______ _   __      __       \n"
                + " |  ____| |  | |  __ \\|  ____| | / /    / /\\ \\     \n"
                + " | |__  | |  | | |__) | |__  |  / /    / /__\\ \\     \n"
                + " |  __| | |  | |  _  /|  __| |   <    / /____\\ \\       \n"
                + " | |____| |__| | | \\ \\| |____|  \\ \\  / /      \\ \\ \n"
                + " |______|\\____/|_|  \\_\\______|_| \\_\\/_/        \\_\\\n";
        ;
    }

    /** Prints underlines for separation. */
    public void printLine() {
        System.out.print("  ");
        for (int i = 0; i < 50; i++) {
            System.out.print("_");
        }
        System.out.println();
        System.out.println();
    }

    /** Prints logo. */
    public void printLogo() {
        System.out.println(logo);
    }

    /**
     * Shows error messages.
     *
     * @param message Message to be printed.
     */
    public void showError(String message) {
        printLine();
        System.out.println("  " + message);
        printLine();
    }

    /** Prints welcome message. */
    public void welcome() {
        System.out.println("  Hello! I'm Eureka.");
        System.out.println("  What can I do for you?");
        printLine();
    }

    /** Prints bye message. */
    public void byeMessage() {
        printLine();
        System.out.println("  Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints all tasks in the given task list.
     *
     * @param tasks Task list.
     */
    public void listMessage(TaskList tasks) {
        printLine();
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    /** Prints the warning message for finding a task. */
    public void findWarning() {
        printLine();
        System.out.println("  Finding cannot work without a part of description.");
        printLine();
    }

    /**
     * Prints all tasks in the given task list that contains certain keywords.
     *
     * @param tasks Task list.
     * @param des Description keywords.
     */
    public void find(TaskList tasks, String des) {
        printLine();
        System.out.println("  Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).description.contains(des)) {
                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
            }
        }
        printLine();
    }

    /**
     * Prints the message for change of status from unmarked to marked for the given task.
     *
     * @param task Task marked as done.
     */

    public void markMessage(Task task) {
        printLine();
        System.out.println("  Nice! I've marked this task as done:");
        System.out.println("    " + task);
        printLine();
    }

    /** Prints the message for change of status from marked to unmarked for the given task.
     *
     * @param task Task marked as not done.
     */
    public void unmarkMessage(Task task) {
        printLine();
        System.out.println("  OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        printLine();
    }

    /** Prints the warning message for todo initialisation. */
    public void todoWarning() {
        printLine();
        System.out.println("  A todo without a description doesn’t work. Try again with more details!");
        printLine();
    }

    /**
     * Prints the completion message for todo initialisation.
     *
     * @param tasks Task list where a todo task is added.
     */
    public void todoMessage(ArrayList<Task> tasks) {
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Prints the completion message for deadline initialisation.
     *
     * @param tasks Task list where a deadline task is added.
     */
    public void deadlineMessage(TaskList tasks) {
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Prints the completion message for event initialisation.
     *
     * @param tasks Task list where an event task is added.
     */
    public void eventMessage(TaskList tasks) {
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Prints the completion message for deletion of a task.
     *
     * @param removedTask Task being deleted.
     * @param size Size of the task list where a task is deleted.
     */
    public void deleteMessage(Task removedTask, int size) {
        printLine();
        System.out.println("  Noted. I've removed this task:");
        System.out.println("    " + removedTask);
        System.out.println("  Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void notUnderstand() {
        printLine();
        System.out.println("  Sorry, I didn’t get that. Could you try something else?");
        printLine();
    }
}