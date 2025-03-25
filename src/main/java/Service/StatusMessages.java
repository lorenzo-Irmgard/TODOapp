package Service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusMessages {
    TASK_SUCCESSFULUlLY_ADDED("Task successfully added!"),
    TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
    TASK_SUCCESSFULLY_EDITED("Task successfully edited!"),
    TASk_DELETION_FAILED("Task deletion failed, no such task!"),
    TASK_ADDING_FAILED("Task with that name is already exists!"),
    EMPTY_SET("Tasks list is empty!");
    private final String message;
}
