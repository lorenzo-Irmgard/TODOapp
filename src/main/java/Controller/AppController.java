package Controller;

import Repository.TaskRepository;

public class AppController {
    public void mainLoop() {
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();
        TaskRepository taskRepository = new TaskRepository();
        while(true) {
            consolePrinter.printMenu();
            System.out.println(inputScanAndValidate.menuOption());

        }
    }
}
