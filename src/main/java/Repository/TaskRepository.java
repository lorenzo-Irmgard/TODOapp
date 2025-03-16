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
        Task taskToDelete = tasks.stream().filter(task -> task.getName().equals(taskName)).findFirst().orElse(null);
        return tasks.remove(taskToDelete) ? TaskOperationStatus.SUCCESS : TaskOperationStatus.TASK_NOT_FOUND;
    }

    public void findAndRemoveTask(Task taskToDelete) {
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
