package Repository;

import Model.Task;

import java.util.LinkedHashSet;
import java.util.Set;


public class TaskRepository {
    private final Set<Task> tasks = new LinkedHashSet<>();

    public TaskOperationStatus addTask(Task task) {
        if(tasks.add(task)) return  TaskOperationStatus.SUCCESS;
        return TaskOperationStatus.TASK_ALREADY_EXISTS;
    }

    public TaskOperationStatus removeTask(Task task) {
    }

    public Set<Task> getTasks() {
        if(!tasks.isEmpty()) return tasks;
        return null;
    }
}
