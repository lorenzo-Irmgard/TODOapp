import Model.Task;
import Model.TaskStatus;
import Repository.TaskOperationStatus;
import Repository.TaskRepository;
import Service.StatusMessages;
import Service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void getAllTasks() {
        Task task1 = new Task("task1", "De");
        Task task2 = new Task("task2", "scr");
        Task task3 = new Task("task3", "ipt");
        Task task4 = new Task("task4", "ion");

        Set<Task> testTasks = new LinkedHashSet<>();
        testTasks.add(task1);
        testTasks.add(task2);
        testTasks.add(task3);
        testTasks.add(task4);
        Mockito.when(taskRepository.getAllTasks()).thenReturn(Set.of(task1, task2, task3, task4));
        Assertions.assertEquals(testTasks, taskService.getAllTasks());
    }

    @Test
    void addTask_newTask() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.addTask(task1)).thenReturn(TaskOperationStatus.SUCCESS);
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULUlLY_ADDED.getMessage(), taskService.addTask(task1));
    }

    @Test
    void addTask_existingTask() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.addTask(task1)).thenReturn(TaskOperationStatus.TASK_ALREADY_EXISTS);
        Assertions.assertEquals(StatusMessages.TASK_ADDING_FAILED.getMessage(), taskService.addTask(task1));
    }

    @Test
    void removeTask() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.findAndRemoveTask(task1.getName())).thenReturn(TaskOperationStatus.SUCCESS);
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULLY_DELETED.getMessage(), taskService.removeTask(task1.getName()));
    }

    @Test
    void removeTask_noSuchTask() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.findAndRemoveTask(task1.getName())).thenReturn(TaskOperationStatus.TASK_NOT_FOUND);
        Assertions.assertEquals(StatusMessages.TASk_DELETION_FAILED.getMessage(), taskService.removeTask(task1.getName()));
    }

    @Test
    void editTaskName() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.containsTask("newTaskName")).thenReturn(false);
        Mockito.when(taskRepository.getTask(task1.getName())).thenReturn(task1);
//        taskService.editTaskName(task1.getName(), "newTaskName");
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage(), taskService.editTaskName(task1.getName(), "newTaskName"));
        Assertions.assertEquals("newTaskName", task1.getName());
    }

    @Test
    void editTaskName_taskAlreadyExists() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.containsTask("newTaskName")).thenReturn(true);
        Assertions.assertEquals(StatusMessages.TASK_ADDING_FAILED.getMessage(), taskService.editTaskName(task1.getName(), "newTaskName"));
    }

    @Test
    void editTaskStatus() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.getTask(task1.getName())).thenReturn(task1);
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage(), taskService.editTaskStatus(task1.getName(), TaskStatus.IN_PROGRESS));
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, task1.getStatus());
    }

    @Test
    void editTaskDescription() {
        Task task1 = new Task("task1", "De");
        Mockito.when(taskRepository.getTask(task1.getName())).thenReturn(task1);
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage(), taskService.editTaskDescription(task1.getName(), "newDescription"));
        Assertions.assertEquals("newDescription", task1.getDescription());
    }

    @Test
    void editTaskDeadline() {
        LocalDateTime oldDeadline = LocalDateTime.parse("2025-05-21T12:00");
        LocalDateTime newDeadline = LocalDateTime.parse("2026-05-21T12:00");
        Task task1 = new Task("task1", "De", oldDeadline);
        Mockito.when(taskRepository.getTask(task1.getName())).thenReturn(task1);
        Assertions.assertEquals(StatusMessages.TASK_SUCCESSFULLY_EDITED.getMessage(), taskService.editTaskDeadline(task1.getName(), newDeadline));
        Assertions.assertEquals(newDeadline, task1.getDeadline());
    }

    @Test
    void getFilteredTasks() {
        Task task1 = new Task("task1", "De", TaskStatus.TODO);
        Task task3 = new Task("task3", "ri", TaskStatus.DONE);
        Task task2 = new Task("task2", "ri", TaskStatus.IN_PROGRESS);
        Task task4 = new Task("task4", "pti", TaskStatus.TODO);
        Task task5 = new Task("task5", "on", TaskStatus.DONE);
        Set<Task> todoSet = new LinkedHashSet<>();
        todoSet.add(task1);
        todoSet.add(task4);
        Mockito.when(taskRepository.getAllTasks()).thenReturn(Set.of(task1, task2, task3, task4, task5));
        Assertions.assertEquals(todoSet, taskService.getFilteredTasks(TaskStatus.TODO));
    }

    @Test
    void getFilteredTasks_empty() {
        Task task1 = new Task("task1", "De", TaskStatus.TODO);
        Task task3 = new Task("task3", "ri", TaskStatus.DONE);
        Task task4 = new Task("task4", "pti", TaskStatus.TODO);
        Task task5 = new Task("task5", "on", TaskStatus.DONE);
        Mockito.when(taskRepository.getAllTasks()).thenReturn(Set.of(task1, task3, task4, task5));
        Assertions.assertTrue(taskService.getFilteredTasks(TaskStatus.IN_PROGRESS).isEmpty());
    }

    @Test
    void getTaskListSortedByStatus() {
        Task task1 = new Task("task1", "De", TaskStatus.TODO);
        Task task2 = new Task("task2", "ri", TaskStatus.IN_PROGRESS);
        Task task3 = new Task("task3", "ri", TaskStatus.DONE);
        Task task4 = new Task("task4", "pti", TaskStatus.TODO);
        Task task5 = new Task("task5", "on", TaskStatus.DONE);
        List<Task> testList = new ArrayList<>();
        Set<Task> repoSet = new LinkedHashSet<>();
        repoSet.add(task1);
        repoSet.add(task2);
        repoSet.add(task3);
        repoSet.add(task4);
        repoSet.add(task5);
        testList.add(task1);
        testList.add(task2);
        testList.add(task3);
        testList.add(task4);
        testList.add(task5);
        testList.sort((o1, o2) -> {
            if (o1.getStatus().getOptionInNumberFormat() == o2.getStatus().getOptionInNumberFormat()) return 0;
            return (o1.getStatus().getOptionInNumberFormat() > o2.getStatus().getOptionInNumberFormat()) ? 1 : -1;
        });
        Mockito.when(taskRepository.getAllTasks()).thenReturn(repoSet);
        Assertions.assertEquals(testList, new ArrayList<> (taskService.getTaskListSortedByStatus()));
    }

    @Test
    void getTaskListSortedByDeadline() {
        LocalDateTime time2 = LocalDateTime.parse("2026-04-04T04:04");
        LocalDateTime time3 = LocalDateTime.parse("2027-04-04T04:04");
        LocalDateTime time4 = LocalDateTime.parse("2028-04-04T04:04");
        LocalDateTime time5 = LocalDateTime.parse("2029-04-04T04:04");
        Task task1 = new Task("task1", "De");
        Task task2 = new Task("task2", "ri", time2);
        Task task3 = new Task("task3", "ri", time3);
        Task task4 = new Task("task4", "pti", time4);
        Task task5 = new Task("task5", "on", time5);
        List<Task> expectedList = new ArrayList<>();
        expectedList.add(task2);
        expectedList.add(task3);
        expectedList.add(task4);
        expectedList.add(task5);
        expectedList.add(task1);
        Mockito.when(taskRepository.getAllTasks()).thenReturn(Set.of(task1, task2, task3, task4, task5));
        Assertions.assertEquals(expectedList, new ArrayList<>(taskService.getTaskListSortedByDeadline()));
    }
}