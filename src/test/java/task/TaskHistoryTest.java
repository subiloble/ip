package task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskHistoryTest {
    private TaskHistory taskHistory;
    private TaskList initialTasks;

    @BeforeEach
    void setUp() {
        initialTasks = new TaskList();
        initialTasks.add(new ToDo("First task"));
        taskHistory = new TaskHistory(initialTasks);
    }

    @Test
    void testSaveHistory() {
        TaskList newTasks = (TaskList) initialTasks.clone();
        newTasks.add(new ToDo("Second task"));
        taskHistory.saveHistory(newTasks);
        assertTrue(taskHistory.canDeleteHistory(), "History should contain more than one entry after saving");
    }

    @Test
    void testDeleteHistory() {
        TaskList newTasks = (TaskList) initialTasks.clone();
        newTasks.add(new ToDo("Second task"));
        taskHistory.saveHistory(newTasks);

        TaskList revertedTasks = taskHistory.deleteHistory();
        assertEquals(1, revertedTasks.size(), "Deleting history should revert to the initial task list");
    }

    @Test
    void testCanDeleteHistory() {
        assertFalse(taskHistory.canDeleteHistory(), "Initially, history should not be deletable");
        TaskList newTasks = (TaskList) initialTasks.clone();
        newTasks.add(new ToDo("Second task"));
        taskHistory.saveHistory(newTasks);
        assertTrue(taskHistory.canDeleteHistory(), "After adding history, deletion should be possible");
    }
}