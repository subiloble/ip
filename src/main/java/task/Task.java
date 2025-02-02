package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Task */

public abstract class Task {
    /** Description of the task */
    String description;
    /** Status of the task being done or not */
    boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks the task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks the task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Returns the status of the task.
     *
     * @return Message referring to the status of the task. */
    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    /** Returns whether the task happens on certain date.
     *
     * @param date Date by when whether the task happens is shown.
     * @return Whether the task is going to happen on the date. */
    public abstract boolean isOnDate(LocalDate date);

    /** Returns the line of the task to be saved to a task book.
     *
     * @return String of the task to be saved into a task book. */
    public abstract String toFileString();

    @Override
    public String toString() {
        return getStatus() + " " + description;
    }
}