package Repository;

import Model.Task;

import java.util.HashSet;
import java.util.Set;


public class TaskRepository {
    private final Set<Task> tasks = new HashSet<>();

    public TaskOperationStatus addTask(Task task) {
        if (tasks.contains(task)) {
            return TaskOperationStatus.TASK_ALREADY_EXISTS;
        } else {
            tasks.add(task);
            return TaskOperationStatus.SUCCESS;
        }
    }
}
