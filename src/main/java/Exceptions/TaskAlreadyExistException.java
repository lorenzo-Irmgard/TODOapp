package Exceptions;

public class TaskAlreadyExistException extends RuntimeException {
    public TaskAlreadyExistException() {
        super("Task with that name is already exists!");
    }
}
