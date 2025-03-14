package Controller;

import Service.TasksService;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AppController {
    public void mainLoop() {
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();
        TasksService tasksService = new TasksService();
        while(true) {
            consolePrinter.printMenu();
            int userInput = inputScanAndValidate.userChoiceInMenu();
            if (userInput == menuOptions.EXIT.getOptionInNumberFormat()) break;
        }
    }
}

class InputScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

    public int userChoiceInMenu() {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (menuOptions.getPossibleOptions().contains(userInput)) return Integer.parseInt(userInput);
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

@Getter
enum menuOptions {
    LIST(1),
    ADD(2),
    DELETE(3),
    EDIT(4),
    FILTER(5),
    SORT(6),
    EXIT(7);
    private final int optionInNumberFormat;
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6", "7");

     menuOptions(int optionInNumberFormat) {
        this.optionInNumberFormat = optionInNumberFormat;
     }

     static List<String> getPossibleOptions() {
         return possibleOptions;
     }
}
