package Exceptions;

public class EmptyTasksListException extends RuntimeException {
    public EmptyTasksListException() {
        super("Tasks list is empty!");
    }
}
