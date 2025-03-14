package Controller;

import java.util.Scanner;

public class InputScanAndValidate {
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
