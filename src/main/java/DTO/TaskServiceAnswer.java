package DTO;

import Model.Task;
import Repository.TaskOperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TaskServiceAnswer {
    private final TaskOperationStatus operationStatus;
    private final Set<Task> tasks;
    @Setter
    private String message;
}
