package Controller;

import Model.Task;
import Model.TaskStatus;
import Repository.TaskRepository;
import Service.TaskService;

import java.time.LocalDateTime;
import java.util.Set;

import static Controller.MainMenuOptions.*;
import static Controller.TaskEditingMenuOptions.*;
import static Service.StatusMessages.EMPTY_SET;

public class AppController {
    private final TaskService taskService = new TaskService(new TaskRepository());
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();

    public void mainLoop() {
        while(true) {
            ConsolePrinter.printMainMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu(MainMenuOptions.getPossibleOptions());
            if (userInput == MainMenuOptions.EXIT.getOptionInNumberFormat()) break;
            System.out.println(chooseServiceMethodBasedOnUserInput(userInput));
        }
    }
    private String chooseServiceMethodBasedOnUserInput(int userInput) {
        if (userInput == LIST_ALL_TASKS.getOptionInNumberFormat()) {
            Set<Task> taskList = taskService.getAllTasks();
            return (taskList.isEmpty()) ? EMPTY_SET.getMessage() : taskList.toString();
        }
        if (userInput == ADD.getOptionInNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.ADD_NEW_TASK.getMessage());
            return taskService.addTask(formNewTaskObject());
        }
        if (userInput == DELETE.getOptionInNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.DELETE_TASK.getMessage());
            return taskService.removeTask(inputScanAndValidate.getTaskNameFromUser());
        }
        if (userInput == EDIT.getOptionInNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.EDIT_TASK.getMessage());
            String nameOfTaskToEdit = inputScanAndValidate.getTaskNameFromUser();
            if (!taskService.isTaskExist(nameOfTaskToEdit)) return "No such task!";
            return chooseServiceMethodForEditing(nameOfTaskToEdit, inputScanAndValidate.getUserChoiceForTaskFieldsToEdit());
        }
        if (userInput == FILTER.getOptionInNumberFormat()) {
            System.out.println("Select the status of which to filter");
            ConsolePrinter.printTaskStatusOptions();
            int userInputForFilter = inputScanAndValidate.userChoiceInMenu(TaskStatus.getPossibleOptions());
            Set<Task> filteredTasksList = taskService.getFilteredTasks(TaskStatus.convertFromNumberToStatus(userInputForFilter));
            return (filteredTasksList.isEmpty()) ? EMPTY_SET.getMessage() : filteredTasksList.toString();
        }
        if (userInput == SORT.getOptionInNumberFormat()) {
            System.out.println("Select which field to sort");
            ConsolePrinter.printSortingMenuOptions();
            return chooseServiceMethodForSorting(inputScanAndValidate.userChoiceInMenu(SortingMenuOptions.getPossibleOptions()));
        }
        return "No such option";
    }

    private Task formNewTaskObject() {
        String taskName = inputScanAndValidate.getTaskNameFromUser();
        String taskDescription = inputScanAndValidate.getTaskDescriptionFromUser();
        LocalDateTime deadline = inputScanAndValidate.confirmationForGetTaskDeadlineFromUser();
        if (deadline != null) return new Task(taskName, taskDescription, deadline);
        return new Task(taskName, taskDescription);
    }

    private String chooseServiceMethodForSorting(int userInput) {
        Set<Task> sortedTasksList;
        if (userInput == SortingMenuOptions.SORT_BY_STATUS.getOptionInNumberFormat()) sortedTasksList = taskService.getTaskListSortedByStatus();
        else sortedTasksList = taskService.getTaskListSortedByDeadline();
        return (sortedTasksList.isEmpty()) ? EMPTY_SET.getMessage() : sortedTasksList.toString();
    }

    private String chooseServiceMethodForEditing(String nameOfTaskToEdit, int userInput) {
        String result = "";
        if (userInput == EXIT_EDITING_TASK.getOptionInNumberFormat()) return result;
        boolean editAllFieldsOption = userInput == EDIT_ALL_FIELDS.getOptionInNumberFormat();
        if (userInput == EDIT_NAME.getOptionInNumberFormat() || editAllFieldsOption) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.RENAME_TASK.getMessage());
            String newName = inputScanAndValidate.getTaskNameFromUser();
            result = taskService.editTaskName(nameOfTaskToEdit, newName);
            nameOfTaskToEdit = newName;
        }
        if (userInput == EDIT_STATUS.getOptionInNumberFormat() || editAllFieldsOption) {
            System.out.println("Select new task status:");
            ConsolePrinter.printTaskStatusOptions();
            result = taskService.editTaskStatus(nameOfTaskToEdit, TaskStatus.convertFromNumberToStatus(inputScanAndValidate.userChoiceInMenu(TaskStatus.getPossibleOptions())));
        }
        if (userInput == TaskEditingMenuOptions.EDIT_DESCRIPTION.getOptionInNumberFormat() || editAllFieldsOption) {
            result = taskService.editTaskDescription(nameOfTaskToEdit, inputScanAndValidate.getTaskDescriptionFromUser());
        }
        if (userInput == EDIT_DEADLINE.getOptionInNumberFormat() || editAllFieldsOption) {
            result = taskService.editTaskDeadline(nameOfTaskToEdit, inputScanAndValidate.getTaskDeadlineFromUser());
        }
        return result;
    }
}



