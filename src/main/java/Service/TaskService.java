package Service;


import Exceptions.EmptyTasksListException;
import Exceptions.NoSuchTaskException;
import Exceptions.TaskAlreadyExistException;
import Model.Task;
import Model.TaskStatus;
import Repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class TaskService {
      private final TaskRepository taskRepository;

      public String getAllTasks() throws EmptyTasksListException {
            List<Task> tasksList = taskRepository.getAllTasks()
                    .values().stream().toList();
            if (tasksList.isEmpty()) {
                  throw new EmptyTasksListException();
            } else {
                  return tasksList.toString();
            }
      }

      public void addTask(Task task) throws TaskAlreadyExistException {
            if (taskRepository.containsTask(task)) {
                  throw new TaskAlreadyExistException();
            } else {
                  taskRepository.addTask(task);
            }
      }

      public void removeTask(String taskName) throws NoSuchTaskException {
            if (taskRepository.containsTask(taskName)) {
                  taskRepository.removeTask(taskName);
            } else {
                  throw new NoSuchTaskException();
            }
      }

      public boolean isTaskExist(String name) {
            return taskRepository.containsTask(name);
      }

      public void editTaskName(String nameOfTaskToEdit, String newName) throws TaskAlreadyExistException {
            if (taskRepository.containsTask(newName)) {
                  throw new TaskAlreadyExistException();
            } else {
                  Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
                  taskToEdit.setName(newName);
                  taskRepository.replaceTask(nameOfTaskToEdit, taskToEdit);
            }
      }

      public void editTaskStatus(String nameOfTaskToEdit, TaskStatus newTaskStatus) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setStatus(newTaskStatus);
      }

      public void editTaskDescription(String nameOfTaskToEdit, String newDescription) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            if (newDescription.isBlank()) {
                  taskToEdit.setDescription("No description");
            } else {
                  taskToEdit.setDescription(newDescription);
            }
      }
      
      public void editTaskDeadline(String nameOfTaskToEdit, LocalDateTime newDeadLine) {
            Task taskToEdit = taskRepository.getTask(nameOfTaskToEdit);
            taskToEdit.setDeadline(newDeadLine);
      }

      public String getFilteredTasks(TaskStatus statusToFilter) throws EmptyTasksListException {
            List<Task> tasksList = taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .filter(task -> task.getStatus() == statusToFilter)
                    .toList();
            if (tasksList.isEmpty()) {
                  throw new EmptyTasksListException();
            } else {
                  return tasksList.toString();
            }
      }

      public String getTaskListSortedByStatus() {
            List<Task> tasksList = taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(task -> task.getStatus().getNumberFormat()))
                    .toList();
            if (tasksList.isEmpty()) {
                  throw new EmptyTasksListException();
            } else {
                  return tasksList.toString();
            }
      }

      public String getTaskListSortedByDeadline() {
            List<Task> tasksList = taskRepository.getAllTasks()
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(Task::getDeadline))
                    .toList();
            if (tasksList.isEmpty()) {
                  throw new EmptyTasksListException();
            } else {
                  return tasksList.toString();
            }
      }
}
