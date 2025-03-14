package Repository;

import DTO.TaskServiceAnswer;
import Model.Task;

import java.util.LinkedHashSet;
import java.util.Set;


public class TaskRepository {
    private final Set<Task> tasks = new LinkedHashSet<>();

    public TaskServiceAnswer addTask(Task task) {

        if(tasks.add(task)) return new TaskServiceAnswer(TaskOperationStatus.SUCCESS, null, "Задача успешно создана");
        return new TaskServiceAnswer(TaskOperationStatus.TASK_SET_IS_EMPTY, null, "Лист пуст");
    }

    public Set<Task> getTasks() {
        if(!tasks.isEmpty()) return tasks;
        return null;
    }
}
