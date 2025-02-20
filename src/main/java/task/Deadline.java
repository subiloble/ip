package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Deadline task.
 */

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDate(by)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid date format. Please use 'yyyy-MM-dd HHmm' (e.g., 2025-02-15 2359)."
                ));
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
        return Optional.ofNullable(by)
                .map(LocalDateTime::toLocalDate)
                .map(date::equals)
                .orElse(false);
    }

    @Override
    public String toFileString() {
        return String.format("D | %d | %s | %s",
                isDone ? 1 : 0,
                description,
                by != null ? by.format(INPUT_FORMATTER) : "N/A"
        );
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(),
                by != null ? by.format(OUTPUT_FORMATTER) : "Invalid Date"
        );
    }
}