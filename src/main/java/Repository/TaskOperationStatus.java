package Repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TaskOperationStatus {
    SUCCESS("Success!"),
    TASK_ALREADY_EXISTS("Task already exists!"),
//    TASK_SET_IS_EMPTY("There is no tasks in the list!"),
    TASK_NOT_FOUND("Task not found!");
    private final String message;

}
