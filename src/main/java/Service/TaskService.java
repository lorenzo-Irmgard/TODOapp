package Service;


import Model.Task;
import Model.TaskStatus;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

import static Service.StatusMessages.*;

@RequiredArgsConstructor
public class TaskService {
      private final TaskRepository taskRepository;

      public String getAllTasks() {
            return taskRepository.getAllTasks()
                    .values()
                    .toString();
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
            if (taskRepository.containsTask(newName)) return Service.StatusMessages.TASK_ADDING_FAILED.getMessage();
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
            return taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .filter(task -> task.getStatus() == statusToFilter)
                    .toList()
                    .toString();
      }

      public String getTaskListSortedByStatus() {
            return taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(task -> task.getStatus().getNumberFormat()))
                    .toList()
                    .toString();
      }

      public String getTaskListSortedByDeadline() {
            return taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(Task::getDeadline))
                    .toList()
                    .toString();
      }
}
