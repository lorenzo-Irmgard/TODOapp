package Repository;

import Model.Task;

import java.util.LinkedHashMap;
import java.util.Map;


public class TaskRepository {
    private final Map<String, Task> tasks = new LinkedHashMap<>();

    public TaskOperationStatus addTask(Task task) {
        if(tasks.containsKey(task.getName())) {
            return TaskOperationStatus.TASK_ALREADY_EXISTS;
        }
        tasks.put(task.getName(), task);
        return TaskOperationStatus.SUCCESS;
    }

    public TaskOperationStatus findAndRemoveTask(String taskName) {
        return (tasks.remove(taskName) == null) ? TaskOperationStatus.TASK_NOT_FOUND : TaskOperationStatus.SUCCESS;
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
}
