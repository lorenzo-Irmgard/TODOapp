package Controller;

import Model.Task;
import Service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Controller.ConsolePrinter.printMessageForTaskNameScan;
import static Controller.MainMenuOptions.*;
import static Controller.TaskEditingMenuOptions.EDIT_NAME;

public class AppController {
    private final TaskService taskService = new TaskService();
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();

    public void mainLoop() {
        while(true) {
            ConsolePrinter.printMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu(MainMenuOptions.getPossibleOptions());
            if (userInput == MainMenuOptions.EXIT.getOptionInNumberFormat()) break;
            System.out.println(chooseServiceMethodBasedOnUserInput(userInput));

        }
    }
    private String chooseServiceMethodBasedOnUserInput(int userInput) {
        String result = "";
        if (userInput == LIST_ALL_TASKS.getOptionInNumberFormat()) {
            result = taskService.getAllTasks();
        }
        if (userInput == ADD.getOptionInNumberFormat()) {
            printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.ADD_NEW_TASK);
            result = taskService.addTask(formNewTaskObject());
        }
        if (userInput == DELETE.getOptionInNumberFormat()) {
            printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.DELETE_TASK);
            result = taskService.removeTask(inputScanAndValidate.getTaskNameFromUser());
        }
        if (userInput == EDIT.getOptionInNumberFormat()) {
            printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.EDIT_TASK);
            String taskToEditName = inputScanAndValidate.getTaskNameFromUser();
            if (!taskService.isTaskExist(taskToEditName)) return "No such task!";
            result = chooseServiceMethodForEditing(taskToEditName, inputScanAndValidate.getUserChoiceForTaskFieldsToEdit());
        }
        return result;
    }

    private Task formNewTaskObject() {
        String taskName = inputScanAndValidate.getTaskNameFromUser();
        String taskDescription = inputScanAndValidate.getTaskDescriptionFromUser();
        LocalDateTime deadline = inputScanAndValidate.confirmationForGetTaskDeadlineFromUser();
        if (deadline != null) return new Task(taskName, taskDescription, deadline);
        return new Task(taskName, taskDescription);
    }

    private String chooseServiceMethodForEditing(String nameOfTaskToEdit, int userInput) {
        String result = "";
        if (userInput == EDIT_NAME.getOptionInNumberFormat()) {
            printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.RENAME_TASK);
            result = taskService.editTaskName(nameOfTaskToEdit, inputScanAndValidate.getTaskNameFromUser());
        }
//        if (userInput == TaskEditingMenuOptions.EDIT_DESCRIPTION.getOptionInNumberFormat()) {
//            result = taskService.editTaskDescription(inputScanAndValidate.getTaskDescriptionFromUser());
//        }
//        if (userInput == TaskEditingMenuOptions.DEADLINE.getOptionInNumberFormat()) {
//            result = taskService.editTaskDeadline(inputScanAndValidate.confirmationForGetTaskDeadlineFromUser());
//        }
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
        System.out.println("Enter your task description:");
        String userInput = scan.nextLine();
        if (!userInput.isEmpty()) return userInput;
        return "No description";
    }

    LocalDateTime confirmationForGetTaskDeadlineFromUser() {
        System.out.println("Do you want add deadline to your task? (y/n)");
        if (yesOrNoCheck()) {
            return getTaskDeadlineFromUser();
        }
        return null;
    }

    private LocalDateTime getTaskDeadlineFromUser() {
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

    public static void printMenu() {
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

    public static void printMessageForTaskNameScan(MessageTypeForUserInputTaskName messageType) {
        System.out.println(messageType.message);
    }

    public static void printTaskEditingOptions() {
        System.out.println("""
            1. Edit task name\
            
            2. Edit task description\
            
            3. Edit task deadline\
            
            4. Edit all task fields\
            
            5. Stop editing task""");
    }
}

@Getter
enum MainMenuOptions {
    LIST_ALL_TASKS(1),
    ADD(2),
    DELETE(3),
    EDIT(4),
    FILTER(5),
    SORT(6),
    EXIT(7);

    private final int optionInNumberFormat;
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6", "7");

    MainMenuOptions(int optionInNumberFormat) {
        this.optionInNumberFormat = optionInNumberFormat;
    }

    static List<String> getPossibleOptions() {
        return possibleOptions;
    }
}

@Getter
enum TaskEditingMenuOptions {
    EDIT_NAME(1),
    EDIT_DESCRIPTION(2),
    EDIT_DEADLINE(3),
    EDIT_STATUS(4),
    EDIT_ALL_FIELDS(5),
    EXIT_EDITING_TASK(6);

    private final int optionInNumberFormat;
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6");

    TaskEditingMenuOptions(int optionInNumberFormat) {
        this.optionInNumberFormat = optionInNumberFormat;
    }

    static List<String> getPossibleOptions() {
        return possibleOptions;
    }
}


