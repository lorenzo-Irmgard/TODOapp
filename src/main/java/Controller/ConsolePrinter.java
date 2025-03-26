package Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ConsolePrinter {
    @RequiredArgsConstructor
    @Getter
    enum MessageTypeForUserInputTaskName {
        ADD_NEW_TASK("Enter name for your new task:"),
        DELETE_TASK("Enter name of task you want to delete:"),
        EDIT_TASK("Enter name of task you want to edit"),
        RENAME_TASK("Enter a new name for your task:");
        private final String message;
    }

    public static void printMainMenu() {
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

    public static void printTaskEditingOptions() {
        System.out.println("""
            1. Edit task name\
            
            2. Edit task status\
            
            3. Edit task description\
            
            4. Edit task deadline\
            
            5. Edit all task fields\
            
            6. Stop editing task""");
    }

    public static void printTaskStatusOptions() {
        System.out.println("""
                1. TODO\
                
                2. IN PROGRESS\
                
                3. DONE""");
    }

    public static void printSortingMenuOptions() {
        System.out.println("""
                1. Sort by task status\
                
                2. Sort by task deadline""");
    }
}
