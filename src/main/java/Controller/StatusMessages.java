package Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusMessages {
    TASK_SUCCESSFULLY_ADDED("Task successfully added!"),
    TASK_SUCCESSFULLY_DELETED("Task successfully deleted!"),
    TASK_SUCCESSFULLY_EDITED("Task successfully edited!");
    private final String message;
}
