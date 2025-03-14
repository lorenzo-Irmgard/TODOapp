import Controller.AppController;
import Model.TaskStatus;
import Repository.TaskRepository;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.mainLoop();
    }
}

