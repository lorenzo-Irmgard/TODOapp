package Controller;

import Repository.TaskRepository;

import java.util.Scanner;

public class AppController {
    public void mainLoop() {
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();
        TaskRepository taskRepository = new TaskRepository();
        while(true) {
            consolePrinter.printMenu();

        }
    }
}

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

    public int menuOption() {
        while (true) {
            String option = scan.nextLine().trim();
            switch (option) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
                case "6":
                    return 6;
                case "7":
                    return 7;
                default:
                    System.out.println("Invalid input. Please enter a option number.");
            }
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
