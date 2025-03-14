package Service;

import Controller.MenuOptions;
import DTO.TaskServiceAnswer;
import Repository.TaskRepository;

public class TaskService {
    private final TaskRepository taskRepository = new TaskRepository();

    public TaskServiceAnswer processSelectedMenuOption(int selectedOption) {
        if (selectedOption == MenuOptions.LIST.getOptionInNumberFormat()) {

        }
        if (selectedOption == MenuOptions.ADD.getOptionInNumberFormat()) {

        }
        if (selectedOption == MenuOptions.DELETE.getOptionInNumberFormat()) {

        }
        if (selectedOption == MenuOptions.EDIT.getOptionInNumberFormat()) {

        }
        if (selectedOption == MenuOptions.FILTER.getOptionInNumberFormat()) {

        }
        if (selectedOption == MenuOptions.SORT.getOptionInNumberFormat()) {

        }
    }
}
