package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task {
    public String description;
    public boolean isDone;

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

    public abstract boolean isOnDate(LocalDate date);

    public abstract String toFileString();

    @Override
    public String toString() {
        return getStatus() + " " + description;
    }
}