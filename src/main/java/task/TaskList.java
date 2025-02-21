package task;

import java.util.ArrayList;

/** List of tasks. */

public class TaskList extends ArrayList<Task> {
    public TaskList(ArrayList<Task> tasks) {
        super(tasks);
    }
    public TaskList() { super(); }
}
