package Service;


import Model.Task;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import lombok.Getter;

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

      public String editTaskName(String taskToEditName, String newName) {
            Task taskToEdit = taskRepository.getTask(taskToEditName);
            if (taskRepository.containsTask(newName)) return StatusMessages.TASK_ADDING_FAILED.getMessage();
            taskToEdit.setName(newName);
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
