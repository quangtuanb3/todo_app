package com.example.task_app.services;

import com.example.task_app.model.Task;
import com.example.task_app.model.TaskHistory;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.repository.TaskHistoryRepository;
import com.example.task_app.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TaskRenewalService {

    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void renewTasks() {
        System.out.println("Renewal task started.");
        LocalDate lastDate = getLastCreatedDate();

        if (LocalDate.now().isAfter(lastDate)) {
            List<Task> tasksToRenew = taskRepository.findByRenewalDate(lastDate.plusDays(1)); // Adjust method name
            List<TaskHistory> renewedTasks = tasksToRenew.stream()
                    .map(this::createTaskHistoryFromTask)
                    .map(taskHistoryRepository::save)
                    .toList();
            updateRenewalDate(tasksToRenew, lastDate);
            taskHistoryRepository.saveAll(renewedTasks);

        }
    }

    public void updateRenewalDate(List<Task> tasks, LocalDate date) {
        taskRepository.saveAll(tasks.stream()
                .peek(task -> task.setRenewalDate(date.plusDays(2)))
                .collect(Collectors.toList()));

    }


    @Transactional
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        renewTasks();
    }

    private LocalDate getLastCreatedDate() {

        List<LocalDate> results = taskHistoryRepository.findCreatedTask(PageRequest.of(0, 1));
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return LocalDate.now().plusDays(-1);

    }


    private LocalDate getNextRenewalDateFromStore() {
        Task taskWithNextRenewal = taskRepository.findTopByOrderByRenewalDateAsc();

        if (taskWithNextRenewal != null) {
            return taskWithNextRenewal.getRenewalDate();
        } else {
            // If no tasks are found, return tomorrow's date
            return LocalDate.now().plusDays(1);
        }
    }


    private TaskHistory createTaskHistoryFromTask(Task task) {
        LocalDate currentDate = LocalDate.now();

        return TaskHistory.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .start(currentDate.atTime(task.getStart()))
                .end(currentDate.atTime(task.getEnd()))
                .status(TaskStatus.TODO)
                .type(TaskType.DAILY)
                .task(task)
                .created(LocalDate.now())
                .build();
    }

}
