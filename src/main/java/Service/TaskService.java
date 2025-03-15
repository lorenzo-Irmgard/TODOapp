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

      public String addTaskToList(Task task) {
            return (taskRepository.addTask(task) == TaskOperationStatus.SUCCESS) ?
                    StatusMessages.TASK_SUCCESSFULUlLY_ADDED.getMessage() : StatusMessages.TASK_ADDING_FAILED.getMessage();
      }

      public String removeTaskFromList(String taskName) {
            return (taskRepository.findAndRemoveTask(taskName) == TaskOperationStatus.SUCCESS) ?
                    StatusMessages.TASK_SUCCESSFULLY_DELETED.getMessage() : StatusMessages.TASk_DELETION_FAILED.getMessage();
      }

      public String editTaskName(String taskToEditName, String newName) {
            Task taskToEdit = taskRepository.getTask(taskToEditName); //TODO неправильно работает
            if (taskToEdit != null) {
                  Task renamedTask = taskToEdit;
                  renamedTask.setName(newName);
                  if (taskRepository.addTask(renamedTask) == TaskOperationStatus.SUCCESS) {
                        taskRepository.removeTask(taskToEdit);
                        return StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage();
                  } else {
                        return StatusMessages.TASK_ADDING_FAILED.getMessage();
                  }

            } else return StatusMessages.TASK_EDITING_FAILED.getMessage();
      }
}

@Getter
enum StatusMessages {
      TASK_SUCCESSFULUlLY_ADDED("Task successfully added!"),
      TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
      TASK_SUCCESSFULLY_EDITED("Task successfully edited!"),
      TASk_DELETION_FAILED("Task deletion failed, no such task!"),
      TASK_EDITING_FAILED("Task editing failed, no such task!"),
      TASK_ADDING_FAILED("Task with that name is already exists!"),
      EMPTY_SET("Tasks list is empty!");
      private final String message;
      
      StatusMessages(String message) {
          this.message = message;
      }
}
