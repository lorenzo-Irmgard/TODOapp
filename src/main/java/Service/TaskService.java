package Service;


import Model.Task;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import lombok.Getter;

public class TaskService {
      private final TaskRepository taskRepository = new TaskRepository();

      public String getTasksList() {
            return (taskRepository.getTasks() == null) ?
                    StatusMessages.EMPTY_SET.getMessage() : taskRepository.getTasks().toString();

      }

      public String addTask(Task task) {
            return (taskRepository.addTask(task) == TaskOperationStatus.SUCCESS) ?
                    StatusMessages.TASK_SUCCESSFULUlLY_ADDED.getMessage() : StatusMessages.TASK_ADDING_FAILED.getMessage();
      }

      public String removeTask(Task task) {

      }
}

@Getter
enum StatusMessages {
      TASK_SUCCESSFULUlLY_ADDED("Task successfully added!"),
      TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
      TASK_SUCCESSFULLY_EDITED("Task successfully edited!"),
      TAS_DELETION_FAILED("Task deletion failed, no such task!"),
      TASK_EDITING_FAILED("Task editing failed, no such task!"),
      TASK_ADDING_FAILED("Task adding failed, this task already exists!"),
      EMPTY_SET("Tasks list is empty!");
      private final String message;
      
      StatusMessages(String message) {
          this.message = message;
      }
}
