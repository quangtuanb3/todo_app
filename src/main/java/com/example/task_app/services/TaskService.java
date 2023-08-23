package com.example.task_app.services;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class TaskService {
    private static Long currentID = 0L;
    private static Long currentNonDailyId = 0L;
    private static TaskService taskService;
    public static List<Task> taskList = new ArrayList<>();
    private Map<LocalDate, List<Task>> dailyTask = new HashMap<>();
    private static Map<LocalDate, List<Task>> nonDailyTask = new HashMap<>();


    static {
        taskService = new TaskService();
        taskList.add(Task.builder().id(++currentID).title("Do exercise")
                .description("wake up time")
                .start(LocalTime.of(6, 0))
                .end(LocalTime.of(6, 30))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS)
                .build());
        taskList.add(Task.builder().id(++currentID).title("Daily Meeting")
                .description("show what did you do and what will you do")
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(8, 30))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS)
                .build());
    }



    public List<Task> getDailyTask() {
        if (!dailyTask.containsKey(LocalDate.now())) {
            dailyTask.put(LocalDate.now(), taskList);
        }
        return dailyTask.get(LocalDate.now());
    }

    public List<Task> getDailyTask(LocalDate date) {
        if (!dailyTask.containsKey(date)) {
            dailyTask.put(date, taskList);
        }
        return dailyTask.get(date);
    }
    public List<Task> getNonDailyTask() {
        return TaskService.nonDailyTask.get(LocalDate.now());
    }

    public List<Task> getNonDailyTask(LocalDate date) {
        return nonDailyTask.get(date);
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
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Id not found"));
    }

    public void edit(Task task) {
        taskList.stream()
                .filter(e -> Objects.equals(e.getId(), task.getId())).findFirst()
                .ifPresent(
                        e -> {
                            e.setDescription(task.getDescription());
                            e.setTitle(task.getTitle());
                            e.setStatus(task.getStatus());
                            e.setStart(task.getStart());
                            e.setEnd(task.getEnd());
                            e.setType(task.getType());
                        }
                );
    }

    public void deleteById(Long id) {
        taskList = taskList.stream().filter(e -> !Objects.equals(e.getId(), id)).collect(Collectors.toList());
    }


    public void saveNonDailyTask(Task task) {
        task.setType(TaskType.NON_DAILY);
        List<Task> listNonDailyTask = new ArrayList<>();
        if (TaskService.nonDailyTask.containsKey(LocalDate.now())) {
           listNonDailyTask = TaskService.nonDailyTask.get(LocalDate.now());
        }
        task.setId(getMaxTaskId(listNonDailyTask) + 1);
        listNonDailyTask.add(task);
        TaskService.nonDailyTask.put(LocalDate.now(), listNonDailyTask);
    }
    public static Long getMaxTaskId(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .max(Long::compare)
                .orElse(0L);
    }
    public List<Task> getAll(){
        List<Task> result = new ArrayList<>();

        List<Task> dailyTasks = TaskService.getInstance().getDailyTask();
        if (dailyTasks != null) {
            result.addAll(dailyTasks);
        }

        List<Task> nonDailyTasks = TaskService.getInstance().getNonDailyTask();
        if (nonDailyTasks != null) {
            result.addAll(nonDailyTasks);
        }

        result.sort(Comparator.comparing(Task::getStart)); // Sorting by start time

        return result;
    }
}
