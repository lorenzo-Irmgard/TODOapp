package DTO;

import Model.Task;
import Repository.TaskOperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class TaskServiceAnswer {
    private final TaskOperationStatus operationStatus;
    private final Set<Task> tasks;
    private final String message;
}
