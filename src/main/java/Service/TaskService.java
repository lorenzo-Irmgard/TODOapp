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


public class TaskService {
      private final TaskRepository taskRepository = new TaskRepository();

      public Set<Task> getAllTasks() {
            return taskRepository.getAllTasks();
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
            if (taskRepository.containsTask(newName)) return Service.StatusMessages.TASK_ADDING_FAILED.getMessage();
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setName(newName);
            return Service.StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskStatus(String nameOfTaskToEdit, TaskStatus newTaskStatus) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setStatus(newTaskStatus);
            return Service.StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public String editTaskDescription(String nameOfTaskToEdit, String newDescription) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            if (newDescription.isBlank()) taskToEdit.setDescription("No description");
            else taskToEdit.setDescription(newDescription);
            return Service.StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }
      
      public String editTaskDeadline(String nameOfTaskToEdit, LocalDateTime newDeadLine) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setDeadline(newDeadLine);
            return Service.StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
      }

      public Set<Task> getFilteredTasks(TaskStatus statusToFilter) {
            return taskRepository.getAllTasks().stream().filter(task -> task.getStatus() == statusToFilter).collect(Collectors.toSet());
      }

      public Set<Task> getSortedByStatusTaskList() {
            return taskRepository.getAllTasks().stream().sorted((o1, o2) -> {
                  if (o1.getStatus().getOptionInNumberFormat() == o2.getStatus().getOptionInNumberFormat()) return 0;
                  return (o1.getStatus().getOptionInNumberFormat() > o2.getStatus().getOptionInNumberFormat()) ? 1 : -1;
            }).collect(Collectors.toCollection(LinkedHashSet::new));
      }

      public Set<Task> getSortedByDeadlineTaskList() {
            return taskRepository.getAllTasks().stream().sorted((o1, o2) -> {
                  if (o1.getDeadline().isEqual(o2.getDeadline())) return 0;
                  return (o1.getDeadline().isAfter(o2.getDeadline())) ? 1 : -1;
            }).collect(Collectors.toCollection(LinkedHashSet::new));
      }
}

@Getter
enum StatusMessages {
      TASK_SUCCESSFULUlLY_ADDED("Task successfully added!"),
      TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
      TASK_SUCCESSFULLY_EDITED("Task successfully edited!"),
      TASk_DELETION_FAILED("Task deletion failed, no such task!"),
      TASK_ADDING_FAILED("Task with that name is already exists!");
      private final String message;

      StatusMessages(String message) {
            this.message = message;
      }
}
