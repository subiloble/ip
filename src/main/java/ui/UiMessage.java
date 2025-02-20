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
                Ah, greetings, my dear pupil!
                How may I expand your horizons today?
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
        return "Farewell, dear scholar! Until we meet again!";
    }

    /**
     * Returns all tasks in the given task list.
     *
     * @param tasks Task list.
     * @return Message.
     */
    public String listMessage(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Behold! Your noble tasks await:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /** Returns the warning message for finding a task. */
    public String findWarning() {
        return "Ah, dear apprentice! Finding cannot work without a clue.";
    }

    /**
     * Returns all tasks in the given task list that contains certain keywords.
     *
     * @param tasks Task list.
     * @param des Description keywords.
     *
     * @return Message.
     */
    public String find(TaskList tasks, String des) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aha! I have scoured the depths of your list and found these matches:");
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
        return "Splendid! This task has been vanquished with great success:\n" + task;
    }

    /** Returns the message for change of status from marked to unmarked for the given task.
     *
     * @param task Task marked as not done.
     */
    public String unmarkMessage(Task task) {
        return "Oh dear! This task has been resurrected from the realm of completion:\n" + task;
    }

    /** Returns the warning message for todo initialisation. */
    public String todoWarning() {
        return "Kindly provide a description so I may assist you properly.";
    }

    /**
     * Returns the completion message for todo initialisation.
     *
     * @param tasks Task list where a todo task is added.
     *
     * @return Message.
     */
    public String todoMessage(ArrayList<Task> tasks) {
        return "Ah, an intellectual pursuit! A wise choice, my friend.\n" +
                tasks.get(tasks.size() - 1) + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for deadline initialisation.
     *
     * @param tasks Task list where a deadline task is added.
     *
     * @return Message.
     */
    public String deadlineMessage(TaskList tasks) {
        return "A deadline, you say? Pressure makes diamonds, my dear apprentice.\n" +
                tasks.get(tasks.size() - 1) + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for event initialisation.
     *
     * @param tasks Task list where an event task is added.
     *
     * @return Message.
     */
    public String eventMessage(TaskList tasks) {
        return "Ah, an event! How delightful! I have inscribed it in the grand tome of schedules:\n" +
                tasks.get(tasks.size() - 1) + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the completion message for deletion of a task.
     *
     * @param removedTask Task being deleted.
     * @param size Size of the task list where a task is deleted.
     *
     * @return Message.
     */
    public String deleteMessage(Task removedTask, int size) {
        return "Task annihilated! If only all life's problems had a delete button.\n" +
                removedTask + "\n" +
                "Now you have " + size + " tasks in the list.";
    }

    /** Returns the message for commands not identified*/
    public String notUnderstand() {
        return "A peculiar request! Perhaps you meant something else?";
    }
}
