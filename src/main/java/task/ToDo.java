package task;

import java.time.LocalDate;

/**
 * ToDo task.
 */

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return false;
    }

    @Override
    public String toFileString() {
        return String.format("T | %d | %s", isDone ? 1 : 0, description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
