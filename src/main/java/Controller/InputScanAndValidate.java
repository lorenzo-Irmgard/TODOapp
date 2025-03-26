package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InputScanAndValidate {
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
