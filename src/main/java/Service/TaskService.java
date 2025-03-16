package Service;


import Model.Task;
import Model.TaskStatus;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static Model.TaskStatus.*;
import static Service.StatusMessages.*;

public class TaskService {
      private final TaskRepository taskRepository = new TaskRepository();

      public boolean isTaskListEmpty() {
            return taskRepository.getAllTasks().isEmpty();
      }

      public String getAllTasks() {
            return (taskRepository.getAllTasks() == null) ?
                    EMPTY_SET.getMessage() : taskRepository.getAllTasks().toString();

      }

      public String addTask(Task task) {
            return (taskRepository.addTask(task) == TaskOperationStatus.SUCCESS) ?
                    TASK_SUCCESSFULUlLY_ADDED.getMessage() : StatusMessages.TASK_ADDING_FAILED.getMessage();
      }

      public String removeTask(String taskName) {
            return (taskRepository.findAndRemoveTask(taskName) == TaskOperationStatus.SUCCESS) ?
                    TASK_SUCCESSFULLY_DELETED.getMessage() : StatusMessages.TASk_DELETION_FAILED.getMessage();
      }

      public boolean isTaskExist(String name) {
            return taskRepository.containsTask(name);
      }

      public String editTaskName(String nameOfTaskToEdit, String newName) {
            if (taskRepository.containsTask(newName)) return StatusMessages.TASK_ADDING_FAILED.getMessage();
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setName(newName);
            return TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskStatus(String nameOfTaskToEdit, TaskStatus newTaskStatus) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setStatus(newTaskStatus);
            return TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskDescription(String nameOfTaskToEdit, String newDescription) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            if (newDescription.isBlank()) taskToEdit.setDescription("No description");
            else taskToEdit.setDescription(newDescription);
            return TASK_SUCCESSFULLY_EDITED.getMessage();
      }
      
      public String editTaskDeadline(String nameOfTaskToEdit, LocalDateTime newDeadLine) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setDeadline(newDeadLine);
            return TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String getFilteredTasks(TaskStatus statusToFilter) {
            Set<Task> buff = taskRepository.getAllTasks().stream().filter(task -> task.getStatus() == statusToFilter).collect(Collectors.toSet());
            return (!buff.isEmpty()) ? buff.toString() : "";
      }
}

@Getter
enum StatusMessages {
      TASK_SUCCESSFULUlLY_ADDED("Task successfully added!"),
      TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
      TASK_SUCCESSFULLY_EDITED("Task successfully edited!"),
      TASk_DELETION_FAILED("Task deletion failed, no such task!"),
      TASK_ADDING_FAILED("Task with that name is already exists!"),
      EMPTY_SET("Tasks list is empty!");
      private final String message;
      
      StatusMessages(String message) {
          this.message = message;
      }
}
