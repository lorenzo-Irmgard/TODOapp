package Controller;

import Model.Task;
import Service.TaskService;

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
            String taskName = inputScanAndValidate.userInputTaskName();
            String taskDescription = inputScanAndValidate.userInputTaskDescription();
            LocalDateTime deadline = inputScanAndValidate.userInputTaskDeadline();
            if (deadline != null) {
                result = taskService.addTask(new Task(taskName, taskDescription, deadline));
            } else {
                result = taskService.addTask(new Task(taskName, taskDescription));
            }
        }
        if (userInput == MenuOptions.DELETE.getOptionInNumberFormat()) {
            String taskName = inputScanAndValidate.userInputTaskName();
            result = taskService.removeTask(taskName);
        }
        if (userInput == MenuOptions.EDIT.getOptionInNumberFormat()) {
            String taskName = inputScanAndValidate.userInputTaskName();
            String taskDescription = inputScanAndValidate.userInputTaskDescription();
            LocalDateTime deadline = inputScanAndValidate.userInputTaskDeadline();
            if (deadline != null) {
                result = taskService.editTask(new Task(taskName, taskDescription, deadline));
            } else {
                result = taskService.editTask(new Task(taskName, taskDescription));
            }
        }
        return result;
    }
}

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

     int userChoiceInMenu() {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (MenuOptions.getPossibleOptions().contains(userInput)) return Integer.parseInt(userInput);
            System.out.println("Invalid input. Please, enter userInput number from menu");
        }
    }

    String userInputTaskName() {
        System.out.println("Enter your task name:");
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

    LocalDateTime userInputTaskDeadline() {
        System.out.println("Do you want add deadline to your task? (y/n)");
        if (yesOrNoCheck()) {
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
        return null;
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


