package Repository;

import Model.Task;

import java.util.LinkedHashMap;
import java.util.Map;


public class TaskRepository {
    private final Map<String, Task> tasks = new LinkedHashMap<>();

    public void addTask(Task task) {
        tasks.put(task.getName(), task);
    }

    public void replaceTask(String oldNameTask, Task task) {
        tasks.remove(oldNameTask);
        tasks.put(task.getName(), task);
    }

    public void removeTask(String taskName) {
        tasks.remove(taskName);
    }

    public Task getTask(String taskName) {
        return tasks.get(taskName);
    }

    public Map<String,Task> getAllTasks() {
        return tasks;
    }

    public boolean containsTask(String taskName) {
        return tasks.containsKey(taskName);
    }

    public boolean containsTask(Task task) {
        return tasks.containsValue(task);
    }
}
