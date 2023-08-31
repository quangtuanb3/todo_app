package com.example.task_app.cronJobs;

import com.example.task_app.model.Task;
import com.example.task_app.model.TaskHistory;
import com.example.task_app.repository.TaskHistoryRepository;
import com.example.task_app.repository.TaskRepository;
import com.example.task_app.services.TaskRenewalService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class renewalTasks {
    TaskRenewalService taskRenewalService;
    TaskHistoryRepository taskHistoryRepository;
    TaskRepository taskRepository;

    @Transactional
    @Scheduled(cron = "0 18 11 * * *")
    public void renewTasks() {
        System.out.println("Renewal task started.");
        LocalDate lastDate = taskRenewalService.getLastCreatedDate();

        if (LocalDate.now().isAfter(lastDate)) {
            List<Task> tasksToRenew = taskRepository.findByRenewalDate(lastDate.plusDays(1)); // Adjust method name
            List<TaskHistory> renewedTasks = tasksToRenew.stream()
                    .map(task -> taskRenewalService.createTaskHistoryFromTask(task))
                    .map(taskHistoryRepository::save)
                    .toList();
            taskRenewalService.updateRenewalDate(tasksToRenew, lastDate);
            taskHistoryRepository.saveAll(renewedTasks);

        }
    }
    @Transactional
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        renewTasks();
    }
}
