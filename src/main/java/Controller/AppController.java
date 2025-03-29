package Controller;

import Model.Task;
import Model.TaskStatus;
import Repository.TaskRepository;
import Service.TaskService;

import java.time.LocalDateTime;

import static Controller.MainMenuOptions.*;
import static Controller.TaskEditingMenuOptions.*;


public class AppController {
    private final TaskService taskService = new TaskService(new TaskRepository());
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();

    public void mainLoop() {
        while(true) {
            ConsolePrinter.printMainMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu(MainMenuOptions.getPossibleOptions());
            if (userInput == EXIT.getNumberFormat()) break;
            System.out.println(chooseServiceMethodBasedOnUserInput(userInput));
        }
    }
    private String chooseServiceMethodBasedOnUserInput(int userInput) {
        if (userInput == LIST_ALL_TASKS.getNumberFormat()) {
            return taskService.getAllTasks();
        }
        if (userInput == ADD.getNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.ADD_NEW_TASK.getMessage());
            return taskService.addTask(formNewTaskObject());
        }
        if (userInput == DELETE.getNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.DELETE_TASK.getMessage());
            return taskService.removeTask(inputScanAndValidate.getTaskNameFromUser());
        }
        if (userInput == EDIT.getNumberFormat()) {
            System.out.println(ConsolePrinter.MessageTypeForUserInputTaskName.EDIT_TASK.getMessage());
            String nameOfTaskToEdit = inputScanAndValidate.getTaskNameFromUser();
            if (!taskService.isTaskExist(nameOfTaskToEdit)) return "No such task!";
            return chooseServiceMethodForEditing(nameOfTaskToEdit, inputScanAndValidate.getUserChoiceForTaskFieldsToEdit());
        }
        if (userInput == FILTER.getNumberFormat()) {
            System.out.println("Select the status of which to filter");
            ConsolePrinter.printTaskStatusOptions();
            int userInputForFilter = inputScanAndValidate.userChoiceInMenu(TaskStatus.getPossibleOptions());
            return taskService.getFilteredTasks(TaskStatus.convertFromNumberToStatus(userInputForFilter));
        }
        if (userInput == SORT.getNumberFormat()) {
            System.out.println("Select which field to sort");
            ConsolePrinter.printSortingMenuOptions();
            return chooseServiceMethodForSorting(inputScanAndValidate.userChoiceInMenu(SortingMenuOptions.getPossibleOptions()));
        }
        return "No such option";
    }

    private Task formNewTaskObject() {
        String taskName = inputScanAndValidate.getTaskNameFromUser();
        String taskDescription = inputScanAndValidate.getTaskDescriptionFromUser();
        LocalDateTime deadline = inputScanAndValidate.getTaskDeadlineFromUser();
        return new Task(taskName, taskDescription, deadline);
    }

    private String chooseServiceMethodForSorting(int userInput) {
        if (userInput == SortingMenuOptions.SORT_BY_STATUS.getOptionInNumberFormat()) {
            return taskService.getTaskListSortedByStatus();
        } else {
            return taskService.getTaskListSortedByDeadline();
        }
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



