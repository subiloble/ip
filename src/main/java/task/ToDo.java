package task;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public boolean isOnDate(LocalDate date) {
        return false;
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}