package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class EventTest {

    @Test
    void testValidEventCreation() {
        Event event = new Event("Team meeting", "2025-02-10 0900", "2025-02-10 1100");
        assertEquals("[E][ ] Team meeting (from: Feb 10 2025, 9:00 AM to: Feb 10 2025, 11:00 AM)", event.toString());
    }

    @Test
    void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Event("Project presentation", "10-02-2025 0900", "2025-02-10 1100"); // Wrong date format
        });
        assertEquals("Invalid date format. Please use 'yyyy-MM-dd HHmm' (e.g., 2025-02-15 2359).", exception.getMessage());
    }

    @Test
    void testIsOnDate() {
        Event event = new Event("Workshop", "2025-03-01 1000", "2025-03-03 1600");
        assertTrue(event.isOnDate(LocalDate.of(2025, 3, 1)));  // Start date
        assertTrue(event.isOnDate(LocalDate.of(2025, 3, 2)));  // Middle date
        assertTrue(event.isOnDate(LocalDate.of(2025, 3, 3)));  // End date
        assertFalse(event.isOnDate(LocalDate.of(2025, 2, 28))); // Before event
        assertFalse(event.isOnDate(LocalDate.of(2025, 3, 4)));  // After event
    }

    @Test
    void testToFileString() {
        Event event = new Event("Conference", "2025-04-15 0800", "2025-04-15 1700");
        assertEquals("E | 0 | Conference | 2025-04-15 0800 | 2025-04-15 1700", event.toFileString());
    }

    @Test
    void testMarkAsDone() {
        Event event = new Event("Client call", "2025-05-20 1400", "2025-05-20 1500");
        event.markAsDone();
        assertEquals("[E][X] Client call (from: May 20 2025, 2:00 PM to: May 20 2025, 3:00 PM)", event.toString());
    }
}
