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

    public List<TaskHistoryListResponse> getTaskInDay() {
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findTaskHistoriesForToday();
        return taskHistoryList.stream()
                .map(task -> AppUtil.mapper.map(task, TaskHistoryListResponse.class))
                .toList();

    }

    public void deleteById(Long id) {
        taskHistoryRepository.deleteById(id);
    }
}
