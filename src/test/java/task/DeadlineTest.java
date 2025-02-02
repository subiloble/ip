package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class DeadlineTest {

    @Test
    void testValidDeadlineCreation() {
        Deadline deadline = new Deadline("Submit assignment", "2025-02-15 2359");
        assertEquals("[D][ ] Submit assignment (by: Feb 15 2025, 11:59 PM)", deadline.toString());
    }

    @Test
    void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Deadline("Submit report", "15-02-2025 2359"); // Wrong date format
        });
        assertEquals("Invalid date format. Please use 'yyyy-MM-dd HHmm' (e.g., 2025-02-15 2359).", exception.getMessage());
    }

    @Test
    void testIsOnDate() {
        Deadline deadline = new Deadline("Pay bills", "2024-12-01 1200");
        assertTrue(deadline.isOnDate(LocalDate.of(2024, 12, 1)));
        assertFalse(deadline.isOnDate(LocalDate.of(2024, 11, 30)));
    }

    @Test
    void testToFileString() {
        Deadline deadline = new Deadline("Renew license", "2025-03-10 0900");
        assertEquals("D | 0 | Renew license | 2025-03-10 0900", deadline.toFileString());
    }

    @Test
    void testMarkAsDone() {
        Deadline deadline = new Deadline("Attend meeting", "2025-01-20 1500");
        deadline.markAsDone();
        assertEquals("[D][X] Attend meeting (by: Jan 20 2025, 3:00 PM)", deadline.toString());
    }
}