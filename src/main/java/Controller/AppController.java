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

public class AppController {
    private final TaskService taskService = new TaskService();
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();

    public void mainLoop() {
        while(true) {
            ConsolePrinter.printMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu(MainMenuOptions.getPossibleOptions());
            if (userInput == MainMenuOptions.EXIT.getOptionInNumberFormat()) break;
            System.out.println(serviceMethodSelectionBasedOnUserInput(userInput));

        }
    }
    private String serviceMethodSelectionBasedOnUserInput(int userInput) {
        String result = "";
        if (userInput == MainMenuOptions.LIST.getOptionInNumberFormat()) {
            result = taskService.getTasksList();
        }
        if (userInput == MainMenuOptions.ADD.getOptionInNumberFormat()) {
            ConsolePrinter.printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.ADD_NEW_TASK);
            String taskName = inputScanAndValidate.userInputTaskName();
            String taskDescription = inputScanAndValidate.userInputTaskDescription();
            LocalDateTime deadline = inputScanAndValidate.checkForUserInputTaskDeadline();
            if (deadline != null) {
                result = taskService.addTaskToList(new Task(taskName, taskDescription, deadline));
            } else {
                result = taskService.addTaskToList(new Task(taskName, taskDescription));
            }
        }
        if (userInput == MainMenuOptions.DELETE.getOptionInNumberFormat()) {
            ConsolePrinter.printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.DELETE_TASK);
            String taskName = inputScanAndValidate.userInputTaskName();
            result = taskService.removeTaskFromList(taskName);
        }
        if (userInput == MainMenuOptions.EDIT.getOptionInNumberFormat()) {
            ConsolePrinter.printMessageForTaskNameScan(ConsolePrinter.MessageTypeForUserInputTaskName.EDIT_TASK);
            String taskName = inputScanAndValidate.userInputTaskName();
            switch (inputScanAndValidate.selectTaskFieldsToEdit()) {

            }
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

    int selectTaskFieldsToEdit() {
        System.out.println("What do you want to edit?");
        ConsolePrinter.printTaskEditingOptions();
        return userChoiceInMenu(TaskEditingMenuOptions.getPossibleOptions());
    }

    String userInputTaskName() {
        while (true) {
            String userInput = scan.nextLine();
            if (!userInput.isEmpty()) return userInput;
            System.out.println("Invalid input. Please, enter task name");
        }
    }

    String userInputTaskDescription() {
        System.out.println("Enter your task description:");
        String userInput = scan.nextLine();
        if (!userInput.isEmpty()) return userInput;
        return "No description";
    }

    LocalDateTime checkForUserInputTaskDeadline() {
        System.out.println("Do you want add deadline to your task? (y/n)");
        if (yesOrNoCheck()) {
            return userInputTaskDeadline();
        }
        return null;
    }

    private LocalDateTime userInputTaskDeadline() {
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
    LIST(1),
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
    EDIT_ALL_FIELDS(4);

    private final int optionInNumberFormat;
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5");

    TaskEditingMenuOptions(int optionInNumberFormat) {
        this.optionInNumberFormat = optionInNumberFormat;
    }

    static List<String> getPossibleOptions() {
        return possibleOptions;
    }
}


