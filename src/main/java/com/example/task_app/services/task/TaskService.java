package com.example.task_app.services.task;

import com.example.task_app.model.Profile;
import com.example.task_app.model.Task;

import com.example.task_app.model.TaskHistory;
import com.example.task_app.model.User;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.repository.TaskHistoryRepository;
import com.example.task_app.repository.TaskRepository;
import com.example.task_app.services.task.request.TaskSaveRequest;
import com.example.task_app.services.task.response.TaskListResponse;
import com.example.task_app.services.user.response.UserListResponse;
import com.example.task_app.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TaskService {

    private  TaskRepository taskRepository;
    private TaskHistoryRepository taskHistoryRepository;

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }

    public List<TaskListResponse> findAll() {
        return taskRepository.findAll().stream().map(task -> {
            return AppUtil.mapper.map(task, TaskListResponse.class);
        }).toList();
    }

    public void edit(Task task) {
        taskRepository.findById(task.getId()).map(
                t -> {
                    t.setTitle(task.getTitle());
                    t.setStart(task.getStart());
                    t.setEnd(task.getEnd());
                    t.setDescription(task.getDescription());
                    return taskRepository.save(t);
                }
        );
    }

    public void create(TaskSaveRequest request) {
        var task = AppUtil.mapper.map(request, Task.class);
        var historyTask = AppUtil.mapper.map(task, TaskHistory.class);
        task = taskRepository.save(task);

        historyTask.setTask(task);
        historyTask.setStatus(TaskStatus.TODO);
        historyTask.setType(TaskType.DAILY);

        taskHistoryRepository.save(historyTask);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
