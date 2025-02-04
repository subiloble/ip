package ui;

import java.util.ArrayList;

import task.Task;
import task.TaskList;

/**
 * Chatbot response messages.
 */

public class UiMessage {
    /** Logo */
    private final String logo;

    /** Initialise UiMessage wth Eureka logo. */
    public UiMessage() {
        this.logo = "  ______ _    _ _____  ______ _   __      __       \n"
                + " |  ____| |  | |  __ \\|  ____| | / /    / /\\ \\     \n"
                + " | |__  | |  | | |__) | |__  |  / /    / /__\\ \\     \n"
                + " |  __| | |  | |  _  /|  __| |   <    / /____\\ \\       \n"
                + " | |____| |__| | | \\ \\| |____|  \\ \\  / /      \\ \\ \n"
                + " |______|\\____/|_|  \\_\\______|_| \\_\\/_/        \\_\\\n";
        ;
    }

    /** Returns logo. */
    public String printLogo() {
        return logo;
    }

    /** Returns welcome message. */
    public String welcome() {
        return """
                Hello! I'm Eureka.
                What can I do for you?
                """;
    }

    /**
     * Returns error messages.
     *
     * @param message Message to be printed.
     */
    public String showError(String message) {
        return message;
    }

    public String byeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns all tasks in the given task list.
     *
     * @param tasks Task list.
     * @return Message.
     */
    public String listMessage(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /** Returns the warning message for finding a task. */
    public String findWarning() {
        return "Finding cannot work without a part of description.";
    }

    /**
     * Returns all tasks in the given task list that contains certain keywords.
     *
     * @param tasks Task list.
     * @param des Description keywords.
     */
    public String find(TaskList tasks, String des) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).description.contains(des)) {
                sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the message for change of status from unmarked to marked for the given task.
     *
     * @param task Task marked as done.
     * @return Message.
     */

    public String markMessage(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /** Returns the message for change of status from marked to unmarked for the given task.
     *
     * @param task Task marked as not done.
     */
    public String unmarkMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /** Returns the warning message for todo initialisation. */
    public String todoWarning() {
        return "A todo without a description doesn’t work. Try again with more details!";
    }

    /**
     * Returns the completion message for todo initialisation.
     *
     * @param tasks Task list where a todo task is added.
     */
    public String todoMessage(ArrayList<Task> tasks) {
        return "Got it. I've added this todo:\n" +
                tasks.get(tasks.size() - 1) +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for deadline initialisation.
     *
     * @param tasks Task list where a deadline task is added.
     */
    public String deadlineMessage(TaskList tasks) {
        return "Got it. I've added this deadline:\n" +
                tasks.get(tasks.size() - 1) +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for event initialisation.
     *
     * @param tasks Task list where an event task is added.
     */
    public String eventMessage(TaskList tasks) {
        return "Got it. I've added this event:\n" +
                tasks.get(tasks.size() - 1) +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for deletion of a task.
     *
     * @param removedTask Task being deleted.
     * @param size Size of the task list where a task is deleted.
     */
    public String deleteMessage(Task removedTask, int size) {
        return "Noted. I've removed this task:\n" +
                removedTask +
                "Now you have " + size + " tasks in the list.";
    }

    /** Returns the message for commands not identified*/
    public String notUnderstand() {
        return "Sorry, I didn’t get that. Could you try something else?";
    }
}
