package Controller;

import Exceptions.EmptyTasksListException;
import Exceptions.NoSuchTaskException;
import Exceptions.TaskAlreadyExistException;
import Model.Task;
import Model.TaskStatus;
import Repository.TaskRepository;
import Service.TaskService;

import java.time.LocalDateTime;

import static Controller.MainMenuOptions.*;
import static Controller.TaskEditingMenuOptions.*;
import static Model.TaskStatus.convertNumberToStatus;
import static Controller.StatusMessages.*;


public class AppController {
    private final TaskService taskService = new TaskService(new TaskRepository());
    private final ScanAndValidate scanAndValidate = new ScanAndValidate();

    public void mainLoop() {
        while(true) {
            ConsolePrinter.printMainMenu();
            int userInput = scanAndValidate.choiceInMenu(MainMenuOptions.getPossibleOptions());
            if (userInput == EXIT.getNumberFormat()) break;
            System.out.println(chooseServiceMethodBasedOnUserInput(userInput));
        }
    }
    private String chooseServiceMethodBasedOnUserInput(int userInput) {
        if (userInput == LIST_ALL_TASKS.getNumberFormat()) {
            try {
                return taskService.getAllTasks();
            } catch (EmptyTasksListException e) {
                return e.getMessage();
            }
        }
        if (userInput == ADD.getNumberFormat()) {
            System.out.println(ConsolePrinter.InputTaskNameMessage.ADD_NEW_TASK.getMessage());
            try {
                taskService.addTask(formNewTaskObject());
                return TASK_SUCCESSFULLY_ADDED.getMessage();
            } catch (TaskAlreadyExistException e) {
                return e.getMessage();
            }
        }
        if (userInput == DELETE.getNumberFormat()) {
            System.out.println(ConsolePrinter.InputTaskNameMessage.DELETE_TASK.getMessage());
            try {
                taskService.removeTask(scanAndValidate.taskName());
                return TASK_SUCCESSFULLY_DELETED.getMessage();
            } catch (NoSuchTaskException e) {
                return e.getMessage();
            }
        }
        if (userInput == EDIT.getNumberFormat()) {
            System.out.println(ConsolePrinter.InputTaskNameMessage.EDIT_TASK.getMessage());
            String nameOfTaskToEdit = scanAndValidate.taskName();
            if (!taskService.isTaskExist(nameOfTaskToEdit)) return "No such task!";
            return chooseServiceMethodForEditing(nameOfTaskToEdit, scanAndValidate.taskFieldsToEdit());
        }
        if (userInput == FILTER.getNumberFormat()) {
            System.out.println("Select the status of which to filter");
            ConsolePrinter.printTaskStatusOptions();
            int userInputForFilter = scanAndValidate.choiceInMenu(TaskStatus.getPossibleOptions());
            try {
                return taskService.getFilteredTasks(convertNumberToStatus(userInputForFilter));
            } catch (EmptyTasksListException e) {
                return e.getMessage();
            }
        }
        if (userInput == SORT.getNumberFormat()) {
            System.out.println("Select which field to sort");
            ConsolePrinter.printSortingMenuOptions();
            return chooseServiceMethodForSorting(scanAndValidate.choiceInMenu(SortingMenuOptions.getPossibleOptions()));
        }
        return "No such option";
    }

    private Task formNewTaskObject() {
        String taskName = scanAndValidate.taskName();
        String taskDescription = scanAndValidate.taskDescription();
        LocalDateTime deadline = scanAndValidate.taskDeadline();
        return new Task(taskName, taskDescription, deadline);
    }

    private String chooseServiceMethodForSorting(int userInput) {
        if (userInput == SortingMenuOptions.SORT_BY_STATUS.getOptionInNumberFormat()) {
            try {
                return taskService.getTaskListSortedByStatus();
            } catch (EmptyTasksListException e) {
                return e.getMessage();
            }
        } else {
            try {
                return taskService.getTaskListSortedByDeadline();
            } catch (EmptyTasksListException e) {
                return e.getMessage();
            }
        }
    }

    private String chooseServiceMethodForEditing(String nameOfTaskToEdit, int userInput) {
        if (userInput == EXIT_EDITING_TASK.getOptionInNumberFormat()) {
            return "";
        }
        boolean editAllFieldsOption = userInput == EDIT_ALL_FIELDS.getOptionInNumberFormat();
        if (userInput == EDIT_NAME.getOptionInNumberFormat() || editAllFieldsOption) {
            System.out.println(ConsolePrinter.InputTaskNameMessage.RENAME_TASK.getMessage());
            String newName = scanAndValidate.taskName();
            try {
                taskService.editTaskName(nameOfTaskToEdit, newName);
                nameOfTaskToEdit = newName;
            } catch (TaskAlreadyExistException e) {
                return e.getMessage();
            }
        }
        if (userInput == EDIT_STATUS.getOptionInNumberFormat() || editAllFieldsOption) {
            System.out.println("Select new task status:");
            ConsolePrinter.printTaskStatusOptions();
            taskService.editTaskStatus(nameOfTaskToEdit, convertNumberToStatus(scanAndValidate.choiceInMenu(TaskStatus.getPossibleOptions())));
        }
        if (userInput == TaskEditingMenuOptions.EDIT_DESCRIPTION.getOptionInNumberFormat() || editAllFieldsOption) {
            taskService.editTaskDescription(nameOfTaskToEdit, scanAndValidate.taskDescription());
        }
        if (userInput == EDIT_DEADLINE.getOptionInNumberFormat() || editAllFieldsOption) {
            taskService.editTaskDeadline(nameOfTaskToEdit, scanAndValidate.taskDeadline());
        }
        return TASK_SUCCESSFULLY_EDITED.getMessage();
    }
}



