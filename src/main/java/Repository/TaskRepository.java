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

    public TaskOperationStatus findAndRemoveTask(String taskName) {
        tasks.stream().map(Task::getName).forEach(name -> tasks.removeIf(task -> name.equals(taskName)));
        return tasks.isEmpty() ? TaskOperationStatus.TASK_NOT_FOUND : TaskOperationStatus.SUCCESS;
    }

    public void removeTask(Task taskToDelete) {
        tasks.remove(taskToDelete);
    }

    public Task getTask(String taskName) {
        return tasks.stream().filter(task -> task.getName().equals(taskName)).findFirst().orElse(null);
    }


    public Set<Task> getTasks() {
        if(!tasks.isEmpty()) return tasks;
        return null;
    }
}
