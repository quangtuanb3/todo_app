package com.example.task_app.services;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Service

public class TaskService {
    private static Long currentID = 0L;
    private static TaskService taskService;
    public static List<Task> taskList = new ArrayList<>();

    static {
        taskService = new TaskService();
        taskList.add(Task.builder().id(++currentID).title("Do exercise")
                .description("wake up time")
                .start(LocalTime.of(6, 0))
                .end(LocalTime.of(6,30))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS)
                .build());
        taskList.add(Task.builder().id(++currentID).title("Daily Meeting")
                .description("show what did you do and what will you do")
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(8,30))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS)
                .build());
    }

    public static TaskService getInstance() {
        return taskService;
    }

    public List<Task> findAll() {
        return taskList;
    }

    public void save(Task task) {
        task.setId(++currentID);
        taskList.add(task);
    }

    public Task findById(Long id) {
        return taskList.stream()
                .filter(e-> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Id not found"));
    }

    public void edit(Task task) {
        taskList.stream()
                .filter(e-> Objects.equals(e.getId(), task.getId())).findFirst()
                .ifPresent(
                        e->{e.setDescription(task.getDescription());
                            e.setTitle(task.getTitle());
                            e.setStatus(task.getStatus());
                            e.setStart(task.getStart());
                            e.setEnd(task.getEnd());
                            e.setType(task.getType());
                        }
                );
    }

    public void deleteById(Long id) {
        taskList = taskList.stream().filter(e-> !Objects.equals(e.getId(), id)).collect(Collectors.toList());
    }
}
