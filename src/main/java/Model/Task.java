package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.HTMLDocument;
import java.time.LocalDateTime;

@Setter
@Getter
public class Task {
    private String name;
    private TaskStatus status;
    private String description;
    private LocalDateTime deadline;

    public Task(String name) {
        this.name = name;
        this.status = TaskStatus.TODO;
        this.description = "No description";
    }

    public Task(String name, LocalDateTime deadline) {
        this.name = name;
        this.status = TaskStatus.TODO;
        this.deadline = deadline;
        this.description = "No description";
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.TODO;
    }

    public Task(String name, String description, LocalDateTime deadline) {
        this.name = name;
        this.status = TaskStatus.TODO;
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "\n" + name + "\nDescription: " + description + "\nStatuS: " + status + "\nDeadline: " + deadline + "\n";
    }
}
