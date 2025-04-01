package Exceptions;

public class NoSuchTaskException extends RuntimeException {
    public NoSuchTaskException() {
        super("Task deletion failed, no such task!");
    }
}
