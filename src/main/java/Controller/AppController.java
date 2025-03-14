package Controller;

import Repository.TaskRepository;

public class AppController {
    private final TaskRepository taskRepository = new TaskRepository();
    private final ConsolePrinter consolePrinter = new ConsolePrinter();
    private final InputScanAndValidate inputScanAndValidate = new InputScanAndValidate();

    public void mainLoop() {
        while(true) {
            consolePrinter.printMenu();
            System.out.println(inputScanAndValidate.menuOption());

        }
    }
}
