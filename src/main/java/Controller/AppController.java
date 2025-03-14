package Controller;

import Service.TaskService;
import java.util.Scanner;

public class AppController {
    public void mainLoop() {
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();
        TaskService taskService = new TaskService();
        while(true) {
            consolePrinter.printMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu();
            if (userInput == MenuOptions.EXIT.getOptionInNumberFormat()) break;
            taskService.processSelectedMenuOption(userInput);
        }
    }
}

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

    public int userChoiceInMenu() {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (MenuOptions.getPossibleOptions().contains(userInput)) return Integer.parseInt(userInput);
            System.out.println("Invalid input. Please, enter userInput number from menu");
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


