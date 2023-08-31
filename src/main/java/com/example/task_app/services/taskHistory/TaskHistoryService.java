package com.example.task_app.services.taskHistory;

import com.example.task_app.model.Exception.ResourceNotFoundException;
import com.example.task_app.model.TaskHistory;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.repository.TaskHistoryRepository;
import com.example.task_app.services.taskHistory.request.TaskHistorySaveRequest;
import com.example.task_app.services.taskHistory.response.TaskHistoryListResponse;
import com.example.task_app.util.AppMsg;
import com.example.task_app.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class TaskHistoryService {
    private TaskHistoryRepository taskHistoryRepository;

    public void saveNonDailyTask(TaskHistorySaveRequest taskHistory) {
        var historyTask = AppUtil.mapper.map(taskHistory, TaskHistory.class);
        historyTask.setType(TaskType.NON_DAILY);
        taskHistoryRepository.save(historyTask);

    }

    public void updateStatus(Long id, TaskStatus status) {
        TaskHistory task = findById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);

    }

    public TaskHistory findById(Long id) {
        return taskHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMsg.ID_NOT_FOUND, "Task", id)));
    }

    public List<TaskHistoryListResponse> getTaskInDay(LocalDate date) {
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findTaskHistoriesForDate(date);
        return taskHistoryList.stream()
                .map(task -> AppUtil.mapper.map(task, TaskHistoryListResponse.class))
                .toList();

    }
    public List<TaskHistoryListResponse> getTaskDateToDate(LocalDate start, LocalDate end) {
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findTaskHistoriesDateToDate(start, end);
        return taskHistoryList.stream()
                .map(task -> AppUtil.mapper.map(task, TaskHistoryListResponse.class))
                .toList();

    }
    public List<TaskHistoryListResponse> getTaskInWeek(LocalDate date) {
        LocalDate[] week = AppUtil.getWeekBoundaries(date);
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findTaskHistoriesForWeek(week[0], week[1]);
        return taskHistoryList.stream()
                .map(task -> AppUtil.mapper.map(task, TaskHistoryListResponse.class))
                .toList();
    }

    public void deleteById(Long id) {
        taskHistoryRepository.deleteById(id);
    }

    public void edit(TaskHistorySaveRequest taskHistory, Long id) {
        var taskHistoryDB = findById(id);

        taskHistoryDB.setTitle(taskHistory.getTitle());
        taskHistoryDB.setDescription(taskHistory.getDescription());
        taskHistoryDB.setStart(AppUtil.mapper.map(taskHistory.getStart(), LocalDateTime.class));
        taskHistoryDB.setEnd(AppUtil.mapper.map(taskHistory.getEnd(), LocalDateTime.class));
        taskHistoryDB.setStatus(TaskStatus.valueOf(taskHistory.getStatus()));

        taskHistoryRepository.save(taskHistoryDB);
    }


}
