package Controller;

import Model.Task;
import Service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AppController {
    private final TaskService taskService = new TaskService();
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();
    public void mainLoop() {
        ConsolePrinter consolePrinter = new ConsolePrinter();


        while(true) {
            consolePrinter.printMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu();
            if (userInput == MenuOptions.EXIT.getOptionInNumberFormat()) break;
            System.out.println(serviceMethodSelectionBasedOnUserInput(userInput));

        }
    }
    private String serviceMethodSelectionBasedOnUserInput(int userInput) {
        String result = "";
        if (userInput == MenuOptions.LIST.getOptionInNumberFormat()) {
            result = taskService.getTasksList();
        }
        if (userInput == MenuOptions.ADD.getOptionInNumberFormat()) {
            String taskName = inputScanAndValidate.userInputTaskName(InputScanAndValidate.MessageTypeForUserInputTaskName.ADD_NEW_TASK);
            String taskDescription = inputScanAndValidate.userInputTaskDescription();
            LocalDateTime deadline = inputScanAndValidate.checkForUserInputTaskDeadline();
            if (deadline != null) {
                result = taskService.addTaskToList(new Task(taskName, taskDescription, deadline));
            } else {
                result = taskService.addTaskToList(new Task(taskName, taskDescription));
            }
        }
        if (userInput == MenuOptions.DELETE.getOptionInNumberFormat()) {
            String taskName = inputScanAndValidate.userInputTaskName(InputScanAndValidate.MessageTypeForUserInputTaskName.DELETE_TASK);
            result = taskService.removeTaskFromList(taskName);
        }
        if (userInput == MenuOptions.EDIT.getOptionInNumberFormat()) {
            String taskName = inputScanAndValidate.userInputTaskName(InputScanAndValidate.MessageTypeForUserInputTaskName.EDIT_TASK);

        }
        return result;
    }
}

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

    @RequiredArgsConstructor
    @Getter
    enum MessageTypeForUserInputTaskName {
        ADD_NEW_TASK("Enter name for your new task:"),
        DELETE_TASK("Enter name of task you want to delete:"),
        EDIT_TASK("Enter name of task you want to edit"),
        RENAME_TASK("Enter a new name for your task:");
        private final String message;
    }
     int userChoiceInMenu() {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (MenuOptions.getPossibleOptions().contains(userInput)) return Integer.parseInt(userInput);
            System.out.println("Invalid input. Please, enter userInput number from menu");
        }
    }

    String selectTaskFieldsToEdit() {
        System.out.println("What do you want to edit");
    }

    String userInputTaskName(MessageTypeForUserInputTaskName workMode) {
        System.out.println(workMode.message);
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


    public void printMenu() {
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


}


