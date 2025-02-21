package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Event task.
 */

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDate(from)
                .orElseThrow(() -> new IllegalArgumentException("Invalid 'from' date format."));
        this.to = parseDate(to)
                .orElseThrow(() -> new IllegalArgumentException("Invalid 'to' date format."));
    }

    /** Check null value */
    private Optional<LocalDateTime> parseDate(String dateStr) {
        try {
            return Optional.of(LocalDateTime.parse(dateStr, INPUT_FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return !date.isBefore(from.toLocalDate()) && !date.isAfter(to.toLocalDate());
    }

    @Override
    public String toFileString() {
        return String.format("E | %d | %s | %s | %s",
                isDone ? 1 : 0,
                description,
                from.format(INPUT_FORMATTER),
                to.format(INPUT_FORMATTER)
        );
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                from.format(OUTPUT_FORMATTER),
                to.format(OUTPUT_FORMATTER)
        );
    }
}
