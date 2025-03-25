package Controller;

import Model.Task;
import Model.TaskStatus;
import Service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static Controller.MainMenuOptions.*;
import static Controller.TaskEditingMenuOptions.*;

public class AppController {
    private final TaskService taskService = new TaskService();
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
            return (taskList.isEmpty()) ? "Tasks list is empty!" : taskList.toString();
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
            return (filteredTasksList.isEmpty()) ? "Tasks list is empty!" : filteredTasksList.toString();
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
        if (userInput == SortingMenuOptions.SORT_BY_STATUS.getOptionInNumberFormat()) sortedTasksList = taskService.getSortedByStatusTaskList();
        else sortedTasksList = taskService.getSortedByDeadlineTaskList();
        return (sortedTasksList.isEmpty()) ? "Tasks list is empty!" : sortedTasksList.toString();
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

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

     int userChoiceInMenu(List<String> possibleOptions) {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (possibleOptions.contains(userInput)) return Integer.parseInt(userInput);
            System.out.println("Invalid input. Please, enter userInput number from menu");
        }
    }

    int getUserChoiceForTaskFieldsToEdit() {
        System.out.println("What do you want to edit?");
        ConsolePrinter.printTaskEditingOptions();
        return userChoiceInMenu(TaskEditingMenuOptions.getPossibleOptions());
    }

    String getTaskNameFromUser() {
        while (true) {
            String userInput = scan.nextLine();
            if (!userInput.isEmpty()) return userInput;
            System.out.println("Invalid input. Please, enter task name");
        }
    }

    String getTaskDescriptionFromUser() {
        System.out.println("Enter your task description or leave the field empty for no description:");
        String userInput = scan.nextLine();
        if (!userInput.isBlank()) return userInput;
        return "No description";
    }

    LocalDateTime confirmationForGetTaskDeadlineFromUser() {
        System.out.println("Do you want add deadline to your task? (y/n)");
        if (yesOrNoCheck()) {
            return getTaskDeadlineFromUser();
        }
        return null;
    }

    LocalDateTime getTaskDeadlineFromUser() {
        System.out.println("Enter your task deadline in format 'yyyy-MM-ddTHH:mm':");
        while (true) {
            String userInput = scan.nextLine();
            try {
                LocalDateTime deadline = LocalDateTime.parse(userInput);
                if (deadline.isAfter(LocalDateTime.now())) return deadline;
                System.out.println("You entered deadline in the past. Please, enter correct deadline");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please, enter task deadline");
            }
        }
    }

    boolean yesOrNoCheck() {
        while (true) {
            String userInput = scan.nextLine().toLowerCase();
            if (userInput.equalsIgnoreCase("y")) return true;
            if (userInput.equalsIgnoreCase("n")) return false;
            System.out.println("Invalid input. Please, enter 'y' or 'n'");
        }
    }
}
class ConsolePrinter {

    @RequiredArgsConstructor
    @Getter
    enum MessageTypeForUserInputTaskName {
        ADD_NEW_TASK("Enter name for your new task:"),
        DELETE_TASK("Enter name of task you want to delete:"),
        EDIT_TASK("Enter name of task you want to edit"),
        RENAME_TASK("Enter a new name for your task:");
        private final String message;
    }

    public static void printMainMenu() {
        System.out.println("Choose your option and enter it's number:");
        System.out.println("""
                1. List tasks\
                
                2. Add task\
                
                3. Remove task\
                
                4. Edit task\
                
                5. Filter tasks by status\
                
                6. Sort tasks\
                
                7. Exit""");
    }

    public static void printTaskEditingOptions() {
        System.out.println("""
            1. Edit task name\
            
            2. Edit task status\
            
            3. Edit task description\
            
            4. Edit task deadline\
            
            5. Edit all task fields\
            
            6. Stop editing task""");
    }

    public static void printTaskStatusOptions() {
        System.out.println("""
                1. TODO\
                
                2. IN PROGRESS\
                
                3. DONE""");
    }

    public static void printSortingMenuOptions() {
        System.out.println("""
                1. Sort by task status\
                
                2. Sort by task deadline""");
    }
}

@Getter
@RequiredArgsConstructor
enum MainMenuOptions {
    LIST_ALL_TASKS(1),
    ADD(2),
    DELETE(3),
    EDIT(4),
    FILTER(5),
    SORT(6),
    EXIT(7);

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
}

@Getter
@RequiredArgsConstructor
enum SortingMenuOptions {
    SORT_BY_STATUS(1),
    SORT_BY_DEADLINE(2);

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2");
}

@RequiredArgsConstructor
@Getter
enum TaskEditingMenuOptions {
    EDIT_NAME(1),
    EDIT_STATUS(2),
    EDIT_DESCRIPTION(3),
    EDIT_DEADLINE(4),
    EDIT_ALL_FIELDS(5),
    EXIT_EDITING_TASK(6);

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6");
}


