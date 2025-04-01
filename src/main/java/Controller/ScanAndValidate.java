package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ScanAndValidate {
    private final Scanner scan = new Scanner(System.in);

    int choiceInMenu(List<String> possibleOptions) {
        while (true) {
            String userInput = scan.nextLine().trim();
            if (possibleOptions.contains(userInput)) {
                return Integer.parseInt(userInput);
            }
            System.out.println("Invalid input. Please, enter userInput number from menu");
        }
    }

    int taskFieldsToEdit() {
        System.out.println("What do you want to edit?");
        ConsolePrinter.printTaskEditingOptions();
        return choiceInMenu(TaskEditingMenuOptions.getPossibleOptions());
    }

    String taskName() {
        while (true) {
            String userInput = scan.nextLine();
            if (!userInput.isEmpty()) {
                return userInput;
            }
            System.out.println("Invalid input. Please, enter task name");
        }
    }

    String taskDescription() {
        System.out.println("Enter your task description or leave the field empty for no description:");
        String userInput = scan.nextLine();
        if (!userInput.isBlank()) {
            return userInput;
        }
        return "No description";
    }

    LocalDateTime taskDeadline() {
        System.out.println("Enter your task deadline in format 'yyyy-MM-ddTHH:mm':");
        while (true) {
            String userInput = scan.nextLine();
            try {
                LocalDateTime deadline = LocalDateTime.parse(userInput);
                if (deadline.isAfter(LocalDateTime.now())) {
                    return deadline;
                }
                System.out.println("You entered deadline in the past. Please, enter correct deadline");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please, enter task deadline");
            }
        }
    }
}
