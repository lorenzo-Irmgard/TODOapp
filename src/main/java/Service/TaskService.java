package Service;


import Model.Task;
import Model.TaskStatus;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import lombok.Getter;

import java.time.LocalDateTime;

import static Model.TaskStatus.*;

public class TaskService {
      private final TaskRepository taskRepository = new TaskRepository();

      public String getAllTasks() {
            return (taskRepository.getAllTasks() == null) ?
                    StatusMessages.EMPTY_SET.getMessage() : taskRepository.getAllTasks().toString();

      }

      public String addTask(Task task) {
            return (taskRepository.addTask(task) == TaskOperationStatus.SUCCESS) ?
                    StatusMessages.TASK_SUCCESSFULUlLY_ADDED.getMessage() : StatusMessages.TASK_ADDING_FAILED.getMessage();
      }

      public String removeTask(String taskName) {
            return (taskRepository.findAndRemoveTask(taskName) == TaskOperationStatus.SUCCESS) ?
                    StatusMessages.TASK_SUCCESSFULLY_DELETED.getMessage() : StatusMessages.TASk_DELETION_FAILED.getMessage();
      }

      public boolean isTaskExist(String name) {
            return taskRepository.containsTask(name);
      }

      public String editTaskName(String nameOfTaskToEdit, String newName) {
            if (taskRepository.containsTask(newName)) return StatusMessages.TASK_ADDING_FAILED.getMessage();
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setName(newName);
            return StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskStatus(String nameOfTaskToEdit, int taskStatusInNumberFormat) {
            TaskStatus newStatus = TODO;
            if (taskStatusInNumberFormat == IN_PROGRESS.getOptionInNumberFormat()) newStatus = IN_PROGRESS;
            if (taskStatusInNumberFormat == DONE.getOptionInNumberFormat()) newStatus = DONE;
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setStatus(newStatus);
            return StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskDescription(String nameOfTaskToEdit, String newDescription) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            if (newDescription.isBlank()) taskToEdit.setDescription("No description");
            else taskToEdit.setDescription(newDescription);
            return StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }
      
      public String editTaskDeadline(String nameOfTaskToEdit, LocalDateTime newDeadLine) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setDeadline(newDeadLine);
            return StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
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
