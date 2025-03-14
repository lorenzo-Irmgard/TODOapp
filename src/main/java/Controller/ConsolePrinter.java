package Controller;

public class ConsolePrinter {
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
