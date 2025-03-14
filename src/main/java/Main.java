import Model.TaskStatus;
import Repository.TaskRepository;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepository();
        taskRepository.addTask(new Model.Task("Task 1", "Description 1",
                               LocalDateTime.of(2025, 3, 15, 14, 0)));
        taskRepository.addTask(new Model.Task("Task 2", "Description 2"));
        taskRepository.addTask(new Model.Task("Task 3", LocalDateTime.of(2025, 3, 16, 14, 0)));
        taskRepository.addTask(new Model.Task("Task 4"));

        System.out.println(taskRepository.getTasks());
    }
}

