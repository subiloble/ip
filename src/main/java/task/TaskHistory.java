package task;

import java.util.ArrayList;

/** Task history. */

public class TaskHistory {
    private final ArrayList<TaskList> history;

    public TaskHistory(TaskList tasks) {
        ArrayList<TaskList> temp = new ArrayList<TaskList>();
        temp.add(tasks);
        this.history = temp;
    }

    public void saveHistory(TaskList tasks) {
        this.history.add((TaskList) tasks.clone());
    }

    public TaskList deleteHistory() {
        this.history.remove(this.history.size()-1);
        return this.history.get(this.history.size()-1);
    }

    public Boolean canDeleteHistory() {
        return this.history.size() > 1;
    }
}
