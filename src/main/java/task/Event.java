package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date format. Please use 'yyyy-MM-dd HHmm' (e.g., 2025-02-15 2359)."
            );
        }
    }

    public boolean isOnDate(LocalDate date) {
        return (date.isEqual(from.toLocalDate()) || date.isAfter(from.toLocalDate())) && (date.isEqual(to.toLocalDate()) || date.isBefore(to.toLocalDate()));
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(INPUT_FORMATTER) + " | " + to.format(INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMATTER) + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }
}