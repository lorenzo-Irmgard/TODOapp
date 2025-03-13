package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Task {
    private String name;
    private TaskStatus status;
    private String description;
    private LocalDateTime deadline;

    public Task(String name, TaskStatus status, LocalDateTime deadline) {
        this.name = name;
        this.status = status;
        this.deadline = deadline;
        this.description = "No description";
    }

    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, TaskStatus status) {
        this.name = name;
        this.status = status;
        this.description = "No description";
    }
}
